package org.os.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class RmdirCommandTest {
    private final RmdirCommand rmdirCommand = new RmdirCommand();
    private final String testDirName = "testDir";

    @BeforeEach
    void setUp() throws IOException {
        // Set up an empty directory
        new File(testDirName).mkdir();
    }

    @AfterEach
    void tearDown() {
        // Clean up
        File dir = new File(testDirName);
        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                file.delete();
            }
            dir.delete();
        }
    }

    @Test
    void testRmdir_CreatesDirectory() {
        rmdirCommand.execute(testDirName);
        assertFalse(new File(testDirName).exists(), "Directory should be removed.");
    }

    @Test
    void testRmdirNonExistentDirectory() {
        rmdirCommand.execute("nonExistentDir");
        // Confirm that no directory was affected, and error message was logged
    }

    @Test
    void testRmdirWithFiles() throws IOException {
        // Set up a non-empty directory
        File dir = new File(testDirName);
        new File(dir, "testFile.txt").createNewFile();

        rmdirCommand.execute(testDirName);
        assertTrue(dir.exists(), "Directory should not be removed if it is not empty.");
    }
}
