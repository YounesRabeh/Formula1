package it.unicam.cs.parser;


/**
 * A command is a set of instructions, that has an identifier and a set of parameters.
 * @param identifier the identifier of the command
 * @param params the parameters of the command
 * @author Younes Rabeh
 */
public record Command(char identifier, int[] params) {
}