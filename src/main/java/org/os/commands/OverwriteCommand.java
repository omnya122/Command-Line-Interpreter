package org.os.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OverwriteCommand {
    public String execute(String[] args, String currentDirectory) {
        if (args.length != 2)
        {
            return "Error: Incorrect number of arguments. Use '<output> > <filename>'.";
        }
        String output = args[0];
        String filename = args[1];

        // Create the full path by combining the current directory with the filename
        File file = new File(currentDirectory, filename);

        try (FileWriter writer = new FileWriter(file))
        {
            writer.write(output);
        } catch (IOException exception)
        {
            return "Error Writing to file: " + exception.getMessage();
        }
        return "";
    }
}
