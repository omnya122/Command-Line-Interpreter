package org.os.commands;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class CatCommandTest {

    private final CatCommand catCommand = new CatCommand();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out); // Restores standard output
        outContent.reset();
    }

    @Test
    void testCatNonFileInput() throws IOException {
        Path tempDir = Files.createTempDirectory("testDir");

        catCommand.execute(new String[]{tempDir.toString()});

        assertTrue(outContent.toString().contains("Error: '" + tempDir.toString() + "' is not a file."));

        // Clean up
        Files.deleteIfExists(tempDir);
    }

    @Test
    void testCatFileNotFound() {
        String nonexistentFile = "nonexistent.txt";

        catCommand.execute(new String[]{nonexistentFile});

        assertTrue(outContent.toString().contains("Error: File '" + nonexistentFile + "' does not exist."));
    }

    @Test
    void testCatFileContent() throws IOException {
        File file = new File("testFile.txt");
        String fileContent = "Hello, this is a test file!\nSecond line of the file.";

        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(fileContent);
                System.out.println("Successfully wrote to the file.");
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        String res = catCommand.execute(new String[]{"testFile.txt"});

        String actualOutput = res.replace("\r\n", "\n");

        assertEquals(fileContent, res);

        // Clean up
        file.delete();
    }

    @Test
    void testCatEmptyFile() throws IOException {
        Path tempFile = Files.createTempFile("emptyFile", ".txt");

        catCommand.execute(new String[]{tempFile.toString()});

        assertEquals("", outContent.toString()); // Expected output is empty

        // Clean up
        Files.deleteIfExists(tempFile);
    }


}