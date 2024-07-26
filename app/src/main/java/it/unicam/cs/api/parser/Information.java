package it.unicam.cs.api.parser;

import it.unicam.cs.api.parser.parsers.AbstractParser;

/**
 * The information used by the parser.
 * change the values of the constants to change the behavior of the parser.
 * @see AbstractParser
 * @author Younes Rabeh
 * @version 1.0
 */
public interface Information {
    /** The character used to indicate the start of a comment.*/
    String COMMENT_CHARACTER = "#";
    /** The separator used to separate the command identifier and its parameters.*/
    String PARSER_SEPARATOR = " ";
    /** The file extension of the drawing parser (f1Map file).*/
    String F1_MAP_FILE_EXTENSION = "f1m";
    /** The file extension of the player parser (f1Player file).*/
    String F1_PLAYER_FILE_EXTENSION = "f1p";

}
