package org.os.commands;

import java.io.File;

public class RmdirCommand {
    public void execute(String dirName) {
        File directory = new File(dirName);
        if (!directory.exists()) {
            System.out.println("Error: Directory does not exist.");
        } else if (directory.isDirectory() && directory.list().length == 0) {
            if (directory.delete()) {
                System.out.println("Directory '" + dirName + "' removed successfully.");
            } else {
                System.out.println("Error: Failed to remove the directory.");
            }
        } else {
            System.out.println("Error: Directory is either not empty or is not a directory.");
        }
    }
}
