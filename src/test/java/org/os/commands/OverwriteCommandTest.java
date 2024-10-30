package org.os.commands;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.os.CLI.currentDirectory;

import static org.junit.jupiter.api.Assertions.*;

public class OverwriteCommandTest {
    private final String filename = "fileTest.txt"; // file to use in tests
    @Test
    public void TestOverwriteFile() throws Exception {
        String[] args = {"overwrite", filename};
        OverwriteCommand cmd = new OverwriteCommand();
        String result = cmd.execute(args, currentDirectory);
        assertEquals("file " + filename + " Overwritten Successfully!", result);
        String content = Files.readString(Path.of(currentDirectory, filename));
        assertEquals("overwrite", content);
    }

    @AfterEach
    public void CleanUp() throws Exception{
        Path filePath = Path.of(filename);
        if (Files.exists(filePath)) {
            System.out.println("Deleting file: " + filename);
            Files.delete(filePath);
        } else {
            System.out.println("File not found, nothing to delete: " + filename);
        }
    }
}
