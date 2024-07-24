package it.unicam.cs.api.exception.parser;

/**
 * An exception that is thrown when a parser error occurs.
 * @author Younes Rabeh
 */
public class ParsingException extends RuntimeException {

    /**
     * Constructs a new parsing exception with a custom message.
     * @param message the custom message
     */
    public ParsingException(String message) {
        super("\n[!!!] > Parser Exception: " + message);
    }

    /**
     * Constructs a new parsing exception with a custom message. The line number is also provided.
     * @param lineNumber the line number where the exception occurred
     * @param message the custom message
     */
    public ParsingException(int lineNumber, String message) {
        super("\n[!!!] > Parsing Exception (line " + lineNumber + "): " + message);
    }

}