package org.os.commands;
import java.nio.file.Path;
import java.nio.file.Paths;


public class PwdCommand {
    // Method to print the current directory
    public static void printCurrentDirectory() {
        System.out.println("Current directory: " + CdCommand.getCurrentDirectory());
    }
}
