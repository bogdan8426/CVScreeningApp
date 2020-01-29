package CVScreening.exceptions;

public class CVGeneratorException extends Exception {

    public CVGeneratorException(String message) {
        super(message);
    }

    public CVGeneratorException(String message, Exception e) {
        super(message, e);
    }
}
