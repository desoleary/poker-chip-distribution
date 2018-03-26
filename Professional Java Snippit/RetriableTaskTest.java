package com.smarttech.appengine.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.apphosting.api.DeadlineExceededException;
import com.smarttech.test.util.BaseJMockTest;

/**
 * Test for {@link RetriableTask}.
 * 
 * @author doleary
 */
// CHECKSTYLE_OFF: MagicNumber
public class RetriableTaskTest extends BaseJMockTest {
	private RetryImpl task;

	protected final LocalServiceTestHelper helper = new LocalServiceTestHelper();

	@Before
	public final void setupAppEngine() {
		helper.setUp();
	}

	@After
	public final void teardownAppEngine() {
		helper.tearDown();
	}

	@Before
	public void setUp() {
		task = createMock(RetryImpl.class, "stringRetryImpl");
	}

	@Test
	public void allUnrecoverableExceptionMethodsAreTheSame() {
		List<Class<? extends RuntimeException>> theExceptions = Arrays.asList(IllegalStateException.class, IllegalArgumentException.class);

		List<Class<? extends Throwable>> v1 =
				new RetriableTask.Builder<String>().unrecoverableExceptions(theExceptions).getUnrecoverableExceptions();
		List<Class<? extends Throwable>> v2 =
				new RetriableTask.Builder<String>().unrecoverableExceptions(IllegalStateException.class, IllegalArgumentException.class)
						.getUnrecoverableExceptions();
		List<Class<? extends Throwable>> v3 =
				new RetriableTask.Builder<String>().unrecoverableException(IllegalStateException.class)
						.unrecoverableException(IllegalArgumentException.class).getUnrecoverableExceptions();

		assertEquals(v1, v2);
		assertEquals(v2, v3);
	}

	@Test(expected = ClassCastException.class)
	public void shouldBeSafeVarargs() {
		// Test if an evil caller is being nasty and trying to sneak a bad class through the varargs
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Class<? extends Exception> c = (Class) List.class;

		new RetriableTask.Builder<String>().unrecoverableExceptions(c);
	}

	@Test(expected = RetryException.class)
	public void shouldRetryThreeTimesBeforeThrowingRetryException() throws Exception {
		final int numberOfRetriesAllowed = 3;

		addExpectations(new Expectations() {
			{
				exactly(numberOfRetriesAllowed).of(task);
				will(throwException(new Exception("some unexpected error")));
			}
		});

		new RetriableTask.Builder<String>().numberOfRetries(numberOfRetriesAllowed).timeToWaitInMs(1).task(task).buildAndExecute();

	}

	@Test(expected = RetryException.class)
	public void shouldRetryZeroTimesIfNumberOfRetriesIsSetToZero() throws Exception {
		final int numberOfRetriesAllowed = 0;

		addExpectations(new Expectations() {
			{
				oneOf(task).call();
				will(throwException(new Exception("some unexpected error")));
			}
		});

		new RetriableTask.Builder<String>().numberOfRetries(numberOfRetriesAllowed).timeToWaitInMs(1).task(task).buildAndExecute();
	}

	@Test(expected = TestException.class)
	public void shouldRetryThreeTimesBeforeThrowingUnderlyingError() throws Exception {
		final int numberOfRetriesAllowed = 3;

		addExpectations(new Expectations() {
			{
				exactly(numberOfRetriesAllowed).of(task);
				will(throwException(new TestException("some unexpected error")));
			}
		});

		new RetriableTask.Builder<String>().numberOfRetries(numberOfRetriesAllowed).timeToWaitInMs(1).task(task).rethrowOriginalError(true)
				.buildAndExecute();
	}

	@Test
	public void shouldReturnSuccessIfThirdCallReturnsValue() throws Exception {
		final int numberOfRetriesAllowed = 3;
		final String result = "some returned value";

		addExpectations(new Expectations() {
			{
				exactly(numberOfRetriesAllowed - 1).of(task);
				will(throwException(new Exception("some unexpected error")));

				oneOf(task).call();
				will(returnValue(result));
			}
		});

		final String actual =
				new RetriableTask.Builder<String>().numberOfRetries(numberOfRetriesAllowed).timeToWaitInMs(1).task(task).buildAndExecute();

		Assert.assertEquals(result, actual);
	}

	@Test(expected = UnrecoverableTestException.class)
	public void shouldNotRetryWhenEncounteringANonRecoverableException() throws Exception {
		// Initialize
		final int numberOfRetriesAllowed = 3;
		ArrayList<Class<? extends Throwable>> unrecoverableErrors = new ArrayList<>();
		unrecoverableErrors.add(UnrecoverableTestException.class);

		addExpectations(new Expectations() {
			{
				oneOf(task).call();
				will(throwException(new UnrecoverableTestException("some unexpected error", new Exception())));
			}
		});

		new RetriableTask.Builder<String>().numberOfRetries(numberOfRetriesAllowed).timeToWaitInMs(1).task(task)
				.unrecoverableExceptions(unrecoverableErrors).buildAndExecute();
	}

	@Test
	public void shouldReturnSuccessIfFirstCallReturnsValue() throws Exception {
		final int numberOfRetriesAllowed = 3;
		final String result = "some returned value";

		addExpectations(new Expectations() {
			{
				oneOf(task).call();
				will(returnValue(result));
			}
		});

		String actual =
				new RetriableTask.Builder<String>().numberOfRetries(numberOfRetriesAllowed).timeToWaitInMs(1).task(task).buildAndExecute();

		Assert.assertEquals(result, actual);
	}

	@Test
	public void shouldReturnImmediatelyWhenTestingWithRealObject() throws Exception {
		final int numberOfRetriesAllowed = 3;

		RetryImpl task = new RetryImpl(false);

		String actual =
				new RetriableTask.Builder<String>().numberOfRetries(numberOfRetriesAllowed).timeToWaitInMs(1).task(task).buildAndExecute();

		System.out.println(actual);
	}

	@Test(expected = RetryException.class)
	public void shouldThrowRetryExceptionWithReal() throws Exception {
		final int numberOfRetriesAllowed = 3;

		RetryImpl task = new RetryImpl();

		String actual =
				new RetriableTask.Builder<String>().numberOfRetries(numberOfRetriesAllowed).timeToWaitInMs(1).task(task).buildAndExecute();

		System.out.println(actual);
	}

	@Test(expected = DeadlineExceededException.class)
	public void shouldThrowDeadlineException() throws Exception {
		DeadlineExceededImpl impl = new DeadlineExceededImpl();

		String actual = new RetriableTask.Builder<String>().numberOfRetries(5).timeToWaitInMs(1).task(impl).buildAndExecute();

		System.out.println(actual);
	}

	/**
	 * Testing Retry functionality utility class.
	 */
	class RetryImpl implements Callable<String> {
		private int numberOfTries = 0;
		private boolean throwException = true;

		public RetryImpl() {
		}

		public RetryImpl(boolean throwException) {
			this.throwException = throwException;
		}

		/**
		 * Computes a result, or throws an exception if unable to do so.
		 * 
		 * @return computed result
		 * @throws Exception
		 *             if unable to compute a result
		 */
		@Override
		public String call() throws Exception {
			if (this.throwException) {
				System.out.println(String.format("throwing retry exception %s time", ++numberOfTries));
				throw new Exception("RetryImpl throwing error");
			}

			return "some successful return value";
		}
	}

	/**
	 * Testing Retry functionality utility class.
	 */
	static class DeadlineExceededImpl implements Callable<String> {
		private int tries = 0;

		public DeadlineExceededImpl() {
		}

		/**
		 * Computes a result, or throws an exception if unable to do so.
		 * 
		 * @return computed result
		 * @throws Exception
		 *             if unable to compute a result
		 */
		@Override
		public String call() throws Exception {
			if (tries++ == 3) {
				throw new DeadlineExceededException("Oh noez");
			}

			throw new Exception("Meh");
		}
	}

	/**
     *
     */
	static class TestException extends Exception {
		private static final long serialVersionUID = -2741817691732065092L;

		public TestException(String m) {
			super(m);
		}
	}

	/**
     *
     */
	static class UnrecoverableTestException extends Exception {
		private static final long serialVersionUID = -3753306567738764171L;

		public UnrecoverableTestException(String m, Throwable t) {
			super(m, t);
		}
	}
}
