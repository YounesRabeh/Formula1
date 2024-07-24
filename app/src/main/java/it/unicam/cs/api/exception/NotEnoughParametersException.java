package it.unicam.cs.api.exception;

/**
 * An exception that is thrown when the number of parameters is not enough.
 * @see ParsingException
 * @author Younes Rabeh
 * @version 1.0
 */
public class NotEnoughParametersException extends RuntimeException {

    /**
     * Constructs a new exception with a custom message.
     * @param expectedLength the expected length of the parameters
     * @param foundLength the found length of the parameters
     */
    public NotEnoughParametersException(int expectedLength, int foundLength) {
        super("Invalid number of parameters. expected " + expectedLength + ", found " + foundLength);
    }

}
