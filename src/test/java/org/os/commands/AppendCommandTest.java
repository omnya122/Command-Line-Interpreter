package org.os.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppendCommandTest {

    private AppendCommand appendCommand;
    private String testFile;

    @BeforeEach
    public void setUp() {
        appendCommand = new AppendCommand();
        testFile = "test.txt";
        // Ensure the file is clean before tests
        try {
            Files.deleteIfExists(Paths.get(testFile));
            Files.createFile(Paths.get(testFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        // Clean up after tests
        File file = new File(testFile);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testAppendCommand() {
        String output = "Hello, World!";
        String result = appendCommand.execute(new String[]{output, testFile});
        assertEquals("Appended to test.txt successfully.", result);

        try {
            String content = new String(Files.readAllBytes(Paths.get(testFile))).trim();
            assertEquals("Hello, World!", content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
