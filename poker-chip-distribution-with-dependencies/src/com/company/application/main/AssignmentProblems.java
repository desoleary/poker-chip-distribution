package com.company.application.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.company.application.controller.PokerChipDistributionController;
import com.company.application.domain.PokerChipDistributionResult;
import com.company.application.domain.PokerChipDistributionResultList;
import com.company.application.domain.ResponseType;
import com.company.application.exception.ParserException;

/**
 * Class with main method for running the inputs specified in the programming
 * problems document
 */
public class AssignmentProblems {
	private static PokerChipDistributionResultList resultList;

	/**
	 * Given a path to a configuration file, calculates the optimum distribution
	 * of poker chips that maximizes the number of chips each player receives.
	 * and runs the inputs specified in the programming problems document.
	 * 
	 * @param args
	 *            Command line options. index 0 is the path to a configuration
	 *            file.
	 * @throws IllegalArgumentException
	 *             If there are any errors reading the configuration file field
	 *             parameters.
	 * @throws IOException
	 *             If there are any errors opening the configuration file.
	 * @throws ParserException
	 *             If there are any errors parsing the configuration file.
	 */
	public static void main(String[] args) throws IllegalArgumentException,
			ParserException {
		String fileName = null;
		try {
			fileName = readFileNameFrom(System.in);

			resultList = new PokerChipDistributionController()
					.calculatePokerChipDistributionFor(readFileAsString(fileName));

			if (resultList.getResponseType() == ResponseType.DEFAULT) {
				problemOneAndTwo();
			} else if (resultList.getResponseType() == ResponseType.BONUS_ONE) {
				problemOneAndTwo();
			} else if (resultList.getResponseType() == ResponseType.BONUS_TWO) {
				problemThree();
			}
		} catch (IOException ex) {
			System.out
					.println(String
							.format(
									"IO error trying to read your filename: '%s', must end with a carriage return",
									fileName));
			System.exit(1);

		}
	}

	/**
	 * Reads and returns file name from standard input.
	 * 
	 * @param in
	 *            Input Stream from standard input.
	 * @return file name
	 * @throws IOException
	 *             occurs if there is an error in reading from the standard
	 *             input.
	 */
	private static String readFileNameFrom(InputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		return br.readLine();
	}

	/**
	 * Returns the contents of a file to a string.
	 * 
	 * @param filePath
	 *            the name of the file to open
	 * @return contents of input file.
	 * @throws java.io.IOException
	 */
	private static String readFileAsString(String filePath)
			throws java.io.IOException {
		FileReader reader = new FileReader(new File(filePath));
		BufferedReader bin = new BufferedReader(reader);
		StringBuffer sb = new StringBuffer();
		for (String line = bin.readLine(); line != null; line = bin.readLine()) {
			sb.append(line).append("\n");
		}
		return sb.toString();
	}

	private static void problemOneAndTwo() {
		System.out.println("Output:");
		for (PokerChipDistributionResult result : resultList) {
			System.out.println(String.format("%s - %s", result
					.getDenomination(), result.getQuantity()));
		}
	}

	private static void problemThree() {
		System.out.println("Output:");
		for (PokerChipDistributionResult result : resultList) {
			System.out.println(String.format("%s - %s - %s", result.getColor(),
					result.getDenomination(), result.getQuantity()));
		}
	}
}
