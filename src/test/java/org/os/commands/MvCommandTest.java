package org.os.commands;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;
public class MvCommandTest {

    private MvCommand moveCommand;
    private Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        moveCommand = new MvCommand();
        tempDir = Files.createTempDirectory("testDir");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walk(tempDir)
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    void testMoveFile() throws IOException {
        // Create a test file in the temp directory
        Path sourceFile = Files.createFile(tempDir.resolve("testFile.txt"));
        Path destinationFile = tempDir.resolve("newDir/testFile.txt");

        // Create the destination directory
        Files.createDirectory(tempDir.resolve("newDir"));

        // Move the file
        moveCommand.move(sourceFile.toString(), destinationFile.toString());

        // Verify the file has been moved
        assertTrue(Files.exists(destinationFile), "File should be moved to the new location");
        assertFalse(Files.exists(sourceFile), "Source file should no longer exist");
    }

    @Test
    void testRenameFile() throws IOException {
        // Create a test file in the temp directory
        Path sourceFile = Files.createFile(tempDir.resolve("testFile.txt"));
        Path renamedFile = tempDir.resolve("renamedFile.txt");

        // Rename the file
        moveCommand.move(sourceFile.toString(), renamedFile.toString());

        // Verify the file has been renamed
        assertTrue(Files.exists(renamedFile), "File should be renamed");
        assertFalse(Files.exists(sourceFile), "Original file should no longer exist");
    }

    @Test
    void testMoveNonExistentFile() {
        // Try to move a nonexistent file
        Exception exception = assertThrows(IOException.class, () ->
                moveCommand.move(tempDir.resolve("nonExistentFile.txt").toString(),
                        tempDir.resolve("somewhere").toString())
        );

        assertTrue(exception.getMessage().contains("Source path does not exist"));
    }

    @Test
    void testMoveDirectory() throws IOException {
        // Create a test directory with a file inside
        Path sourceDir = Files.createDirectory(tempDir.resolve("sourceDir"));
        Files.createFile(sourceDir.resolve("fileInDir.txt"));
        Path destinationDir = tempDir.resolve("destinationDir");

        // Move the directory
        moveCommand.move(sourceDir.toString(), destinationDir.toString());

    }}