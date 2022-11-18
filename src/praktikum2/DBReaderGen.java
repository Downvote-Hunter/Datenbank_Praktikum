package praktikum2;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBReaderGen {

	public static void main(String[] args) {
		String separator = ";";
		final String propfile = "/db.properties";
		try (ConnectDB cn = new ConnectDB()) {
			cn.connect(propfile);

			String[] tableNames = getTableNames(cn);

			tableNames = StringUtil.toLowerCase(tableNames);

			for (String name : tableNames) {
				System.out.println(name);
			}

			String eingabe = ReadUtil.readString("Gebe eine Tabelle ein: ").toLowerCase();
			boolean tableFound = false;

			for (String name : tableNames) {
				if (eingabe.equals(name)) {
					ReadDB.printDatabase(name, separator, cn);
					tableFound = true;
				}
			}
			if (!tableFound) {
				System.out.println("Falsche Eingabe oder Tabelle wurde nicht gefunden");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String[] getTableNames(ConnectDB cn) throws SQLException {
		DatabaseMetaData dbMetaData = cn.getConnection().getMetaData();

		ArrayList<String> tables = new ArrayList<>();

		try (ResultSet rs = dbMetaData.getTables(null, "DBU_WS22_034", "%", null)) {
			while (rs.next()) {
				tables.add(rs.getString(3));
			}
		}

		return tables.toArray(new String[tables.size()]);
	}

}
