package praktikum2;

public class Main {

	final static String propfile = "/db.properties";

	public static void main(String[] args) {

		try (ConnectDB cn = new ConnectDB()) {
			cn.connect(propfile);
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
		cn.execute("create table Movie (\r\n" + "    MID integer primary key,\r\n"
				+ "    Title varchar(255) not null,\r\n" + "    Year smallint not null\r\n" + ")", false);

		cn.execute("CREATE TABLE Person ( \r\n" + "pid integer PRIMARY KEY,\r\n" + "name varchar(50) NOT NULL,\r\n"
				+ "geb_datum DATE,\r\n" + "geschlecht char(1) NOT NULL)", false);

		cn.execute("create table Cast (\r\n" + "  mid integer,\r\n" + "  pid integer,\r\n" + "  role varchar(50),\r\n"
				+ "  primary key (mid, pid, role)\r\n" + ")", false);

	}

}
