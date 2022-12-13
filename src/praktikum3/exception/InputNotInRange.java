package praktikum3.exception;

public class InputNotInRange extends Exception {

    public InputNotInRange(int min, int max) {

        super("Input is not between " + min + " and " + max);


    }

}
