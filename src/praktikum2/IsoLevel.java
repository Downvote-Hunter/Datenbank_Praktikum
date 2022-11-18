package praktikum2;

import java.sql.DatabaseMetaData;

public class IsoLevel {

    public static void main(String[] args) {
        try (ConnectDB cn = new ConnectDB()) {
            cn.connect("/db.properties");

			DatabaseMetaData dbMetaData = cn.getConnection().getMetaData();
            System.out.println("Database: " + dbMetaData.getDatabaseProductName());
            System.out.println("JDBC Major Version: " + dbMetaData.getJDBCMajorVersion());
            System.out.println("JDBC Minor Version: " + dbMetaData.getJDBCMinorVersion());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
