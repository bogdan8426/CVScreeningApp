package com.bogdanrotaru.cvscreeningapp.exceptions;

public class CVFilesReadException extends CVGeneratorException {


    public CVFilesReadException(String message) {
        super(message);
    }

    public CVFilesReadException(String s, Exception e) {
        super(s, e);
    }
}
