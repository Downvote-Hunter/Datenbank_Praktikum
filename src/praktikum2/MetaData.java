package praktikum2;

public class MetaData {

	static final String PROPFILE = "/db.properties";

	public static void main(String[] args) throws Exception {

		try (ConnectDB cn = new ConnectDB()) {
			cn.connect(PROPFILE);

			String[] tables = DBReaderGen.getTableNames(cn);

			for (String table : tables) {
				String query = "DROP TABLE " + table;
				System.out.println(query);
				cn.execute(query, false);
			}
			System.out.println();
			DatabaseUtil.createTables(cn);

		}

	}

}
