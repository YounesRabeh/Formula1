package it.unicam.cs.api.exception.parser;

/**
 * An exception that is thrown when no action is found for a given identifier.
 * @see ParsingException
 * @author Younes Rabeh
 * @version 1.0
 */
public class NoActionFoundException extends RuntimeException {

    /**
     * Constructs a new exception with a custom message.
     * @param identifier the identifier that caused the exception
     */
    public NoActionFoundException(char identifier) {
        super("No action found for identifier " + "\"" + identifier + "\"");
    }
}
