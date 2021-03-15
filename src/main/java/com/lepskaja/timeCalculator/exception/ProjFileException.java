package com.lepskaja.timeCalculator.exception;

public class ProjFileException extends Exception{
    public ProjFileException() {
    }

    public ProjFileException(String message) {
        super(message);
    }

    public ProjFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjFileException(Throwable cause) {
        super(cause);
    }
}
