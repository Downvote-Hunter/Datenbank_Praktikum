package praktikum2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUpdater {
	public static void main(String[] args) {
		try (ConnectDB cn = new ConnectDB()) {
			cn.connect("/db.properties");
			cn.getConnection().setAutoCommit(false);

			int ID = -1;
			int min = -1;
			int max = -1;
			boolean done = false;
			String query = null;
			char eingabe;

			try (Statement stmt = cn.getConnection().createStatement()) {

				query = "select MIN(PID) as PID from person";

				try (ResultSet rs = stmt.executeQuery(query)) {
					rs.next();
					min = rs.getInt("PID");
				}

				query = "select MAX(PID) as PID from person";

				try (ResultSet rs = stmt.executeQuery(query)) {
					rs.next();
					max = rs.getInt("PID");
				}

			} catch (SQLException e) {
				System.out.println("Konnte Befehl: " + query + " Nicht korrekt ausf�hren");
				System.out.println(e.getMessage());
			}

			do {
				cn.execute("select * from person", true);
				System.out.println();

				ID = ReadUtil.readInt("Geben Sie eine PID Nummer ein: ", min, max);
				String newName = ReadUtil.readString("Geben Sie einen neuen Namen ein: ");

				query = "UPDATE PERSON SET NAME = ? WHERE PID = ?";
				cn.preparedExecuteUpdatePersonName(query, ID, newName);

				System.out.println();
				cn.execute("select * from person", true);
				System.out.println();

				eingabe = ReadUtil.readString("Moechten Sie einen weiteren Namen aendern?(Y/N): ").toLowerCase()
						.charAt(0);

				switch (eingabe) {
				case 'y':
					done = false;
					break;
				default:
					done = true;
					break;

				}
			} while (!done);

			System.out.println();
			cn.execute("select * from person", true);
			System.out.println();

			eingabe = ReadUtil.readString("Sind die Aenderungen in Ordnung?(Y/N): ").toLowerCase().charAt(0);

			switch (eingabe) {
			case 'y':
				System.out.println("Commit");
				cn.getConnection().commit();
				break;
			default:
				System.out.println("Rollback");
				cn.getConnection().rollback();
				break;
			}

			System.out.println();
			cn.execute("select * from person", true);
			System.out.println();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
