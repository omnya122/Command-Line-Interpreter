package org.os.commands;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TouchCommand {
    public void touch(String filename)
    {
        try {
            File file = new File(filename);
            if (file.exists())
            {
                if (file.isDirectory())
                {
                    System.out.println("Error: Cannot update timestamp of a directory.");
                }
                else {
                    // If it exists, update its timestamp
                    Files.setLastModifiedTime(Paths.get(filename), java.nio.file.attribute.FileTime.fromMillis(System.currentTimeMillis()));
                }
            }
            else
            {
                // If it does not exist, create a new empty file
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}