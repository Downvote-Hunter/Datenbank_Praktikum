package praktikum3.util;


import praktikum3.exception.InputNotInRange;

import java.util.Scanner;

public class ReadUtil {

	public static Scanner scanner = new Scanner(System.in);

	public static String readString(String message) {
		System.out.print(message);

		String input = null;

		input = scanner.nextLine();

		return input;

	}

	public static int readInt(String message, int min, int max) throws InputNotInRange {

		System.out.print(message);

		int input = -1;

		input = Integer.parseInt(scanner.nextLine());

		if (input < min || input > max) {
			throw new InputNotInRange(min, max);
		}
		return input;
	}

}
