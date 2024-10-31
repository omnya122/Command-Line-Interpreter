package org.os.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AppendCommand {
    public String execute(String[] args, String currentDirectory) {
        if (args.length != 2) {
            return "Error: Incorrect number of arguments. Use 'echo <output> >> <filename>'.";
        }
        String output = args[0];
        String filename = args[1];
        File file = new File(currentDirectory, filename);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(output);
            writer.newLine();
        } catch (IOException e) {
            return "Error: Unable to write to " + file.getPath() + ". " + e.getMessage();
        }
        return "";
    }
}