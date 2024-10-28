package org.os.commands;

import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CatCommandTest {

    private final CatCommand catCommand = new CatCommand();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent)); // Redirects standard output for testing
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out); // Restores standard output
        outContent.reset();
    }



    @Test
    void testCatNonFileInput() throws IOException {
        // Arrange
        Path tempDir = Files.createTempDirectory("testDir");

        // Act
        catCommand.execute(new String[]{tempDir.toString()});

        // Assert
        assertTrue(outContent.toString().contains("Error: '" + tempDir.toString() + "' is not a file."));

        // Clean up
        Files.deleteIfExists(tempDir);
    }
    @Test
    void testCatFileNotFound() {
        // Arrange
        String nonexistentFile = "nonexistent.txt";

        // Act
        catCommand.execute(new String[]{nonexistentFile});

        // Assert
        assertTrue(outContent.toString().contains("Error: File '" + nonexistentFile + "' does not exist."));
    }
}

