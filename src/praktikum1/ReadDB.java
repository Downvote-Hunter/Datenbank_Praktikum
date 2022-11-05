package praktikum1;

public class ReadDB {

	public static void main(String[] args) {

	}

	public static void printDatabase(String database, ConnectDB cn) {
		cn.execute("select * from " + database, true);

	}

	public static void printDatabase(String database, ConnectDB cn, int min, int max) {

		String query = "select * from " + database + " where MID between ? and ?";
		cn.preparedExecuteBetween(query, min, max);

	}

}
