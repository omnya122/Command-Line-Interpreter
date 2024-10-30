package org.os.commands;

import java.io.File;

public class CdCommand {
    // Store the current directory
    private static String currentDirectory = System.getProperty("user.dir");

    // Method to change the directory
    public static void changeDirectory(String path) {
        File newDirectory = new File(path);

        // Check if the specified directory exists
        if (!newDirectory.exists() || !newDirectory.isDirectory()) {
            System.out.println("Error: The specified path does not exist or is not a directory.");
            return; // Exit the method if the directory is invalid
        }

        // Update the stored current directory
        currentDirectory = newDirectory.getAbsolutePath();
        System.setProperty("user.dir", currentDirectory); // Update system property
        System.out.println("Changed directory to: " + currentDirectory);
    }

    // Method to get the current directory
    public static String getCurrentDirectory() {
        return currentDirectory;
    }
}
