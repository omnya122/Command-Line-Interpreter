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
        File testDir = new File("test1");
        boolean dirCreated = testDir.mkdir();
        assertTrue(dirCreated, "Test directory creation failed.");

        CdCommand.changeDirectory(testDir.getAbsolutePath());

        String currentDir = CdCommand.getCurrentDirectory();
        assertEquals(testDir.getAbsolutePath(), currentDir);

        // Cleanup after test
        testDir.delete();
    }

    @Test
    public void testChangeToInvalidDirectory() {
        // Attempt to change to a non-existent directory
        String invalidDir = "invalid_directory";

        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // change the directory
        CdCommand.changeDirectory(invalidDir);

        // Restore the original System.out
        System.setOut(originalOut);

        String output = outputStream.toString();
        assertTrue(output.contains("Error: The specified path does not exist or is not a directory."));
    }

    @Test
    public void testChangeToFilePath() {
        File testFile = new File("filetest.txt");
        try {
            testFile.createNewFile(); // Ensure the file exists for the test

            // Capture the original System.out
            PrintStream originalOut = System.out;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            // Change to the test file's path
            CdCommand.changeDirectory(testFile.getAbsolutePath());

            // Restore the original System.out
            System.setOut(originalOut);

            String output = outputStream.toString();
            assertTrue(output.contains("Error: The specified path does not exist or is not a directory."));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            testFile.delete(); // Clean up the test file
        }
    }
//
//    @Test
//    public void testChangeToCurrentDirectory() {
//        // Get the current directory
//        String currentDir = CdCommand.getCurrentDirectory();
//
//        CdCommand.changeDirectory(currentDir);
//
//        assertEquals(currentDir, CdCommand.getCurrentDirectory());
//    }
}
