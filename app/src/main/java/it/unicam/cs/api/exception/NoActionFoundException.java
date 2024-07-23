package it.unicam.cs.api.exception;

/**
 * An exception that is thrown when no action is found for a given identifier.
 * @see ParserException
 * @author Younes Rabeh
 * @version 1.0
 */
public class NoActionFoundException extends ParserException {
    /**
     * Constructs a new exception with a default message.
     */
    public NoActionFoundException() {
        super();
    }

    /**
     * Constructs a new exception with a custom message.
     * @param identifier the identifier that caused the exception
     */
    public NoActionFoundException(char identifier) {
        super("No action found for identifier " + "\"" + identifier + "\" ");
    }
}
