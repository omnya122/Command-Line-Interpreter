package org.os.commands;
import java.io.FileWriter;
import java.io.IOException;

public class OverwriteCommand {
    public String execute(String[] args){
        String output = args[0]; // text to write in file
        String filename = args[1]; // file to write into
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(output);
            return "file " + filename + " Overwritten Successfully!";
        }
        catch (IOException exception){
            return "Error Writing to file: " + exception.getMessage();
        }
    }
}
