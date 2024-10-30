package org.os.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AppendCommand {
    public String execute(String[] args){
        if(args.length != 2){
            return "Error: Incorrect number of arguments. Use 'echo <output> >> <filename>'.";
        }
        String output = args[0];
        String filename = args[1];

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(output);
            writer.newLine();
            System.out.println("Appended to " + filename + " successfully.");
        } catch (IOException e) {
            return "Error: Unable to write to " + filename + ". " + e.getMessage();
        }
        return "";
    }
}
