package org.os.commands;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class CdCommandTest {
    private final CdCommand cdCommand = new CdCommand();

    @Test
    public void testChangeToValidDirectory() {
        // Create a test directory
        File testDir = new File("test1");
        boolean dirCreated = testDir.mkdir(); // Ensure the directory exists for the test
        assertTrue(dirCreated, "Test directory creation failed."); // Assert directory was created

        // Change to the test directory
        CdCommand.changeDirectory(testDir.getAbsolutePath());

        // Assert that the current directory has changed
        String currentDir = CdCommand.getCurrentDirectory(); // Use the method from CdCommand
        assertEquals(testDir.getAbsolutePath(), currentDir); // Check against CdCommand's stored directory

        // Cleanup after test (after assertions)
        testDir.delete(); // Clean up the test directory
    }

    @Test
    public void testChangeToInvalidDirectory() {
        // Attempt to change to a non-existent directory
        String invalidDir = "invalid_directory";

        // Capture the original System.out
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Call the method to change the directory
        CdCommand.changeDirectory(invalidDir);

        // Restore the original System.out
        System.setOut(originalOut);

        // Assert that the output contains the expected error message
        String output = outputStream.toString();
        assertTrue(output.contains("Error: The specified path does not exist or is not a directory."));
    }

    @Test
    public void testChangeToFilePath() {
        // Create a test file
        File testFile = new File("filetest.txt");
        try {
            testFile.createNewFile(); // Ensure the file exists for the test

            // Capture the original System.out
            PrintStream originalOut = System.out;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            // Attempt to change to the test file's path
            CdCommand.changeDirectory(testFile.getAbsolutePath());

            // Restore the original System.out
            System.setOut(originalOut);

            // Assert that the output contains the expected error message
            String output = outputStream.toString();
            assertTrue(output.contains("Error: The specified path does not exist or is not a directory."));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            testFile.delete(); // Clean up the test file
        }
    }

    @Test
    public void testChangeToCurrentDirectory() {
        // Get the current directory
        String currentDir = CdCommand.getCurrentDirectory();

        // Attempt to change to the current directory
        CdCommand.changeDirectory(currentDir);

        // Assert that the current directory remains the same
        assertEquals(currentDir, CdCommand.getCurrentDirectory());
    }
}
