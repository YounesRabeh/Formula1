package it.unicam.cs.api.parser;


/**
 * A command is a set of instructions, that has an identifier and a set of parameters.
 * @param identifier the identifier of the command
 * @param params the parameters of the command
 * @author Younes Rabeh
 * @version 1.0
 */
public record Command(char identifier, int[] params) { }