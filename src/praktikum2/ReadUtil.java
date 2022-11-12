package praktikum2;

import java.util.Scanner;

public class ReadUtil {

    public static String readString() {

        System.out.print("Gebe eine Tabelle ein: ");

        String input = null;
        try (Scanner scanner = new Scanner(System.in)) {

            input = scanner.nextLine();

        }

        return input;

    }

}
