package praktikum2;

public class ColumnTypeNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public ColumnTypeNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
