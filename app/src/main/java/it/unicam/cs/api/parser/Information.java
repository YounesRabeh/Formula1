package it.unicam.cs.api.parser;

/**
 * The information used by the parser.
 * change the values of the constants to change the behavior of the parser.
 * @see AbstractParser
 * @author Younes Rabeh
 */
public interface Information {
    /** The character used to indicate the start of a comment.*/
    String COMMENT_CHARACTER = "#";
    /** The separator used to separate the command identifier and its parameters.*/
    String PARSER_SEPARATOR = " ";

}
