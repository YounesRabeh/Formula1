package it.unicam.cs.api.parser;


import it.unicam.cs.api.exceptions.NoActionFoundException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;


/**
 * An abstract parser that reads a file and executes the commands.
 * the parser create stores the commands and their corresponding actions.
 *
 * <ul>- {@code Identifier:} The first character (Capital for convention) of the line.</ul>
 * <ul>- {@code Parameters:} The Integers after the identifier.</ul>
 *
 * <b>THE PARSER ONLY ACCEPTS INTEGERS AS PARAMETERS </b>
 * will be changed in the future.
 * @see Interpretable
 * @see Information
 * @see Command
 * @author Younes Rabeh
 * @version 1.2
 */
abstract class AbstractParser implements Interpretable, Information {
    /** The file to be parsed.*/
    protected File FILE;
    /** A map that stores the commands and their corresponding actions.*/
    protected final Map<Character, CommandAction> functionMap = new HashMap<>();

    /**
     * Creates a new parser with the specified file.
     * @param file the file to be parsed
     * @throws IllegalArgumentException if the file does not exist or is a directory
     */
    AbstractParser(File file) throws IllegalArgumentException {
        if (!isFileValid(file)){
            throw new IllegalArgumentException("File is not valid: " + file.getAbsolutePath());
        }
        this.FILE = file;

    }

    @Override
    public void start() throws IOException, NoActionFoundException {
        try (BufferedReader reader = getFileData(FILE).orElseThrow(() ->
                new IOException("Unable to read file: " + FILE.getAbsolutePath()))) {
            reader.lines()
                    .filter(line -> !line.isEmpty() && !line.startsWith(COMMENT_CHARACTER))
                    .forEach(line -> executeCommand(parseLine(line)));
        }
    }

    @Override
    public Optional<BufferedReader> getFileData(File file) {
        try {
            return Optional.of(Files.newBufferedReader(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public void addRule(char identifier, CommandAction action) {
        if (functionMap.containsKey(identifier)) {
            throw new IllegalArgumentException("[!!!] - THE IDENTIFIER \"" + identifier +
                    "\" IS ALREADY MAPPED TO A COMMAND");
        }
        functionMap.put(identifier, action);
    }

    @Override
    public void setFile(File file) {
        if (!isFileValid(file)){
            throw new IllegalArgumentException("File is not valid: " + file.getAbsolutePath());
        }
        this.FILE = file;
    }

    /**
     * Get all the identifiers of all the commands.
     * @return a set of all the identifiers
     */
    public Set<Character> getAllIdentifiers(){
        return functionMap.keySet();
    }

    /**
     * Check if the file is valid.
     * @return true if the file exists and is not a directory
     */
    private boolean isFileValid(File file) {
        return file.exists() && file.isFile();
    }

    /**
     * Execute the command.
     * @param command the command to be executed
     * @throws RuntimeException if the command execution fails
     */
    private void executeCommand(Command command) throws NoActionFoundException {
        CommandAction action = functionMap.get(command.identifier());
        if (action != null) {
            action.execute(command);
        } else {
            throw new NoActionFoundException(command.identifier());
        }
    }

    /**
     * Parse the line and create a command containing the identifier and the parameters.
     * @param line the line to be parsed
     * @return the command
     */
    private Command parseLine(String line) {
        String[] parts = line.split(COMMENT_CHARACTER)[0].split(PARSER_SEPARATOR);
        char identifier = parts[0].charAt(0); // Get the first character of the line (O_msksdfsdf) is ok
        int[] params = Arrays.stream(parts, 1, parts.length)
                .mapToInt(Integer::parseInt)
                .toArray();

        return new Command(identifier, params);
    }
}



