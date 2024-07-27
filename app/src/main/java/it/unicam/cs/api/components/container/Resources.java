package it.unicam.cs.api.components.container;

import it.unicam.cs.App;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/**
 * Utility class for getting resources.
 * @author Younes Rabeh
 * @version 1.3
 */
public final class Resources {
    /** Prevent instantiation of this utility class. */
    private Resources() {}

    /**
     * Gets the resource file, that needs a specific file extension.
     *
     * @param path the path of the file
     * @return the resource file
     * @throws URISyntaxException if the path is not valid
     * @throws IOException if the file cannot be read
     */
    public static File getResourceFile(String path, String suffix) throws URISyntaxException, IOException {
        URL resourceURL = App.class.getResource(path); // Get the URL of the resource
        if (resourceURL == null) {
            throw new IllegalArgumentException("Resource not found: " + path);
        }

        // Check if the resource is within a JAR file
        if (resourceURL.getProtocol().equals("jar")) { // Handle JAR resources (running the JAR file)
            Path tempFile = Files.createTempFile("resource-", "." + suffix);
            try (InputStream inputStream = resourceURL.openStream()) {
                Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }
            return tempFile.toFile();
        } else { // Handle non-JAR resources {running the src code directly (using gradle)}
            String absolutePath = Paths.get(resourceURL.toURI()).toString();
            return new File(absolutePath);
        }
    }

    /**
     * Gets the resource file. No specific file extension is required.
     *
     * @param path the path of the file
     * @return the resource file
     * @throws URISyntaxException if the path is not valid
     * @throws IOException if the file cannot be read
     */
    public static File getResourceFile(String path) throws URISyntaxException, IOException {
        return getResourceFile(path, ".tmp");
    }


    /**
     * Gets the URL of the resource.
     *
     * @param path the path of the resource
     * @return the URL of the resource
     */
    public static URL getResourceURL(String path) {
        return Objects.requireNonNull(
                App.class.getResource(path));
    }

    /**
     * Gets the extension of the file.
     * @param file the file
     * @return the extension of the file
     */
    public static String getFileExtension(File file) {
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return ""; // No extension found or dot is the last character
        }
        return fileName.substring(lastDotIndex + 1);
    }
}
