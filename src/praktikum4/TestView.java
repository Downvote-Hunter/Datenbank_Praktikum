import controller.CastController;
import controller.MovieController;
import controller.PersonController;
import exception.InputNotInRange;
import menue.Action;
import menue.Menue;

public class TestView {

    public static void main(String[] args) throws InputNotInRange {

        Menue mainMenue = new Menue();
        Menue movieMenue = new Menue();
        Menue personMenue = new Menue();
        Menue castMenue = new Menue();

        initMenue(mainMenue, movieMenue, personMenue, castMenue);

        mainMenue.linkMenueItem(movieMenue, mainMenue.getMenueItems().get(0));
        mainMenue.linkMenueItem(personMenue, mainMenue.getMenueItems().get(1));
        mainMenue.linkMenueItem(castMenue, mainMenue.getMenueItems().get(2));
        mainMenue.linkActionItem(mainMenue.getMenueItems().get(3), new Action() {

            @Override
            public void execute() {
                System.out.println("Exit");
                System.exit(0);
            }
        });

        movieMenue.linkMenueItem(mainMenue, movieMenue.getMenueItems().get(7));
        personMenue.linkMenueItem(mainMenue, personMenue.getMenueItems().get(6));
        castMenue.linkMenueItem(mainMenue, castMenue.getMenueItems().get(5));

        linkMovieActionItems(movieMenue);
        linkPersonActionItems(personMenue);
        linkCastActionItems(castMenue);

        mainMenue.showMenue();
    }

    private static void linkCastActionItems(Menue castMenue) {
        castMenue.linkActionItem(castMenue.getMenueItems().get(0), new Action() {
            @Override
            public void execute() {
                CastController.addCast();
            }
        });

        castMenue.linkActionItem(castMenue.getMenueItems().get(1), new Action() {
            @Override
            public void execute() {
                CastController.removeCast();
            }
        });

        castMenue.linkActionItem(castMenue.getMenueItems().get(2), new Action() {
            @Override
            public void execute() {
                CastController.clear();
            }
        });

        castMenue.linkActionItem(castMenue.getMenueItems().get(3), new Action() {
            @Override
            public void execute() {
                CastController.showCastsByMovieID();
            }
        });

        castMenue.linkActionItem(castMenue.getMenueItems().get(4), new Action() {
            @Override
            public void execute() {
                CastController.showCastsByPersonID();
            }
        });
    }

    private static void linkPersonActionItems(Menue personMenue) {
        personMenue.linkActionItem(personMenue.getMenueItems().get(0), new Action() {
            @Override
            public void execute() {
                PersonController.addPerson();
            }
        });

        personMenue.linkActionItem(personMenue.getMenueItems().get(1), new Action() {
            @Override
            public void execute() {
                PersonController.removePerson();
            }
        });

        personMenue.linkActionItem(personMenue.getMenueItems().get(2), new Action() {
            @Override
            public void execute() {
                PersonController.updatePerson();
            }
        });

        personMenue.linkActionItem(personMenue.getMenueItems().get(3), new Action() {
            @Override
            public void execute() {
                PersonController.clear();
            }
        });

        personMenue.linkActionItem(personMenue.getMenueItems().get(4), new Action() {
            @Override
            public void execute() {
                PersonController.findPersonByID();
            }
        });

        personMenue.linkActionItem(personMenue.getMenueItems().get(5), new Action() {
            @Override
            public void execute() {
                PersonController.findPersonsPlayedInMovie();
            }
        });

    }

    private static void linkMovieActionItems(Menue movieMenue) {
        movieMenue.linkActionItem(movieMenue.getMenueItems().get(0), new Action() {
            @Override
            public void execute() {
                MovieController.addMovie();
            }
        });
        movieMenue.linkActionItem(movieMenue.getMenueItems().get(1), new Action() {
            @Override
            public void execute() {
                MovieController.deleteMovie();
            }
        });
        movieMenue.linkActionItem(movieMenue.getMenueItems().get(2), new Action() {
            @Override
            public void execute() {
                MovieController.updateMovie();
            }
        });
        movieMenue.linkActionItem(movieMenue.getMenueItems().get(3), new Action() {
            @Override
            public void execute() {
                MovieController.clear();
            }
        });
        movieMenue.linkActionItem(movieMenue.getMenueItems().get(4), new Action() {
            @Override
            public void execute() {
                MovieController.findMovieByID();
            }
        });
        movieMenue.linkActionItem(movieMenue.getMenueItems().get(5), new Action() {
            @Override
            public void execute() {
                MovieController.findMoviesBetweenYear();
            }
        });
        movieMenue.linkActionItem(movieMenue.getMenueItems().get(6), new Action() {
            @Override
            public void execute() {
                MovieController.findMoviesPlayedByPerson();
            }
        });
    }

    private static void initMenue(Menue mainMenue, Menue movieMenue, Menue personMenue, Menue castMenue) {
        mainMenue.addMenueItem("1. Movie");
        mainMenue.addMenueItem("2. Person");
        mainMenue.addMenueItem("3. Cast");
        mainMenue.addMenueItem("4. Exit");

        movieMenue.addMenueItem("1. Add Movie");
        movieMenue.addMenueItem("2. Delete Movie");
        movieMenue.addMenueItem("3. Update Movie");
        movieMenue.addMenueItem("4. Delete every Movie");
        movieMenue.addMenueItem("5. Show Movie by ID");
        movieMenue.addMenueItem("6. Show movies between years");
        movieMenue.addMenueItem("7. Show movies played by Person");
        movieMenue.addMenueItem("8. Back");

        personMenue.addMenueItem("1. Add Person");
        personMenue.addMenueItem("2. Delete Person");
        personMenue.addMenueItem("3. Update Person");
        personMenue.addMenueItem("4. Delete every Person");
        personMenue.addMenueItem("5. Show Person by ID");
        personMenue.addMenueItem("6. Show persons who played in a Movie");
        personMenue.addMenueItem("7. Back");

        castMenue.addMenueItem("1. Add Cast");
        castMenue.addMenueItem("2. Delete Cast");
        castMenue.addMenueItem("3. Delete every Cast");
        castMenue.addMenueItem("4. Show Casts by Movie ID");
        castMenue.addMenueItem("5. Show Casts by Person ID");
        castMenue.addMenueItem("6. Back");
    }
}
