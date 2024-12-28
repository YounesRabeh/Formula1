package it.unicam.cs.api.components.container;

import it.unicam.cs.App;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.nio.file.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Utility class for getting resources.
 * @author Younes Rabeh
 * @version 1.4
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
        URL resourceURL = getResourceURL(path); // Get the URL of the resource

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
     * Gets the input stream of the resource.
     *
     * @param path the path of the resource
     * @return the input stream of the resource
     */
    public static InputStream getResourceStream(String path) {
        Objects.requireNonNull(path, "Resource path cannot be null");
        InputStream resourceStream = App.class.getResourceAsStream(path);
        if (resourceStream == null) {
            throw new IllegalArgumentException("Resource not found: " + path);
        }
        return resourceStream;
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

    /**
     * Gets the directory stream of all files having the specified glob pattern in a directory.
     *
     * @param directoryPath the path of the folder
     * @param glob the glob pattern
     * @return the directory stream
     * @throws URISyntaxException if the path is not valid
     * @throws IOException if the file cannot be read
     */
    public static DirectoryStream<Path> getDirectoryStreamOfAllIn(
            String directoryPath,
            String glob
    ) throws URISyntaxException, IOException {
        Path folderPath = getResourceFile(directoryPath).toPath();
        return Files.newDirectoryStream(folderPath, glob);
    }

    /**
     * Gets the icons from the specified folder.
     *
     * @param iconFolderPath the path of the folder
     * @return the icons
     * @throws URISyntaxException if the path is not valid
     * @throws IOException if the file cannot be read
     */
    public static Collection<Image> getIcons(String iconFolderPath) throws URISyntaxException, IOException {
        List<Image> icons = new ArrayList<>();

        for (Path path : getDirectoryStreamOfAllIn(iconFolderPath, "*.png")) {
            icons.add(new Image(path.toUri().toString()));
        }
        return icons;
    }
}
