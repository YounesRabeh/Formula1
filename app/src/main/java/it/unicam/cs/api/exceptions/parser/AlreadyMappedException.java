package it.unicam.cs.api.exceptions.parser;

import it.unicam.cs.api.parser.parsers.AbstractParser;

/**
 * An exception that is thrown when an identifier is already mapped to a command.
 * @see AbstractParser
 * @see ParsingException
 * @author Younes Rabeh
 * @version 1.0
 */
public class AlreadyMappedException extends ParsingException {
    /**
     * Constructs a new exception with a custom message.
     * @param identifier the identifier that caused the exception
     */
    public AlreadyMappedException(char identifier) {
        super("THE IDENTIFIER \"" + identifier + "\" IS ALREADY MAPPED TO A COMMAND");
    }

}
