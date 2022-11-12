package praktikum2;

import javax.xml.crypto.Data;

public class MetaData {

	static final String PROPFILE = "/db.properties";

	public static void main(String[] args) throws Exception {
		// TODO Tabellen neu anlegen

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
