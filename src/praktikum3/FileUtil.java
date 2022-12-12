package praktikum3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FileUtil {

    public static void main(String[] args) {

        String[] movieList = readFileCSV("src/movies.csv");

        for (int i = 0; i < movieList.length; i++) {
            System.out.println(i + 1 + ": " + movieList[i]);
        }

    }

    public static String[] readFileCSV(String filePath) {

        List<String> listData = new ArrayList<>();

        try (Scanner readFile = new Scanner(new File(filePath))) {
            while (readFile.hasNext()) {
                String[] splitted = readFile.nextLine().split(";");

                Collections.addAll(listData, splitted);

            }

            return listData.toArray(new String[listData.size()]);

        } catch (FileNotFoundException e) {
            System.out.println("Datei:" + filePath + " Konnte nicht gefunden werden");
            System.out.println(e.getMessage());
            return new String[1];
        }

    }

}
