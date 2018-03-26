package com.smarttech.appengine.util;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;

import com.google.apphosting.api.ApiProxy;
import com.google.apphosting.api.DeadlineExceededException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * Generic class to handle retries.
 * 
 * @param <T>
 *            task
 */
public final class RetriableTask<T> implements Callable<T> {
	public static final int DEFAULT_NUMBER_OF_RETRIES = 3;
	private static final int MAX_TIME_TO_WAIT = 25000;

	/**
	 * Make sure we leave ourselves with at least 250ms to perform the operation. This is not configurable.
	 */
	private static final int REMAINING_APPENGINE_TIME_GRACE = 250;

	private final Callable<T> task;

	private final int numberOfRetries; // total number of tries
	private int numberOfTriesLeft; // number left
	private final boolean rethrowOriginalError;
	private final List<Class<? extends Throwable>> unrecoverableExceptions;
	private boolean working = false;
	private final RetryStrategy retryStrategy;
	private final Object lock = new Object();

	private RetriableTask(Builder<T> builder) {
		this.numberOfRetries = builder.getNumberOfRetries();
		this.numberOfTriesLeft = builder.getNumberOfTriesLeft();
		this.unrecoverableExceptions = builder.getUnrecoverableExceptions();
		this.task = builder.getTask();
		this.rethrowOriginalError = builder.isRethrowOriginalError();
		this.retryStrategy = builder.getRetryStrategy();
	}

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 * 
	 * @return computed result
	 * @throws InterruptedException
	 *             if thread is interrupted
	 * @throws Exception
	 *             if unable to compute a result, thread is interrupted or if caught exception is an instance of any of
	 *             [unrecoverableExceptions]
	 */
	@Override
	public T call() throws Exception {
		synchronized (lock) {
			if (working)
				throw new ConcurrentModificationException("call() may only be called a single time; please allocate a new object");
			working = true;
		}

		int lastIntervalMillis = RetryStrategy.FIRST_RETRY_ATTEMPT;
		while (true) {
			try {
				return task.call();
			} catch (InterruptedException | CancellationException e) {
				throw e;
			} catch (DeadlineExceededException e) {
				// AppEngine is going to kill us very soon. We need to exit ASAP.
				throw e;
			} catch (Exception e) {
				if (unrecoverableExceptions.contains(e.getClass())) {
					throw e;
				}

				numberOfTriesLeft--;
				if (numberOfTriesLeft <= 0) {
					throwExceptionAfterFailure(e);
				}

				lastIntervalMillis = this.retryStrategy.shouldRetryIn(lastIntervalMillis);

				// Make sure we actually have enough time to sleep and try an operation (minimum 250ms) before we
				// actually do that.
				// getCurrentEnvironment will return null if the App Engine Test Helper is not setup properly.
				// Ignore timeouts in this case.
				if (ApiProxy.getCurrentEnvironment() != null
                        && ApiProxy.getCurrentEnvironment().getRemainingMillis() < lastIntervalMillis + REMAINING_APPENGINE_TIME_GRACE) {
					throwExceptionAfterFailure(e);
				}

				Thread.sleep(lastIntervalMillis);
			}
		}
	}

	private void throwExceptionAfterFailure(Exception e) throws Exception, RetryException {
		if (rethrowOriginalError) {
			throw e;
		}

		throw new RetryException(numberOfRetries + " attempts to retry failed after " + numberOfRetries + "attempts", e);
	}

	/**
	 * Builder class to buildAndExecute the {@link RetriableTask} that returns the immutable {@link RetriableTask}
	 * object.
	 */
	public static class Builder<T> {
		private int numberOfRetries = DEFAULT_NUMBER_OF_RETRIES;
		private int numberOfTriesLeft = DEFAULT_NUMBER_OF_RETRIES;
		private boolean rethrowOriginalError = false;
		private List<Class<? extends Throwable>> unrecoverableExceptions = Lists.newArrayList();
		private Callable<T> task;
		private RetryStrategy retryStrategy = new ExponentialBackoffRetryStragety();

		public Builder<T> numberOfRetries(int numberOfRetries) {
			this.numberOfRetries = numberOfRetries;
			this.numberOfTriesLeft = numberOfRetries;
			return this;
		}

		public Builder<T> timeToWaitInMs(int timeToWaitInMs) {
			this.retryStrategy.setInitialInterval(timeToWaitInMs);
			return this;
		}

		public Builder<T> unrecoverableException(Class<? extends Throwable> unrecoverableException) {
			this.unrecoverableExceptions.add(unrecoverableException);
			return this;
		}
		
		public Builder<T> unrecoverableExceptions(List<? extends Class<? extends Throwable>> unrecoverableExceptions) {
			this.unrecoverableExceptions.addAll(unrecoverableExceptions);
			return this;
		}

		@SafeVarargs
		public final Builder<T> unrecoverableExceptions(Class<? extends Throwable>... unrecoverableExceptions) {
			// Because this is a generic varargs method, we have to do the work of the compiler
			for (Class<? extends Throwable> e : unrecoverableExceptions)
				if (!Throwable.class.isAssignableFrom(e))
					throw new ClassCastException();
			this.unrecoverableExceptions.addAll(Arrays.asList(unrecoverableExceptions));
			return this;
		}

		public Builder<T> task(Callable<T> task) {
			this.task = task;
			return this;
		}

		public Builder<T> rethrowOriginalError(boolean rethrowOriginalError) {
			this.rethrowOriginalError = rethrowOriginalError;
			return this;
		}

		public int getNumberOfRetries() {
			return numberOfRetries;
		}

		public int getNumberOfTriesLeft() {
			return numberOfTriesLeft;
		}

		public List<Class<? extends Throwable>> getUnrecoverableExceptions() {
			return unrecoverableExceptions;
		}

		public Callable<T> getTask() {
			return task;
		}

		public boolean isRethrowOriginalError() {
			return rethrowOriginalError;
		}

		public RetryStrategy getRetryStrategy() {
			return retryStrategy;
		}

		public Builder<T> setRetryStrategy(RetryStrategy retryStrategy) {
			this.retryStrategy = retryStrategy;
			return this;
		}

		public T buildAndExecute() throws Exception {
			return build().call();
		}

		public RetriableTask<T> build() throws Exception {
			Preconditions.checkNotNull(task);
			Preconditions.checkArgument(numberOfRetries >= 0, "number of retries must be greater than zero given {%s}", numberOfRetries);

			return new RetriableTask<>(this);
		}

		/**
		 * simple retry strategy for critical UI tasks. Will simply retry at a set (quick) interval
		 */
		public static class SetIntervalRetryStrategy implements RetryStrategy {

			private int interval = RetryStrategy.DEFAULT_RETRY_INTERVAL;

			@Override
			public int shouldRetryIn(int lastRetryInterval) {
				return interval;
			}

			@Override
			public void setInitialInterval(int initialInterval) {
				Preconditions.checkArgument(initialInterval >= 0 && initialInterval < MAX_TIME_TO_WAIT, String
						.format("given time to wait is invalid {%s} must be positive and less than maximum {%s}", initialInterval,
								MAX_TIME_TO_WAIT));

				this.interval = initialInterval;
			}
		}

		/**
		 * a more sane retry policy that will perform an exponential backoff.
		 */
		public static class ExponentialBackoffRetryStragety implements RetryStrategy {

			private int firstInterval = RetryStrategy.DEFAULT_RETRY_INTERVAL;

			@Override
			public int shouldRetryIn(int lastRetryInterval) {
				if (lastRetryInterval == RetryStrategy.FIRST_RETRY_ATTEMPT) {
					return firstInterval;
				} else {
					// wait twice as long this time.
					return lastRetryInterval * 2;
				}
			}

			@Override
			public void setInitialInterval(int initialInterval) {
				Preconditions.checkArgument(initialInterval >= 0 && initialInterval < MAX_TIME_TO_WAIT, String
						.format("given time to wait is invalid {%s} must be positive and less than maximum {%s}", initialInterval,
								MAX_TIME_TO_WAIT));
				this.firstInterval = initialInterval;
			}
		}

	}

	/**
	 * this is a generic way to implement whether or not to actually retry.
	 */
	public interface RetryStrategy {

		int FIRST_RETRY_ATTEMPT = -1;
		int DEFAULT_RETRY_INTERVAL = 500;

		int shouldRetryIn(int lastRetryInterval);

		void setInitialInterval(int initialInterval);

	}

}
