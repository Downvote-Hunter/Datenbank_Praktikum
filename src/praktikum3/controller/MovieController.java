package praktikum3.controller;

import praktikum3.Database;
import praktikum3.db.MovieDB;
import praktikum3.dto.MovieDTO;
import praktikum3.exception.InputNotInRange;
import praktikum3.util.ReadUtil;

import java.util.List;

import static praktikum3.controller.ControllerProperties.PROPFILE;

public class MovieController {

    public static void addMovie() {

        MovieDTO movie = new MovieDTO();
        movie.setTitle(ReadUtil.readString("Title: "));

        try {
            movie.setYear(ReadUtil.readInt("Year: ", 1000, 9999));
        } catch (InputNotInRange e) {
            throw new RuntimeException(e);
        }

        try (Database db = new Database()) {
            db.connect(PROPFILE);
            MovieDB movieDB = new MovieDB(db);
            int changes = movieDB.insert(movie);
            System.out.println("Movie added");
            System.out.println(changes + " rows changed");
            System.out.println(movieDB.findByID(movie.getMID()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void deleteMovie() {
        MovieDTO movie = new MovieDTO();

        try {
            movie.setMID(ReadUtil.readInt("MID: "));

        } catch (InputNotInRange e) {
            throw new RuntimeException(e);
        }

        try (Database db = new Database()) {
            db.connect(PROPFILE);
            MovieDB movieDB = new MovieDB(db);
            movie = movieDB.findByID(movie.getMID());
            int changes = movieDB.delete(movie);

            System.out.println("Movie removed");
            System.out.println(changes + " rows changed");
            System.out.println(movie);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void findMovieByID() {

        int MID = 0;
        MovieDTO movie = new MovieDTO();

        try {
            MID = ReadUtil.readInt("MID: ");
        } catch (InputNotInRange e) {
            throw new RuntimeException(e);
        }

        try (Database db = new Database()) {
            db.connect(PROPFILE);
            MovieDB movieDB = new MovieDB(db);
            movie = movieDB.findByID(MID);
            System.out.println(movie);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void findMoviesBetweenYear() {

        int year1 = 0;
        int year2 = 0;
        List<MovieDTO> movieList;

        try {
            year1 = ReadUtil.readInt("Year 1: ");
            year2 = ReadUtil.readInt("Year 2: ");
        } catch (InputNotInRange e) {
            throw new RuntimeException(e);
        }

        try (Database db = new Database()) {
            db.connect(PROPFILE);
            MovieDB movieDB = new MovieDB(db);
            movieList = movieDB.findMoviesBetweenYears(year1, year2);

            for (MovieDTO movie : movieList) {
                System.out.println(movie);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void findMoviesPlayedByPerson() {

        int PID = 0;

        List<MovieDTO> movieList;

        try {
            PID = ReadUtil.readInt("PID: ");
        } catch (InputNotInRange e) {
            throw new RuntimeException(e);
        }

        try (Database db = new Database()) {
            db.connect(PROPFILE);
            MovieDB movieDB = new MovieDB(db);
            movieList = movieDB.findMoviesByPerson(PID);
            System.out.println("Movies played by person with PID " + PID + ":");
            System.out.println(movieList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void clear() {

        try (Database db = new Database()) {
            db.connect(PROPFILE);
            MovieDB movieDB = new MovieDB(db);
            int changes = movieDB.clear();

            System.out.println("All Movies removed");
            System.out.println(changes + " rows changed");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void updateMovie() {

        MovieDTO movie = new MovieDTO();

        try {
            movie.setMID(ReadUtil.readInt("MID: "));
            movie.setTitle(ReadUtil.readString("Title: "));
            movie.setYear(ReadUtil.readInt("Year: ", 1000, 9999));
        } catch (InputNotInRange e) {
            throw new RuntimeException(e);
        }

        try (Database db = new Database()) {
            db.connect(PROPFILE);
            MovieDB movieDB = new MovieDB(db);
            int changes = movieDB.update(movie);

            System.out.println("Movie updated");
            System.out.println(changes + " rows changed");
            System.out.println(movieDB.findByID(movie.getMID()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
