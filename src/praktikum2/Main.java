package praktikum2;

public class Main {

	final static String propfile = "/db.properties";

	public static void main(String[] args) {

		try (ConnectDB cn = new ConnectDB()) {
			cn.connect(propfile);
			initDB(cn);

			String[] movieList = FileDBInput.liesMovies("src/movies.csv");

			cn.preparedExecuteInsert("INSERT INTO MOVIE (MID, TITLE, YEAR) VALUES (?, ?, ?)", movieList, false);

			// ReadDB.printDatabase("movie", cn);
			ReadDB.printDatabase("movie", cn, 1, 2);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void initDB(ConnectDB cn) {
		cn.execute("DROP TABLE MOVIE", false);
		cn.execute("create table Movie (\r\n" + "    MID integer primary key,\r\n"
				+ "    Title varchar(255) not null,\r\n" + "    Year smallint not null\r\n" + ")", false);
	}

}
