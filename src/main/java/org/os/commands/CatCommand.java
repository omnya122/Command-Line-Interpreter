package org.os.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import java.nio.file.Path;


public class CatCommand {
    public String execute(String[] filenames) {
        String result = "";
        if (filenames.length == 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter text (Press Ctrl+D to end input):");
            while (scanner.hasNextLine()) {
                result = result + scanner.nextLine() + "\r\n";
            }
            scanner.close();
            return result;
        }
        for (String filename : filenames) {
            File file = new File(filename);
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
            try {
                result = result + Files.readString(Path.of(filename)).replace("\r\n", "\n");
            } catch (IOException e) {
                System.out.println("Error reading file '" + filename + "': " + e.getMessage());
            }
        }
        return result;
    }
}
