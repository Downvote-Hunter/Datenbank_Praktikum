package praktikum3.util;

import praktikum3.exception.InputNotInRange;

import java.sql.Date;
import java.util.Scanner;

public class ReadUtil {

    public static Scanner scanner = new Scanner(System.in);

    public static String readString(String message) {
        System.out.print(message);

        String input = null;

        do {
            input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty!");
                System.out.print(message);
            }
        } while (input.isEmpty());


        return input;

    }

    public static int readInt(int min, int max) throws InputNotInRange {

        int input = -1;
        input = readInt("", min, max);
        return input;
    }

    public static int readInt(String message, int min, int max) throws InputNotInRange {

        if (!message.equals("")) System.out.print(message);

        int input = -1;

        boolean validInput = false;

        do {
            try {
                input = Integer.parseInt(scanner.nextLine());

                if (input < min || input > max) {
                    System.out.println("Input is not between " + min + " and " + max);
                    if (!message.equals("")) System.out.print(message);
                } else {
                    validInput = true;
                }

            } catch (NumberFormatException e) {
                System.out.println("Input is not a number");
                if (!message.equals("")) System.out.print(message);
            }

        } while (!validInput);

        return input;
    }

    public static int readInt(String message) throws InputNotInRange {
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;

        return readInt(message, min, max);
    }

    public static Date readDate(String message) {

        Date date = null;
        boolean validDate = false;
        String dateAsString = null;

        do {
            try {
                dateAsString = readString(message + " (yyyy-mm-dd): ");
                date = DateUtil.parse(dateAsString);
                validDate = true;

            } catch (Exception e) {
                System.out.println("Falsches Format: " + dateAsString);
                System.out.println("Format: yyyy-mm-dd");
            }
        } while (!validDate);

        return date;

    }

    public static String readChar(String message, char[] validChars) {

        String charAsString;
        boolean validInput = false;

        do {
            charAsString = readChar(message);

            for (char validChar : validChars) {
                if (charAsString.charAt(0) == validChar) {
                    validInput = true;
                    break;
                }
            }

            if (!validInput) {
                System.out.println("Input is not valid");
                System.out.println("Valid inputs are: " + new String(validChars));
            }

        } while (!validInput);

        return charAsString;
    }

    public static String readChar(String message) {

        String charAsString = null;
        boolean validInput = false;
        do {
            charAsString = readString(message);

            if (charAsString.length() > 1) {
                System.out.println("Input is not a single character");
            } else {
                validInput = true;
            }

        } while (!validInput);

        return charAsString;
    }
}
