package it.unicam.cs.api.parser;


/**
 * An interface responsible for executing a command, via lambda expressions.
 *
 * @see Command
 * @author Younes Rabeh
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
