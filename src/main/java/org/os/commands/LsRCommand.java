package org.os.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LsRCommand {

    public static List<String> listRecursive() {
        List<String> results = new ArrayList<>();
        File currentDirectory = new File(System.getProperty("user.dir"));

        // Check if directory is accessible
        if (!currentDirectory.exists() || !currentDirectory.isDirectory()) {
            System.out.println("Error: Current directory is not accessible.");
            return results; // Return an empty list if the directory is not accessible
        }

        listFilesRecursive(currentDirectory, results);

        // Handle case if directory is empty
        if (results.isEmpty()) {
            System.out.println("Directory is empty.");
        }

        return results;
    }

    private static void listFilesRecursive(File directory, List<String> results) {
        File[] files = directory.listFiles();

        // Handle potential null result from listFiles()
        if (files == null) {
            System.out.println("Error: Unable to access contents of " + directory.getPath());
            return;
        }

        for (File file : files) {
            results.add(file.getPath());
            if (file.isDirectory()) {
                listFilesRecursive(file, results);
            }
        }
    }
}
