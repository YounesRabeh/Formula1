package it.unicam.cs.api.parser.types;


import java.io.File;

/**
 * A parser for player files
 * @see AbstractParser
 * @author Younes Rabeh
 * @version 1.0
 */
public class PlayerParser extends AbstractParser {

    public PlayerParser(File file) {
        super(file, F1_PLAYER_FILE_EXTENSION);

    }

}
