package praktikum3.db;

import praktikum3.Database;
import praktikum3.dto.CastDTO;
import praktikum3.dto.MovieDTO;
import praktikum3.dto.PersonDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDB {

    private Database db = null;

    public MovieDB(Database db) {
        this.db = db;
    }

    private static void addPersonList(List<MovieDTO> movieList, PersonDB personDB) {

        List<PersonDTO> personList;

        for (MovieDTO movie : movieList) {
            personList = personDB.findPersonsByMovieSimple(movie.getMID());
            movie.setPersonList(personList);
        }
    }

    public Database getDB() {
        return db;
    }

    public void setDB(Database db) {
        this.db = db;
    }

    public List<MovieDTO> findMoviesByPerson(int PID) {

        List<MovieDTO> movieList = new ArrayList<>();

        movieList = findMoviesByPersonSimple(PID);

        addPersonList(movieList, new PersonDB(db));

        return movieList;
    }

    public List<MovieDTO> findMoviesByPersonSimple(int PID) {

        List<MovieDTO> movieList = new ArrayList<>();

        String query = "SELECT * FROM MOVIE WHERE MID IN (SELECT MID FROM CAST WHERE PID = ?)";

        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setInt(1, PID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    movieList.add(new MovieDTO(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return movieList;
    }

    public List<MovieDTO> findMoviesBetweenYears(int year1, int year2) {
        List<MovieDTO> movieList = new ArrayList<>();
        String queryMovie = "SELECT * FROM MOVIE WHERE YEAR BETWEEN ? AND ?";

        try (PreparedStatement ps = db.preparedStatement(queryMovie)) {
            ps.setInt(1, year1);
            ps.setInt(2, year2);


            try (ResultSet rsMovie = ps.executeQuery()) {
                while (rsMovie.next()) {
                    movieList.add(new MovieDTO(rsMovie));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        addPersonList(movieList, new PersonDB(db));

        return movieList;
    }

    public MovieDTO findByID(int MID) {
        MovieDTO movie = null;
        List<PersonDTO> personList = null;
        PersonDB personDB = new PersonDB(db);


        String query = "SELECT * FROM movie WHERE MID = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setInt(1, MID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    movie = new MovieDTO(rs);
                }
            }

            if (movie == null) {
                return null;
            }

            personList = personDB.findPersonsByMovieSimple(movie.getMID());
            movie.setPersonList(personList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return movie;
    }


    public int insert(MovieDTO movie) {

        final String query = "INSERT INTO MOVIE (MID, TITLE, YEAR) VALUES (?, ?, ?)";
        try (PreparedStatement ps = db.preparedStatement(query)) {

            movie.setMID(getNextID());

            ps.setInt(1, movie.getMID());
            ps.setString(2, movie.getTitle());
            ps.setInt(3, movie.getYear());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getLastID() {
        final String query = "SELECT MAX(MID) FROM MOVIE";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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


    public int update(MovieDTO movie) {
        final String query = "UPDATE MOVIE SET TITLE = ?, YEAR = ? WHERE MID = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, movie.getTitle());
            ps.setInt(2, movie.getYear());
            ps.setInt(3, movie.getMID());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int delete(MovieDTO movie) {
        final String query = "DELETE FROM MOVIE WHERE MID = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setInt(1, movie.getMID());

            removeFromCast(movie);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void removeFromCast(MovieDTO movie) {
        CastDB castDB = new CastDB(db);
        List<CastDTO> castList = castDB.findAllByMID(movie.getMID());

        for (CastDTO cast : castList) {
            castDB.delete(cast);
        }
    }


    public int clear() {

        MovieDB movieDB = new MovieDB(db);
        int changes = 0;


        for (int i = 0; i <= movieDB.getLastID(); i++) {
            MovieDTO movie = movieDB.findByID(i);
            if (movie != null) {
                changes += movieDB.delete(movie);
            }
        }
        return changes;
    }


}
