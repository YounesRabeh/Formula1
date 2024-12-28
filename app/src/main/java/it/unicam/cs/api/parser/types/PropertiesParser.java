package it.unicam.cs.api.parser.types;

import it.unicam.cs.api.components.container.Resources;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;



/**
 * A parser for multiple properties files, located in the resources (settings) folder.
 * @see Properties
 * @author Younes
 * @version 1.5
 */
public final class PropertiesParser {
    /** The map that stores the properties files. */
    private static final Map<String, Properties> propertiesMap = new HashMap<>();
    /** The config properties file of the app.*/
    public static final String CONFIG_PROPERTIES_PATH = "/it/unicam/cs/settings/config.properties";
    /** The properties file of the parser.*/
    static final String PARSER_PROPERTIES_PATH = "/it/unicam/cs/settings/parser.properties";

    static {
        try {
            loadPropertiesFile(CONFIG_PROPERTIES_PATH);
            loadPropertiesFile(PARSER_PROPERTIES_PATH);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Load the properties file from the settings folder, preserving blank spaces in values.
     * @param file the file of the properties file
     */
    public static void loadPropertiesFile(String file) throws IOException {
        InputStream input = Resources.getResourceStream(file);

        Properties properties = new Properties();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue; // Skip comments and empty lines
                }
                int separatorIndex = line.indexOf('=');
                if (separatorIndex > 0) {
                    String key = line.substring(0, separatorIndex).trim();
                    String value = line.substring(separatorIndex + 1); // Preserve trailing spaces
                    properties.setProperty(key, value);
                }
            }
        }
        propertiesMap.put(file, properties);
    }

    /**
     * Gets the value of the key from the specified properties file.
     * @param path the path to the properties file
     * @param key the key
     * @return the value of the key
     */
    public static String getProperty(String path, String key) {
        Properties properties = propertiesMap.get(path);
        return properties != null ? properties.getProperty(key) : null;
    }

    /**
     * Gets the integer value of the key from the specified properties file.
     * @param path the path to the properties file
     * @param key the key
     * @return the integer value of the key
     */
    public static int getIntProperty(String path, String key) {
        Properties properties = propertiesMap.get(path);
        return properties != null ? Integer.parseInt(properties.getProperty(key)) : 0;
    }
}