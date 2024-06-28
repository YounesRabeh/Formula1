package it.unicam.cs.api.parser;

import java.io.*;
import java.util.Optional;

/**
 * Represents the basic functionality of a parser.
 *
 * @see Command
 * @see CommandAction
 * @author Younes Rabeh
 */
public interface Interpretable {
    /**
     * Starts the parsing process.
     * @throws IOException if the parser file is not readable
     * @throws RuntimeException if an error occurs during the execution of the command
     */
    void start() throws IOException, RuntimeException;

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

    /**
     * Set a new file to be parsed.
     * @param file the new file
     * @throws IllegalArgumentException if the file does not exist or is a directory
     */
    void setFile(File file) throws IllegalArgumentException;



}
