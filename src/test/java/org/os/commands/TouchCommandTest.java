package org.os.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class TouchCommandTest {
    private final TouchCommand touchCommand = new TouchCommand();
    private final String testFilename = "testFile.txt";
    private final File testFile = new File(testFilename);

    @BeforeEach
    void setUp() throws IOException {
        // Ensure the file does not exist before each test
        if (testFile.exists()) {
            Files.delete(testFile.toPath());
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        // Clean up by deleting the file after each test
        if (testFile.exists()) {
            Files.delete(testFile.toPath());
        }
    }

    @Test
    void testTouchCreatesNewFile() {
        // Act
        touchCommand.touch(testFilename);

        // Assert
        assertTrue(testFile.exists(), "File should be created if it does not exist.");
    }

    @Test
    void testTouchUpdatesTimestampIfFileExists() throws IOException, InterruptedException {
        // Arrange
        touchCommand.touch(testFilename); // Create the file
        FileTime originalTime = Files.getLastModifiedTime(testFile.toPath());

        // Act - Sleep to ensure a noticeable time change
        Thread.sleep(1000);
        touchCommand.touch(testFilename);
        FileTime updatedTime = Files.getLastModifiedTime(testFile.toPath());

        // Assert
        assertNotEquals(originalTime.toMillis(), updatedTime.toMillis(), "Timestamp should be updated.");
    }

    @Test
    void testTouchOnDirectory() throws IOException {
        // Arrange
        File testDir = new File("testDir");
        if (!testDir.exists()) testDir.mkdir();

        // Act & Assert
        // Attempting to "touch" a directory should not update it as a file
        touchCommand.touch("testDir");
        assertTrue(testDir.exists() && testDir.isDirectory(), "Directory should not be affected by touch.");
    }

    @Test
    void testTouchInvalidPath() {
        // Arrange
        String invalidPath = "\0invalidFile";

        // Act & Assert
        assertDoesNotThrow(() -> touchCommand.touch(invalidPath), "Touch should handle invalid paths gracefully.");
    }
}
