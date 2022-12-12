package praktikum1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Properties;

public class ConnectDB implements AutoCloseable {

	private static Connection cn;

	private static int[] columnTypes;
	private static String[] columnNames;

	public ConnectDB() {
		cn = null;
	}

	public void connect(String propfile) {

		try (java.io.InputStream propFile = new ConnectDB().getClass().getResourceAsStream(propfile)) {
			final Properties props = new Properties(System.getProperties());
			props.load(propFile);
			cn = DriverManager.getConnection(props.getProperty("url"), props.getProperty("user"),
					props.getProperty("password"));

			System.out.println("Verbindung hergestellt");
		} catch (Exception e) {
			System.err.println("connect ERROR :\n" + e);
		}

	}

	public static void main(String[] args) {
		try (ConnectDB cn = new ConnectDB()) {
			cn.connect("/db.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean execute(String query, boolean print) {

		try (Statement stmt = cn.createStatement()) {

			if (print) {
				try (ResultSet rs = stmt.executeQuery(query)) {

					printResultSet(rs);

				}

			} else {
				stmt.executeQuery(query);
			}

			return true;

		} catch (SQLException e) {
			System.out.println("Konnte Befehl: " + query + " Nicht korrekt ausf�hren");
			System.out.println(e.getMessage());
			return false;
		}
	}

	private void printResultSet(ResultSet rs) throws SQLException {
		getColumnTypes(rs);
		printColumnNames(rs);
		System.out.println();
		System.out.println("----------------------------------------");

		while (rs.next()) {

			printColumnLine(rs);
			System.out.println();

		}
	}

	private void printColumnLine(ResultSet rs) throws SQLException {
		for (int i = 0; i < columnTypes.length; i++) {

			switch (columnTypes[i]) {
			case Types.NUMERIC:
				System.out.print(rs.getInt(columnNames[i]));
				break;
			case Types.VARCHAR:
				System.out.print(rs.getString(columnNames[i]));
				break;
			}
			System.out.print("\t");

		}
	}

	private void getColumnTypes(ResultSet rs) {

		try {
			columnTypes = new int[getColumnCount(rs)];

			for (int i = 0; i < columnTypes.length; i++) {
				columnTypes[i] = rs.getMetaData().getColumnType(i + 1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void getColumnNames(ResultSet rs) throws SQLException {
		columnNames = new String[getColumnCount(rs)];

		for (int i = 0; i < columnNames.length; i++) {
			columnNames[i] = getColumnName(rs, i);
		}

	}

	private void printColumnNames(ResultSet rs) throws SQLException {
		getColumnNames(rs);
		for (int i = 0; i < columnNames.length; i++) {
			System.out.print(columnNames[i] + "\t");

		}
	}

	private String getColumnName(ResultSet rs, int i) throws SQLException {
		return rs.getMetaData().getColumnName(i + 1);
	}

	private int getColumnCount(ResultSet rs) throws SQLException {
		return rs.getMetaData().getColumnCount();

	}

	@Override
	public void close() throws Exception {
		try {
			cn.close();
			System.out.println("Verbindung geschlossen");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void preparedExecuteInsert(String query, String[] data, boolean print) {

		try (PreparedStatement pStatement = cn.prepareStatement(query)) {

			int counter = 0;

			while (counter < data.length) {

				pStatement.setInt(1, Integer.valueOf(data[counter]));
				counter++;
				pStatement.setString(2, data[counter]);
				counter++;
				pStatement.setInt(3, Integer.valueOf(data[counter]));
				counter++;
				pStatement.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void preparedExecuteBetween(String query, int min, int max) {
		try (PreparedStatement pStatement = cn.prepareStatement(query)) {

			pStatement.setInt(1, min);
			pStatement.setInt(2, max);
			pStatement.execute();

			ResultSet rs = pStatement.getResultSet();
			printResultSet(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
