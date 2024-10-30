package org.os.commands;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.os.CLI.currentDirectory;

public class PipeCommandTest {

    private PipeCommand pipeCommand;

    @BeforeEach
    public void setUp() {
        pipeCommand = new PipeCommand();
    }
    @AfterEach
    public void tearDown() {
        // Clean up any created files or directories after each test
        cleanUp("testDir");
        cleanUp("testDir2");
        cleanUp("newFile.txt");
        cleanUp("testFile.txt");
        cleanUp("testDirectory");
        cleanUp("Testing.txt");
    }

    private void cleanUp(String name) {
        File file = new File(name);
        if (file.exists()) {
            if (file.isDirectory()) {
                deleteDirectory(file);
            } else {
                file.delete();
            }
        }
    }

    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
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
    @Test
    public void testMkdirCdTouchEchoAppend() throws IOException {
        // mkdir dirName | cd dirName | touch testing.txt | echo testPiping >> outfile.txt
        String testDirectoryName = "testDir2";
        String commandLine = "mkdir " + testDirectoryName + " | cd " + testDirectoryName + " | touch Testing.txt | echo testPiping >> Testing.txt";
        String result = pipeCommand.execute(commandLine);

        File file = new File(testDirectoryName + "/Testing.txt");

        assertTrue(file.exists(), "File '" + testDirectoryName + "/Testing.txt' should exist");

        String fileContent = pipeCommand.execute("cat " + testDirectoryName + "/Testing.txt");
        assertEquals("testPiping", fileContent.trim(), "The content of 'Testing.txt' should match the appended text 'testPiping'");

        new File(testDirectoryName + "/Testing.txt").delete();
        new File(testDirectoryName).delete();
    }

    @Test
    public void testMkdirCdTouchEchoOverwrite() throws IOException
    {
        String dirName = "testDir";
        String fileName = "Test.txt";
        String commandLine = "mkdir " + dirName + " | cd " + dirName + " | touch " + fileName + " | echo overwrite > " + fileName;
        pipeCommand.execute(commandLine);

        File file = new File(currentDirectory + "/" + dirName + "/" + fileName);
        assertTrue(file.exists(), "File '" + dirName + "/" + fileName + "' should exist");

        String fileContent = Files.readString(file.toPath()).trim();
        assertEquals("overwrite", fileContent, "The content of '" + fileName + "' should match the written text 'overwrite'");

        file.delete();
        new File(currentDirectory + "/" + dirName).delete();
    }
}

