package org.os.commands;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PipeCommandTest {

    private PipeCommand pipeCommand;

    @BeforeEach
    public void setUp() {
        pipeCommand = new PipeCommand();
    }

    @Test
    public void testExecuteMkdirCommand_Success() {
        String result = pipeCommand.execute("mkdir testDir");
        assertEquals("", result);
        assertTrue(new File("testDir").exists());
    }

    @Test
    public void testExecuteRmdirCommand_Success() {
        new File("testDir").mkdir();
        String result = pipeCommand.execute("rmdir testDir");
        assertEquals("", result);
        assertFalse(new File("testDir").exists());
    }

    @Test
    public void testExecuteTouchCommand_Success() {
        String result = pipeCommand.execute("touch newFile.txt");
        assertEquals("", result);
        assertTrue(new File("newFile.txt").exists());
    }
    @Test
    public void testMkdirAndRmdir() {
        String commandLine = "mkdir tempDir | rmdir tempDir";
        String result = pipeCommand.execute(commandLine);

        // Verify that the directory was created and then removed
        assertFalse(new File("tempDir").exists(), "Directory 'tempDir' should be removed after rmdir.");
    }

    @Test
    public void testMkdirTouch() throws IOException {
        String commandLine = "mkdir testDirectory | touch testDirectory/TestTest.txt";
        String result = pipeCommand.execute(commandLine);

        File file = new File("testDirectory/TestTest.txt");

        assertTrue(file.exists(), "File 'testDirectory/TestTest.txt' should exist");
        System.out.println("File exists: " + file.exists());
        System.out.println("File path: " + file.getAbsolutePath());
        System.out.println("Current working directory: " + System.getProperty("user.dir"));
    }
    @Test
    public void testMkdirCDTouch() throws IOException {
        String commandLine = "mkdir testDirectory | cd testDirectory | touch TestTest.txt";
        String result = pipeCommand.execute(commandLine);

        File file = new File("testDirectory/TestTest.txt");

        assertTrue(file.exists(), "File 'testDirectory/TestTest.txt' should exist");
        System.out.println("File exists: " + file.exists());
        System.out.println("File path: " + file.getAbsolutePath());
        System.out.println("Current working directory: " + System.getProperty("user.dir"));
    }


    @Test
    public void testAppendAndCatCommands() throws IOException {
        // Define the file name and expected content
        String fileName = "testFile.txt";
        String initialContent = "First line";
        String appendedContent = "Second line";
        PipeCommand pipeCommand = new PipeCommand();
        String appendCommandLine1 = "echo " + initialContent + " >> " + fileName;
        String appendCommandLine2 = "echo " + appendedContent + " >> " + fileName;
        String catCommandLine = "cat " + fileName;
        String result = pipeCommand.execute("touch " + fileName + " | " + appendCommandLine1 + " | " + appendCommandLine2 + " | " + catCommandLine);
        String expectedContent = initialContent + "\n" + appendedContent;
        assertEquals(expectedContent, result.trim(), "The content of the file should match the appended text.");

        // Clean up
        new File(fileName).delete();
    }

    @Test
    public void testOverwriteCat() throws IOException {
        String fileName = "testFile.txt";
        String initialContent = "First line";
        String overwrittenContent = "Second line";
        PipeCommand pipeCommand = new PipeCommand();
        String appendCommandLine1 = "echo " + initialContent + " > " + fileName;
        String appendCommandLine2 = "echo " + overwrittenContent + " > " + fileName;
        String catCommandLine = "cat " + fileName;
        String result = pipeCommand.execute("touch " + fileName + " | " + appendCommandLine1 + " | " + appendCommandLine2 + " | " + catCommandLine);
        assertEquals(overwrittenContent, result.trim(), "The content of the file should match the appended text.");

        // Clean up
        new File(fileName).delete();
    }
}

