package it.unicam.cs.api.parser;

import it.unicam.cs.api.exceptions.parser.NoActionFoundException;

import java.io.*;
import java.util.Optional;

/**
 * Represents the basic functionality of a parser.
 *
 * @see Command
 * @see CommandAction
 * @author Younes Rabeh
 * @version 1.2
 */
public interface Interpretable {
    /**
     * Starts the parsing process.
     * @return an optional object, the result of the parsing
     * @throws IOException if the parser file is not readable
     * @throws NoActionFoundException if no action is found for the identifier
     */
    Optional<?> start() throws IOException, NoActionFoundException;

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

    /**
     * Set the file extension of the parser. the default value is an empty string (a file without an extension).
     * the file extension is used to filter the files that the parser can read,
     * and it doesn't reset if the file is changed.
     *
     * @param fileExtension the file extension
     * @throws IllegalArgumentException if the file extension is empty
     */
    void setFileExtension(String fileExtension) throws IllegalArgumentException;

}
