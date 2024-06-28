package it.unicam.cs.api.parser;

/**
 * An interface that contains the information used by the parser.
 * @see AbstractParser
 * @author Younes Rabeh
 */
interface Information {
    /** The character used to indicate the start of a comment.*/
    String COMMENT_CHARACTER = "#";
    /** The separator used to separate the command identifier and its parameters.*/
    String PARSER_SEPARATOR = " ";

}
