package org.os.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

public class LsCommand {

    // Method to list files in the current directory
    public static List<String> list() {
        File currentDirectory = new File(System.getProperty("user.dir"));

        // Check if directory is accessible
        if (!currentDirectory.exists() || !currentDirectory.isDirectory()) {
            throw new IllegalStateException("Current directory is not accessible.");
        }

        // Get list of files and handle empty directory
        File[] files = currentDirectory.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("Directory is empty.");
            return new ArrayList<>();
        }

        return Arrays.stream(files)
                .map(File::getName)
                .collect(Collectors.toList());
    }

    // Method to list files in a specified directory
    public static List<String> list(String directoryPath) {
        File directory = new File(directoryPath);

        // Check if the directory exists and is accessible
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Error: The specified path does not exist or is not a directory.");
            return new ArrayList<>(); // Return an empty list if invalid
        }

        // Get list of files and handle empty directory
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("Directory is empty.");
            return new ArrayList<>();
        }

        return Arrays.stream(files)
                .map(File::getName)
                .collect(Collectors.toList());
    }
}
