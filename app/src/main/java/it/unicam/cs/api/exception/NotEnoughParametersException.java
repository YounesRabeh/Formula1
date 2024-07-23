package it.unicam.cs.api.exception;

/**
 * An exception that is thrown when the number of parameters is not enough.
 * @see ParserException
 * @author Younes Rabeh
 */
public class NotEnoughParametersException extends ParserException {
    /**
     * Constructs a new exception with a default message.
     */
    public NotEnoughParametersException() {
        super();
    }

    /**
     * Constructs a new exception with a custom message.
     * @param expectedLength the expected length of the parameters
     */
    public NotEnoughParametersException(int expectedLength){
        super("- invalid number of parameters, expected: " + expectedLength);
    }

}
