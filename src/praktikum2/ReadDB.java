package praktikum2;

public class ReadDB {

	public static void printDatabase(String database, ConnectDB cn) {
		printDatabase(database, "\t", cn);
	}

	public static void printDatabase(String database, String separator, ConnectDB cn) {
		cn.execute("select * from " + database, separator, true);
	}

	public static void printDatabase(String database, ConnectDB cn, int min, int max) {

		String query = "select * from " + database + " where MID between ? and ?";
		cn.preparedExecuteBetween(query, min, max);

	}

	private ReadDB() {
	}

}
