package praktikum2;

public class InputNotInRange extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InputNotInRange(int min, int max) {

        super("Input is not between " + min + " and " + max);


    }

}
