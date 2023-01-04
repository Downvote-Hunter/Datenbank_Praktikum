package controller;

import java.util.List;

import database.Database;
import menue.Action;
import model.Cast;
import model.CastPK;
import model.Movie;
import model.Person;
import util.ReadUtil;

public class CastController {
    public static void addCast() {

        try (Database db = new Database()) {

            Person person = PersonController.findPersonByID();
            Movie movie = MovieController.findMovieByID();
            String role = ReadUtil.readString("Role: ");

            Cast cast = new Cast();
            CastPK castPK = new CastPK();
            castPK.setMid(movie.getId());
            castPK.setPid(person.getId());
            castPK.setRole(role);
            cast.setId(castPK);
            cast.setMovie(movie);
            cast.setPerson(person);
            movie.getCasts().add(cast);
            person.getCasts().add(cast);

            db.add(cast);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void removeCast() {

        Person person = PersonController.findPersonByID();
        Movie movie = MovieController.findMovieByID();

        try (Database db = new Database()) {

            Cast cast = new Cast();
            CastPK castPK = new CastPK();
            castPK.setMid(movie.getId());
            castPK.setPid(person.getId());

            cast.setId(castPK);
            cast = (Cast) db.findByID(cast);

            db.delete(cast);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void clear() {

        try (Database db = new Database()) {

            db.execute(new Action() {

                @Override
                public void execute() {
                    List<Cast> casts = db.getEM().createNamedQuery("Cast.findAll", Cast.class).getResultList();

                    for (Cast cast : casts) {
                        db.delete(cast);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showCastsByMovieID() {

        try (Database db = new Database()) {

            db.execute(new Action() {

                @Override
                public void execute() {
                    Movie movie = MovieController.findMovieByID();
                    List<Cast> casts = db.getEM().createNamedQuery("Cast.findByMovieId", Cast.class)
                            .setParameter("mid", movie.getId()).getResultList();
                    for (Cast cast : casts) {
                        System.out.println(cast);
                    }
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showCastsByPersonID() {

        try (Database db = new Database()) {

            db.execute(new Action() {

                @Override
                public void execute() {
                    Person person = PersonController.findPersonByID();
                    List<Cast> casts = db.getEM().createNamedQuery("Cast.findByPersonId", Cast.class)
                            .setParameter("pid", person.getId()).getResultList();
                    for (Cast cast : casts) {
                        System.out.println(cast);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
