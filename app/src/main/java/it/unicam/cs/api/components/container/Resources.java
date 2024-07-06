package it.unicam.cs.api.components.container;

import it.unicam.cs.App;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

public final class Resources {
    private Resources() {}

    public static File getParserFile(String path) throws URISyntaxException {
        String absolutePath = Objects.requireNonNull(
                App.class.getResource(path)).toURI().getPath();
        return new File(absolutePath);
    }
}
