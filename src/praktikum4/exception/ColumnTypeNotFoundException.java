package exception;

public class ColumnTypeNotFoundException extends Exception {

    public ColumnTypeNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
