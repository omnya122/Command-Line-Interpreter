package org.os.commands;

import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

class RmCommandTest {
    private RmCommand rmCommand;

    @BeforeEach
    void setUp() {
        rmCommand = new RmCommand();
    }

    @Test
    void testDeleteFile() throws IOException {

        Path tempFile = Files.createTempFile("testFile", ".txt");
        boolean isDeleted = rmCommand.delete(tempFile.toString());

        // Check if the file was deleted
        assertTrue(isDeleted, "The file should be deleted.");
        assertFalse(Files.exists(tempFile), "The file should no longer exist.");
    }

    @Test
    void testDeleteDirectoryWithoutRecursive() throws IOException {
        Path tempDir = Files.createTempDirectory("testDir");

        // Attempt to delete the directory without recursive deletion
        boolean isDeleted = rmCommand.delete(tempDir.toString());

        // Check if the directory was deleted
        assertTrue(isDeleted, "The directory should be deleted without needing a recursive flag.");
        assertFalse(Files.exists(tempDir), "The directory should no longer exist.");
    }

    @Test
    void testDeleteDirectoryWithRecursive() throws IOException {
        Path tempDir = Files.createTempDirectory("testDir");
        Path subDir = Files.createDirectory(tempDir.resolve("subDir"));
        Path tempFile = Files.createFile(subDir.resolve("testFile.txt"));

        // Attempt to delete the directory recursively
        boolean isDeleted = rmCommand.delete(tempDir.toString());

        // Check if the directory and contents were deleted
        assertTrue(isDeleted, "The directory and contents should be deleted with recursive deletion.");
        assertFalse(Files.exists(tempDir), "The directory should no longer exist.");
    }
}
