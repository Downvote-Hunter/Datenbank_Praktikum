package praktikum1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileDBInput {

	public static void main(String[] args) {

		String[] movieList = liesMovies("src/movies.csv");

		for (int i = 0; i < movieList.length; i++) {
			System.out.println(i + 1 + ": " + movieList[i]);
		}

	}

	public static String[] liesMovies(String filePath) {

		ArrayList<String> lieferanten = new ArrayList<>();

		try (Scanner readFile = new Scanner(new File(filePath))) {
			while (readFile.hasNext()) {
				String[] splitted = readFile.nextLine().split(";");

				for (String value : splitted) {
					lieferanten.add(value);
				}

			}

			return lieferanten.toArray(new String[lieferanten.size()]);

		} catch (FileNotFoundException e) {
			System.out.println("Datei:" + filePath + " Konnte nicht gefunden werden");
			System.out.println(e.getMessage());
			return null;
		}

	}

}
