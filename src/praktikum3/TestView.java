package praktikum3;

import praktikum3.exception.InputNotInRange;
import praktikum3.util.ReadUtil;

public class TestView {
    public static void main(String[] args) throws InputNotInRange {

        // TODO Maybe it's better to make a Menue class

        openMenue();
        int input = ReadUtil.readInt(1, 3);
        openMenue(input);


    }

    private static void openMenue(int input) {
        switch (input) {
            case 1:
                openMovieMenue();
                break;
            case 2:
                openPersonMenue();
                break;
            case 3:
                openCastMenue();
                break;
            default:
                System.out.println("Error");
                break;
        }
    }

    private static void openCastMenue() {
        System.out.println("1. Add Cast");
        System.out.println("2. Delete Cast");
        System.out.println("3. Delete every Cast");
        System.out.println("4. Show Casts by Movie ID");
        System.out.println("5. Show Casts by Person ID");

        // TODO

    }

    private static void openPersonMenue() {
        System.out.println("1. Add Person");
        System.out.println("2. Delete Person");
        System.out.println("3. Update Person");
        System.out.println("4. Delete every Person");
        System.out.println("5. Show person by ID");
        System.out.println("6. Show persons who played in a Movie");
        System.out.println("7. Add and Update multiple persons");

        // TODO
    }

    private static void openMovieMenue() {
        System.out.println("1. Add Movie");
        System.out.println("2. Delete Movie");
        System.out.println("3. Update Movie");
        System.out.println("4. Delete every Movie");
        System.out.println("5. Show Movie by ID");
        System.out.println("6. Show movies between years");
        System.out.println("7. Show movies played by Person");

        // TODO
    }

    private static void openMenue() {

        System.out.println("1. Film");
        System.out.println("2. Person");
        System.out.println("3. Cast");
    }
}
