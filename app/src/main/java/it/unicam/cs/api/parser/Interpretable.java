package it.unicam.cs.api.parser;

import java.io.*;
import java.util.Optional;

/**
 * An interface responsible for interpreting the commands from a file.
 *
 * @see Command
 * @see CommandAction
 * @author Younes Rabeh
 */
interface Interpretable {
    /**
     * Start the parsing process.
     * @throws IOException if the file is not valid
     */
    void start() throws IOException;

    /**
     * Parse the data from the file.
     * @param file the file to be parsed
     * @return an optional BufferedReader
     */
    Optional<BufferedReader> getFileData(File file);

    /**
     * Add a grammar rule to the parser.
     * @param identifier the identifier of the rule
     * @param action the action to be executed
     * @see CommandAction
     */
    void addRule(char identifier, CommandAction action);



}
