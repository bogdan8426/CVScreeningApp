package CVScreening.exceptions;

public class CVFilesWriteException extends CVGeneratorException {
    public CVFilesWriteException(String message) {
        super(message);
    }

    public CVFilesWriteException(String message, Exception e){
        super(message, e);
    }
}
