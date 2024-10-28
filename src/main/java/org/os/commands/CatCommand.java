package org.os.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class CatCommand {
    public void execute(String[] filenames) {
        if (filenames.length == 0) {
            // No arguments: take user input and print to screen
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter text (Press Ctrl+D to end input):");
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
            scanner.close();
        } else {
            // Arguments provided: read and display file contents
            for (String filename : filenames) {
                File file = new File(filename);

                // Check if the file exists and is readable
                if (!file.exists()) {
                    System.out.println("Error: File '" + filename + "' does not exist.");
                    continue;
                }
                if (!file.isFile()) {
                    System.out.println("Error: '" + filename + "' is not a file.");
                    continue;
                }
                if (!file.canRead()) {
                    System.out.println("Error: File '" + filename + "' cannot be read.");
                    continue;
                }

                // Read and display file content
                try {
                    Files.lines(Paths.get(filename)).forEach(System.out::println);
                } catch (IOException e) {
                    System.out.println("Error reading file '" + filename + "': " + e.getMessage());
                }
            }
        }
    }
}
