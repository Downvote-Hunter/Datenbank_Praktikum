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

    public Database getDB() {
        return db;
    }

    public void setDB(Database db) {
        this.db = db;
    }

    private Database db = null;

    public MovieDB(Database db) {
        this.db = db;
    }


    public List<MovieDTO> findMoviesByPerson(int PID) {

        List<MovieDTO> movieList = new ArrayList<>();

        String query = "SELECT * FROM MOVIE WHERE MID IN (SELECT MID FROM CAST WHERE PID = ?)";

        try (PreparedStatement ps = db.preparedStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                ps.setInt(1, PID);
                while (rs.next()) {
                    movieList.add(new MovieDTO(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        addPersonList(movieList, new PersonDB(db));

        return movieList;
    }

    private static void addPersonList(List<MovieDTO> movieList, PersonDB personDB) {
        for (int i = 0; i < movieList.size(); i++) {
            movieList.get(i).setPersonList(personDB.findPersonsByMovie(movieList.get(i).getMID()));
        }
    }

    public List<MovieDTO> findMoviesBetweenYears(int year1, int year2) {
        List<MovieDTO> movieList = new ArrayList<>();
        String queryMovie = "SELECT * FROM MOVIE WHERE YEAR BETWEEN ? AND ?";

        try (PreparedStatement psMovie = db.preparedStatement(queryMovie)) {
            psMovie.setInt(1, year1);
            psMovie.setInt(2, year2);


            try (ResultSet rsMovie = psMovie.executeQuery()) {
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
        PersonDB personDB = new PersonDB(db);

        String query = "SELECT * FROM movie WHERE MID = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setInt(1, MID);
            movie = new MovieDTO(ps.executeQuery());
            movie.setPersonList(personDB.findPersonsByMovie(movie.getMID()));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movie;
    }


    public int insert(MovieDTO movie) {

        final String query = "INSERT INTO MOVIE (MID, TITLE, YEAR) VALUES (?, ?, ?)";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setInt(1, movie.getMID());
            ps.setString(2, movie.getTitle());
            ps.setInt(3, movie.getYear());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
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
        final String query = "DELETE FROM MOVIE";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
