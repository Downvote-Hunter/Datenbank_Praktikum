package praktikum2;

public class Main {

	static final String PROPFILE = "/db.properties";

	public static void main(String[] args) {

		try (ConnectDB cn = new ConnectDB()) {
			cn.connect(PROPFILE);
			initDB(cn);

			String[] movieList = FileDBInput.readFileCSV("src/movies.csv");
			String[] personList = FileDBInput.readFileCSV("src/persons.csv");
			String[] castList = FileDBInput.readFileCSV("src/casts.csv");

			cn.preparedExecuteInsertMovie("INSERT INTO MOVIE (MID, TITLE, YEAR) VALUES (?, ?, ?)", movieList);
			cn.preparedExecuteInsertPerson("INSERT INTO PERSON (PID, NAME, GEB_DATUM, GESCHLECHT) VALUES (?, ?, ?, ?)",
					personList);
			cn.preparedExecuteInsertCast("INSERT INTO CAST (PID, MID, ROLE) VALUES (?, ?, ?)", castList);

			ReadDB.printDatabase("movie", cn);
			System.out.println();
			ReadDB.printDatabase("person", cn);
			System.out.println();
			ReadDB.printDatabase("cast", cn);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void initDB(ConnectDB cn) {
		cn.execute("DROP TABLE MOVIE", false);
		cn.execute("DROP TABLE PERSON", false);
		cn.execute("DROP TABLE CAST", false);
		DatabaseUtil.createTables(cn);

	}

}
