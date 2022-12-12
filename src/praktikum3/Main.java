package praktikum3;

import java.sql.SQLException;

public class Main {

    static final String PROPFILE = "/db.properties";

    public static void main(String[] args) {

        try (Database db = new Database()) {
            db.connect(PROPFILE);
            initDB(db);

            String[] movieList = FileUtil.readFileCSV("src/movies.csv");
            String[] personList = FileUtil.readFileCSV("src/persons.csv");
            String[] castList = FileUtil.readFileCSV("src/casts.csv");

            db.preparedExecuteInsertMovie("INSERT INTO MOVIE (MID, TITLE, YEAR) VALUES (?, ?, ?)", movieList);
            db.preparedExecuteInsertPerson("INSERT INTO PERSON (PID, NAME, GEB_DATUM, GESCHLECHT) VALUES (?, ?, ?, ?)", personList);
            db.preparedExecuteInsertCast("INSERT INTO CAST (PID, MID, ROLE) VALUES (?, ?, ?)", castList);

            db.printDatabase("movie");
            System.out.println();
            db.printDatabase("person");
            System.out.println();
            db.printDatabase("cast");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void initDB(Database db) throws SQLException {
        db.execute("DROP TABLE MOVIE");
        db.execute("DROP TABLE PERSON");
        db.execute("DROP TABLE CAST");
        db.createTables();

    }

}
