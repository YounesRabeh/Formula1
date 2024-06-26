package it.unicam.cs.app.parser;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;


abstract class AbstractParser implements Interpretable, Information {
    /** The file to be parsed.*/
    protected final File file;
    /** A map that stores the commands and their corresponding actions.*/
    protected final Map<Character, CommandAction> functionMap = new HashMap<>();

    /**
     * Creates a new parser with the specified file.
     * @param file the file to be parsed
     */
    AbstractParser(File file) {
        this.file = file;
    }

    @Override
    public void start() throws IOException {
        if (!isFileValid()) {
            throw new IOException("File is not valid: " + file.getAbsolutePath());
        }

        try (BufferedReader reader = getFileData(file).orElseThrow(() ->
                new IOException("Unable to read file: " + file.getAbsolutePath()))) {
            reader.lines() //FIXME: add the debug line number and the line content if
                    // an IllegalArgumentException is thrown
                    .filter(line -> !line.isEmpty() && !line.startsWith(COMMENT_CHARACTER))
                    // the ignoring character , the max line length and the comment character
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

    /**
     * Get all the identifiers of all the commands.
     * @return a set of all the identifiers
     */
    public Set<Character> getAllIdentifiers(){
        return functionMap.keySet();
    }

    private boolean isFileValid() {
        return file.exists() && file.isFile();
    }

    private void executeCommand(Command command) {
        CommandAction action = functionMap.get(command.identifier());
        if (action != null) {
            action.execute(command);
        } else {
            System.err.println("No action found for command: " + command.identifier());
        }
    }

    private Command parseLine(String line) {
        String[] lineSplit = line.split(COMMENT_CHARACTER);
        String[] parts = lineSplit[0].split(PARSER_SEPARATOR);
        char identifier = parts[0].charAt(0); // Get the first character of the line (O_msksdfsdf) is ok
        int[] params = Arrays.stream(parts, 1, parts.length)
                .mapToInt(Integer::parseInt)
                .toArray();

        return new Command(identifier, params);
    }


}
