package praktikum2;

public class DatabaseUtil {

    public static void createTables(ConnectDB cn) {

        String queryTableMovie = "create table Movie (\r\n" + "    MID integer primary key,\r\n"
                + "    Title varchar(255) not null,\r\n" + "    Year smallint not null\r\n" + ")";
        cn.execute(queryTableMovie, false);
        System.out.println(queryTableMovie);
        System.out.println();

        String queryTablePerson = "CREATE TABLE Person ( \r\n" + "pid integer PRIMARY KEY,\r\n"
                + "name varchar(50) NOT NULL,\r\n"
                + "geb_datum DATE,\r\n" + "geschlecht char(1) NOT NULL)";
        cn.execute(queryTablePerson, false);
        System.out.println(queryTablePerson);
        System.out.println();

        String queryTableCast = "create table Cast (\r\n" + "  mid integer,\r\n" + "  pid integer,\r\n"
                + "  role varchar(50),\r\n"
                + "  primary key (mid, pid, role)\r\n" + ")";
        cn.execute(queryTableCast, false);
        System.out.println(queryTableCast);
        System.out.println();
    }

    private DatabaseUtil() {
    }

}
