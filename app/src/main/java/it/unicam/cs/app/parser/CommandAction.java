package it.unicam.cs.app.parser;


/**
 * An interface responsible for executing a command, via lambda expressions.
 *
 * @see Command
 * @author Younes Rabeh
 */
@FunctionalInterface
interface CommandAction {
    void execute(Command command);
}
