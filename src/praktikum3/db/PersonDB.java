package praktikum3.db;

import praktikum3.Database;
import praktikum3.dto.CastDTO;
import praktikum3.dto.MovieDTO;
import praktikum3.dto.PersonDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PersonDB {

    private Database db = null;

    public PersonDB(Database db) {
        this.db = db;
    }

    private static void addMovieList(List<PersonDTO> personList, MovieDB movieDB) {

        List<MovieDTO> movieList;

        for (PersonDTO person : personList) {
            movieList = movieDB.findMoviesByPersonSimple(person.getPID());
            person.setMovieList(movieList);
        }

    }

    public Database getDB() {
        return db;
    }

    public void setDB(Database db) {
        this.db = db;
    }

    public int insert(PersonDTO person) {
        String query = "INSERT INTO PERSON (PID, NAME, GEB_DATUM, GESCHLECHT) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = db.preparedStatement(query)) {

            person.setPID(getNextID());

            ps.setInt(1, person.getPID());
            ps.setString(2, person.getName());
            ps.setDate(3, person.getGebDatum());
            ps.setString(4, person.getGeschlecht());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getNextID() {

        int testID = 1;
        boolean nextIDFound = false;
        while (!nextIDFound) {
            if (findByID(testID) == null) {
                nextIDFound = true;
            } else {
                testID++;
            }
        }
        return testID;
    }

    public int update(PersonDTO person) {
        String query = "UPDATE PERSON SET NAME = ?, GEB_DATUM = ?, GESCHLECHT = ? WHERE PID = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, person.getName());
            ps.setDate(2, person.getGebDatum());
            ps.setString(3, person.getGeschlecht());
            ps.setInt(4, person.getPID());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void removeFromCast(PersonDTO dto) {
        CastDB castDB = new CastDB(db);
        List<CastDTO> castList = castDB.findAllByPID(dto.getPID());

        for (CastDTO cast : castList) {
            castDB.delete(cast);
        }
    }

    public int delete(PersonDTO person) {
        String query = "DELETE FROM PERSON WHERE PID = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setInt(1, person.getPID());

            removeFromCast(person);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int clear() {

        PersonDB personDB = new PersonDB(db);
        int changes = 0;

        for (int i = 0; i <= personDB.getLastID(); i++) {
            PersonDTO person = personDB.findByID(i);
            if (person != null) {
                changes += personDB.delete(person);
            }
        }
        return changes;
    }

    private int getLastID() {

        final String query = "SELECT MAX(PID) FROM PERSON";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public PersonDTO findByID(int PID) {
        PersonDTO person = null;
        MovieDB movieDB = new MovieDB(db);

        String query = "SELECT * FROM PERSON WHERE PID = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setInt(1, PID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    person = new PersonDTO(rs);
                }
            }

            if (person == null) {
                return null;
            }

            person.setMovieList(movieDB.findMoviesByPersonSimple(PID));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    public List<PersonDTO> findPersonsByMovieSimple(int MID) {
        List<PersonDTO> personList = new ArrayList<>();

        String query = "SELECT * FROM PERSON WHERE PID IN (SELECT PID FROM CAST WHERE MID = ?)";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setInt(1, MID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    personList.add(new PersonDTO(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return personList;
    }

    public List<PersonDTO> findPersonsByMovie(int MID) {
        List<PersonDTO> personList;

        personList = findPersonsByMovieSimple(MID);

        addMovieList(personList, new MovieDB(db));

        return personList;
    }

    public int updateInsertPersonList(List<PersonDTO> personList) {

        int counter = 0;

        Collections.sort(personList, Comparator.comparingInt(PersonDTO::getPID));

        for (PersonDTO person : personList) {
            if (isInDatabase(person)) {
                counter += update(person);
            } else {
                counter += insert(person);
            }
        }
        return counter;
    }

    private boolean isInDatabase(PersonDTO person) {

        return findByID(person.getPID()).getPID() == person.getPID();

    }

}

