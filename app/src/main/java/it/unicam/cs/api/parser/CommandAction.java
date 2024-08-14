package it.unicam.cs.api.parser;


/**
 * An interface responsible for executing a command, used by the parser to execute the command.
 *
 * @see Command
 * @author Younes Rabeh
 * @version 1.0
 */
@FunctionalInterface
public interface CommandAction {
    /**
     * Executes the given command.
     * @param command the command to execute
     * @throws RuntimeException if an error occurs during the execution of the command
     */
    void execute(Command command) throws RuntimeException;
}
