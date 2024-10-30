package org.os.commands;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class OverwriteCommandTest {
    private String filename = "fileTest.txt"; // file to use in tests

    @Test
    public void TestOverwriteFile() throws Exception{
        String[] args = {"overwrite", filename};
        OverwriteCommand cmd = new OverwriteCommand();

        // Execute the command and overwriting into the file name
        String result = cmd.execute(args);
        assertEquals("file " + filename + " Overwritten Successfully!", result);

        // reads the content of the file after executing the command and make sure it's correct
        String content = Files.readString(Path.of(filename));
        assertEquals("overwrite", content);
    }

    @AfterEach
    public void CleanUp() throws Exception{
        // Log whether the file exists before attempting to delete it
        Path filePath = Path.of(filename);
        if (Files.exists(filePath)) {
            System.out.println("Deleting file: " + filename);
            Files.delete(filePath);
        } else {
            System.out.println("File not found, nothing to delete: " + filename);
        }
    }
}
