package org.os.commands;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MkdirCommandTest {
    private final MkdirCommand mkdirCommand = new MkdirCommand();
    private final String testDirName = System.getProperty("user.dir") + "/testDir";  // Absolute path to project root

    @BeforeEach
    void setUp() throws Exception {
        Files.deleteIfExists(Path.of(testDirName));
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.deleteIfExists(Path.of(testDirName));
    }

    @Test
    void testMkdir_CreatesDirectory() {
        mkdirCommand.execute(testDirName);
        File testDir = new File(testDirName);
        System.out.println("Expected directory path: " + testDir.getAbsolutePath());
        System.out.println("Exists: " + testDir.exists());
        System.out.println("Is directory: " + testDir.isDirectory());
        assertTrue(testDir.exists() && testDir.isDirectory(), "Directory should be created.");
    }
}
