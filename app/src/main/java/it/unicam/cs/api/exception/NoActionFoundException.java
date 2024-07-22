package it.unicam.cs.api.exception;

public class NoActionFoundException extends RuntimeException {
    public NoActionFoundException(String message) {
        super(message);
    }

    public NoActionFoundException(char identifier) {
        super("[!!!] - No action found for identifier: " + identifier);
    }
}
