package org.os.commands;
import java.io.File;
import java.util.Scanner;

public class RmCommand {
    private Scanner scanner = new Scanner(System.in);
    public boolean delete(String path) {
        File file = new File(path);

        while (!file.exists()) {
            System.out.println("Please enter a valid existing file name.");
            System.out.print("Enter filename: ");
            path = scanner.nextLine(); // Read the new filename from the user
            file = new File(path); // Create a new File object with the updated path
        }


        boolean isDeleted = deleteRecursively(file);

        if (isDeleted) {
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("Failed to delete the file.");
        }

        return isDeleted;
    }


    private boolean deleteRecursively(File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                deleteRecursively(child);  // Recursively delete contents
            }
        }
        return file.delete();
    }
}
