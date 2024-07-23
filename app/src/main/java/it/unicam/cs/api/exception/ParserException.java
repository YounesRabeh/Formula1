package it.unicam.cs.api.exception;

/**
 * An exception that is thrown when a parser error occurs.
 * @author Younes Rabeh
 */
public abstract class ParserException extends RuntimeException {
    /**
     * Constructs a new parsing exception.
     */
    public ParserException() {
        super();
    }

    /**
     * Constructs a new parsing exception with a custom message.
     * @param message the custom message
     */
    public ParserException(String message) {
        super("\n[!!!] > Parser Exception: " + message);
    }

}
