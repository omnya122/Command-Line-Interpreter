package org.os;

import java.io.File;
import java.util.Scanner;
import org.os.commands.MkdirCommand;
import org.os.commands.RmdirCommand;
import org.os.commands.AppendCommand;

import org.os.commands.OverwriteCommand;

public class CLI {

    private static Scanner scanner = new Scanner(System.in);
    private static MkdirCommand mkdirCommand = new MkdirCommand();
    private static RmdirCommand rmdirCommand = new RmdirCommand();
    private static OverwriteCommand overwriteCommand = new OverwriteCommand();
    private static AppendCommand appendCommand = new AppendCommand();


    // Entry point
    public static void main(String[] args) {
        System.out.println("Welcome to the CLI! If you want to see available commands, type 'help'.");

        // Infinite loop to process commands until 'exit' is entered
        while (true) {
            System.out.print("> ");  // CLI prompt
            String command = scanner.nextLine().trim();

            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Exiting CLI. Goodbye!");
                break;
            }

            processCommand(command);  // Process the user's command
        }
    }

    private static void processCommand(String command) {

        // Check if the command starts with 'echo '
        if (command.startsWith("echo "))
        {
            String commandWithoutEcho = command.substring(5).trim(); // Remove 'echo ' and trim spaces

            //======================== Overwrite Command '>' =======================

            if (commandWithoutEcho.contains(" > ")) {
                String[] outputAndFile = commandWithoutEcho.split(" > ", 2);
                if (outputAndFile.length == 2) {
                    String output = outputAndFile[0].trim();    // The text to write
                    String filename = outputAndFile[1].trim();  // The filename to write into
                    String result = overwriteCommand.execute(new String[]{output, filename});
                    System.out.println(result);
                } else {
                    System.out.println("Error: Incorrect format. Use 'echo <output> > <filename>'.");
                }
            }
            //======================== Append Command '>>' =======================

            else if (commandWithoutEcho.contains(" >> "))
            {
                String[] outputAndFile = commandWithoutEcho.split(" >> ", 2);
                if (outputAndFile.length == 2) {
                    String output = outputAndFile[0].trim();    // The text to append
                    String filename = outputAndFile[1].trim();  // The filename to append into
                    String result = appendCommand.execute(new String[]{output, filename});
                    System.out.println(result);
                } else {
                    System.out.println("Error: Incorrect format. Use 'echo <output> >> <filename>'.");
                }
            }
            else
            {
                System.out.println("Error: Command should include '>' or '>>' for file output.");
            }
        }
        //========================= Other Commands ==========================
        else
        {
            // Handle other commands
            String[] parts = command.split(" ");
            String mainCommand = parts[0].toLowerCase();

            switch (mainCommand) {
                case "help":
                    displayHelp();
                    break;
                case "mkdir":
                    if (parts.length < 2) {
                        System.out.println("Error: mkdir requires a directory name.");
                    } else {
                        mkdirCommand.execute(parts[1]);
                    }
                    break;
                case "rmdir":
                    if (parts.length < 2) {
                        System.out.println("Error: rmdir requires a directory name.");
                    } else {
                        rmdirCommand.execute(parts[1]);
                    }
                    break;
                default:
                    System.out.println("Error: Command not recognized.");
            }
        }
    }


    // Method to display available commands
    private static void displayHelp() {
        System.out.println("Available commands:");
        System.out.println("help                          - Show available commands.");
        System.out.println("mkdir <name>                  - Create a new directory.");
        System.out.println("rmdir <name>                  - Remove an existing directory.");
        System.out.println("echo <output> > <filename>    - Overwriting a file.");
        System.out.println("echo <output> >> <filename>   - Appending to a file.");
        System.out.println("exit                          - Exit the CLI.");
    }

    // Method to create a directory
    private static void createDirectory(String dirName) {
        File directory = new File(dirName);
        if (directory.exists()) {
            System.out.println("Error: Directory already exists.");
        } else if (directory.mkdir()) {
            System.out.println("Directory '" + dirName + "' created successfully.");
        } else {
            System.out.println("Error: Failed to create the directory.");
        }
    }

    // Method to remove a directory
    private static void removeDirectory(String dirName) {
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
