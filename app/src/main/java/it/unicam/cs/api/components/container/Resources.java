package it.unicam.cs.api.components.container;

import it.unicam.cs.App;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

/**
 * Utility class for getting resources.
 * @author Younes Rabeh
 */
public final class Resources {
    /** Prevent instantiation of this utility class. */
    private Resources() {}

    /**
     * Gets the file from the resources folder.
     *
     * @param path the path of the file
     * @return the file
     * @throws URISyntaxException if the path is not valid
     */
    public static File getResourceFile(String path) throws URISyntaxException {
        String absolutePath = Objects.requireNonNull(
                App.class.getResource(path)).toURI().getPath();
        return new File(absolutePath);
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
}
