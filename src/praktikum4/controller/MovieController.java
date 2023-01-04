package controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import database.Database;
import menue.Action;
import model.Movie;
import util.ReadUtil;

public class MovieController {

    private static long getLastID() {

        AtomicLong lastID = new AtomicLong(1);

        try (Database db = new Database()) {

            db.execute(new Action() {

                @Override
                public void execute() {
                    Movie movie = db.getEM().createNamedQuery("Movie.findLastID",
                            Movie.class).setMaxResults(1).getResultList().get(0);
                    lastID.set(movie.getId());
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lastID.get();
    }

    public static void addMovie() {

        try (Database db = new Database()) {
            Movie movie = new Movie();
            movie.setId(getLastID() + 1);
            movie.setTitle(ReadUtil.readString("Title: "));
            movie.setYear(ReadUtil.readBigDecimal("Year: ", 1000, 9999));

            db.add(movie);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteMovie() {

        try (Database db = new Database()) {
            Movie movie = new Movie();
            movie.setId(ReadUtil.readLong("MID: "));
            db.delete(movie);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Movie findMovieByID() {
        Movie movie = new Movie();
        try (Database db = new Database()) {

            movie.setId(ReadUtil.readLong("MID: "));

            movie = (Movie) db.findByID(movie);
            System.out.println(movie);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;
    }

    public static void findMoviesBetweenYear() {
        List<Movie> movies = new ArrayList<>();

        try (Database db = new Database()) {
            BigDecimal year1 = ReadUtil.readBigDecimal("Year 1: ", 1000, 9999);
            BigDecimal year2 = ReadUtil.readBigDecimal("Year 2: ", 1000, 9999);

            db.execute(new Action() {

                @Override
                public void execute() {
                    movies.addAll(db.getEM().createNamedQuery("Movie.findBetweenYear", Movie.class)
                            .setParameter("year1", year1)
                            .setParameter("year2", year2)
                            .getResultList());
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    public static void findMoviesPlayedByPerson() {

        List<Movie> movies = PersonController.findPersonByID().getMovies();

        for (Movie movie : movies) {
            System.out.println(movie);
        }

    }

    public static void clear() {

        try (Database db = new Database()) {
            db.execute(new Action() {

                @Override
                public void execute() {
                    List<Movie> movies = db.getEM().createNamedQuery("Movie.findAll", Movie.class).getResultList();

                    for (Movie movie : movies) {
                        db.delete(movie);
                    }
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void updateMovie() {

        try (Database db = new Database()) {
            Movie movie = new Movie();
            movie.setId(ReadUtil.readLong("MID: "));
            movie = (Movie) db.findByID(movie);

            movie.setTitle(ReadUtil.readString("Title: "));
            movie.setYear(ReadUtil.readBigDecimal("Year: ", 1000, 9999));

            db.update(movie);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
