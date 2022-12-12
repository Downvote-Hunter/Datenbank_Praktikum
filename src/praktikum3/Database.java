package praktikum3;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Properties;

public class Database implements AutoCloseable {

    private Connection cn;
    private int[] columnTypes;
    private String[] columnNames;
    private ResultSet rs;
    private String separator = "\t";
    private Statement stmt;

    public Database() {
    }

    public void createTables() throws SQLException {

        String queryTableMovie = "create table Movie (\r\n" + "    MID integer primary key,\r\n" + "    Title varchar(255) not null,\r\n" + "    Year smallint not null\r\n" + ")";
        execute(queryTableMovie);
        System.out.println(queryTableMovie);
        System.out.println();

        String queryTablePerson = "CREATE TABLE Person ( \r\n" + "pid integer PRIMARY KEY,\r\n" + "name varchar(50) NOT NULL,\r\n" + "geb_datum DATE,\r\n" + "geschlecht char(1) NOT NULL)";
        execute(queryTablePerson);
        System.out.println(queryTablePerson);
        System.out.println();

        String queryTableCast = "create table Cast (\r\n" + "  mid integer,\r\n" + "  pid integer,\r\n" + "  role varchar(50),\r\n" + "  primary key (mid, pid, role)\r\n" + ")";
        execute(queryTableCast);
        System.out.println(queryTableCast);
        System.out.println();
    }

    int[] getColumnTypes() {

        try {
            columnTypes = new int[getColumnCount()];

            for (int i = 0; i < columnTypes.length; i++) {
                columnTypes[i] = rs.getMetaData().getColumnType(i + 1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnTypes;

    }

    private String getColumnName(int i) throws SQLException {
        return rs.getMetaData().getColumnName(i + 1);
    }

    private int getColumnCount() throws SQLException {
        return rs.getMetaData().getColumnCount();

    }

    String[] getColumnNames() throws SQLException {
        columnNames = new String[getColumnCount()];

        for (int i = 0; i < columnNames.length; i++) {
            columnNames[i] = getColumnName(i);
        }
        return columnNames;
    }

    public Database preparedExecuteBetween(String query, int min, int max) {

        try (PreparedStatement pStatement = cn.prepareStatement(query)) {

            pStatement.setInt(1, min);
            pStatement.setInt(2, max);
            pStatement.execute();

            rs = pStatement.getResultSet();
            return this;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void preparedExecuteUpdatePersonName(String query, int personID, String name) {
        try (PreparedStatement pStatement = cn.prepareStatement(query)) {

            pStatement.setString(1, name);
            pStatement.setInt(2, personID);
            pStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void preparedExecuteInsertPerson(String query, String[] data) {
        try (PreparedStatement pStatement = cn.prepareStatement(query)) {

            int counter = 0;

            while (counter < data.length) {

                pStatement.setInt(1, Integer.parseInt(data[counter]));
                counter++;
                pStatement.setString(2, data[counter]);
                counter++;
                pStatement.setDate(3, DateUtil.parse(data[counter]));
                counter++;
                pStatement.setString(4, data[counter]);
                counter++;
                pStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void printDatabase(String database) throws Exception {
        printDatabase(database, "\t");
    }

    public void printDatabase(String database, String separator) throws Exception {

        execute("select * from " + database);
        setSeparator(separator);
        System.out.println("Database: " + database);
        print();
    }

    private void setSeparator(String separator) {
        this.separator = separator;
    }

    public void printDatabase(String database, int min, int max) {

        String query = "select * from " + database + " where MID between ? and ?";
        preparedExecuteBetween(query, min, max);
    }

    public void preparedExecuteInsertMovie(String query, String[] data) {

        try (PreparedStatement pStatement = cn.prepareStatement(query)) {

            int counter = 0;

            while (counter < data.length) {

                pStatement.setInt(1, Integer.parseInt(data[counter]));
                counter++;
                pStatement.setString(2, data[counter]);
                counter++;
                pStatement.setInt(3, Integer.parseInt(data[counter]));
                counter++;
                pStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void preparedExecuteInsertCast(String query, String[] data) {
        try (PreparedStatement pStatement = cn.prepareStatement(query)) {

            int counter = 0;

            while (counter < data.length) {

                pStatement.setInt(1, Integer.parseInt(data[counter]));
                counter++;
                pStatement.setInt(2, Integer.parseInt(data[counter]));
                counter++;
                pStatement.setString(3, data[counter]);
                counter++;
                pStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void connect(String propfile) {

        try (java.io.InputStream propFile = Database.class.getResourceAsStream(propfile)) {
            final Properties props = new Properties(System.getProperties());
            props.load(propFile);
            cn = DriverManager.getConnection(props.getProperty("url"), props.getProperty("user"), props.getProperty("password"));

            System.out.println("Verbindung hergestellt");
        } catch (Exception e) {
            System.err.println("connect ERROR :\n" + e);
        }

    }

    @Override
    public void close() throws Exception {
        cn.close();
        rs.close();
        stmt.close();
        System.out.println("Verbindung geschlossen");
    }

    private void printColumnNames() throws SQLException {
        for (String columnName : getColumnNames()) {
            System.out.print(columnName + separator);

        }
    }


    private void printColumnLine() throws SQLException, ColumnTypeNotFoundException {
        int[] columnTypes = getColumnTypes();
        String[] columnNames = getColumnNames();

        for (int i = 0; i < getColumnTypes().length; i++) {

            switch (columnTypes[i]) {
                case Types.NUMERIC:
                    System.out.print(rs.getInt(columnNames[i]));
                    break;
                case Types.VARCHAR:
                case Types.CHAR:
                    System.out.print(rs.getString(columnNames[i]));
                    break;
                case Types.TIMESTAMP:
                    System.out.print(rs.getDate(columnNames[i]));
                    break;
                default:
                    throw new ColumnTypeNotFoundException("Column type couldn't be found: " + columnNames[i]);

            }

            if ((i + 1) < columnTypes.length) {
                System.out.print(separator);
            }

        }
    }

    public void print() throws SQLException, ColumnTypeNotFoundException {
        getColumnTypes();
        printColumnNames();
        System.out.println();
        System.out.println("----------------------------------------");

        while (rs.next()) {
            printColumnLine();
            System.out.println();
        }
    }

    public Database execute(String query) throws SQLException {

        stmt = cn.createStatement();
        rs = stmt.executeQuery(query);
        return this;


    }
}
