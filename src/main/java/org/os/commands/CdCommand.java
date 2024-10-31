package org.os.commands;

import java.io.File;

public class CdCommand {
    private static String currentDirectory = System.getProperty("user.dir");

    public static void changeDirectory(String path) {
        File newDirectory = new File(path);

        if (!newDirectory.exists() || !newDirectory.isDirectory()) {
            System.out.println("Error: The specified path does not exist or is not a directory.");
            return;
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

