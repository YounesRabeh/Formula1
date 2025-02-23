package it.unicam.cs;

import it.unicam.cs.api.parser.types.DrawingParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DrawingParserTest {
    private DrawingParser parser;

    @BeforeEach
    void setUp() {
        try {
            parser = new DrawingParser(new File(Objects.requireNonNull(
                    getClass().getResource("/it/unicam/cs/data/maps/default/e2d44868e75b32ef84051917862d296a4f92b574.f1m")).toURI()), ".f1m");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testParser() {
        try {
            parser.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCommands() {
        Set<Character> symbols = new HashSet<>() {};
        Character[] commands = {'@', 'B', '£', 'C', '$', 'F', 'G', '§', 'K', 'L', 'M', 'O', 'P', 'Q', 'R', 'W', '>'};
        symbols.addAll(List.of(commands));
        assertEquals(parser.getAllIdentifiers(), symbols); ;

    }
}