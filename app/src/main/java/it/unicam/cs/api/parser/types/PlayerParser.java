package it.unicam.cs.api.parser.types;


import it.unicam.cs.api.parser.Information;

import java.io.File;

public class PlayerParser extends AbstractParser {

    public PlayerParser(File file) {
        super(file, Information.F1_PLAYER_FILE_EXTENSION);
        // TODO: add the default commands
        // TODO: add the player logic
    }

}
