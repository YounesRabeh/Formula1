package it.unicam.cs.api.parser.parsers;


import it.unicam.cs.api.components.container.Check;
import it.unicam.cs.api.exceptions.parser.AlreadyMappedException;
import it.unicam.cs.api.exceptions.parser.NoActionFoundException;
import it.unicam.cs.api.exceptions.parser.ParsingException;
import it.unicam.cs.api.parser.api.Command;
import it.unicam.cs.api.parser.api.CommandAction;
import it.unicam.cs.api.parser.api.Information;
import it.unicam.cs.api.parser.api.Interpretable;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static it.unicam.cs.api.components.container.Resources.getFileExtension;


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
 * @version 2.2
 */
public abstract class AbstractParser implements Interpretable, Information {
    /** The file to be parsed.*/
    private File FILE;
    /** The file extension.*/
    private String fileExtension;
    /** A map that stores the commands and their corresponding actions.*/
    protected final Map<Character, CommandAction> functionMap = new HashMap<>();

    /**
     * Creates a new parser with the specified file.
     * @param file the file to be parsed
     * @throws IllegalArgumentException if the file does not exist or is a directory
     */
    AbstractParser(File file, String fileExtension) throws IllegalArgumentException {
        this.fileExtension = fileExtension;
        setFile(file);
    }

    @Override
    public Optional<?> start() throws IOException, NoActionFoundException {
        try (BufferedReader reader = getFileData(FILE).orElseThrow(() ->
                new IOException("Unable to read file: " + FILE.getAbsolutePath()))) {
            Iterator<String> lineIterator = reader.lines().iterator();
            int lineNumber = 0;
            while (lineIterator.hasNext()) {
                String line = lineIterator.next();
                lineNumber++;
                if (!line.isEmpty() && !line.startsWith(COMMENT_CHARACTER)) {
                    try {
                        executeCommand(parseLine(line));
                    } catch (Exception e) {
                        throw new ParsingException(lineNumber, e.getMessage());
                    }
                }
            }
        }
        return Optional.empty();
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
            throw new AlreadyMappedException(identifier);
        }
        functionMap.put(identifier, action);
    }

    @Override
    public void setFile(File file) {
        if (!isFileValid(file)){
            throw new IllegalArgumentException("[!!!]- \"" + file.getAbsolutePath() + "\"" + " IS NOT A VALID FILE");
        }
        this.FILE = file;
    }

    @Override
    public void setFileExtension(String fileExtension) {
        if (fileExtension == null || fileExtension.isEmpty()) {
            throw new IllegalArgumentException("[!!!]- FILE EXTENSION CANNOT BE EMPTY");
        }
        this.fileExtension = fileExtension;
    }

    /**
     * Get the current file to be parsed.
     * @return the file
     */
    public File getFile() {
        return FILE;
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
        return file.exists() && file.isFile() &&
                Check.isFileExtensionCorrect(fileExtension, getFileExtension(file));
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
                .filter(part -> !part.trim().isEmpty()) // Filter out empty strings (leading a param number)
                .mapToInt(Integer::parseInt)
                .toArray();

        return new Command(identifier, params);
    }

}



