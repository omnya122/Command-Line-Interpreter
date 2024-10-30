package org.os;
import java.io.IOException;
import java.util.List;

import java.io.File;
import java.util.Scanner;

import org.os.commands.*;

public class CLI {

    private static Scanner scanner = new Scanner(System.in);
    private static MkdirCommand mkdirCommand = new MkdirCommand();
    private static RmdirCommand rmdirCommand = new RmdirCommand();
    private static TouchCommand touchCommand = new TouchCommand();
    private  static RmCommand rmCommand = new RmCommand();
    private static MvCommand mvCommand = new MvCommand();
    private static CatCommand catCommand = new CatCommand();
    private static OverwriteCommand overwriteCommand = new OverwriteCommand();
    private static AppendCommand appendCommand = new AppendCommand();
    private static PwdCommand pwdCommand = new PwdCommand();
    private static CdCommand cdCommand = new CdCommand();
    private static LsCommand lsCommand = new LsCommand();
    private static LsRCommand lsRCommand = new LsRCommand();


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

    // Method to process the command
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
        else{
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

                case "touch":
                    if (parts.length < 2) {
                        System.out.println("Error: touch requires a file name.");
                    } else {
                        touchCommand.touch(parts[1]);
                    }
                    break;
                case "rm":
                    if (parts.length < 2) {
                        System.out.println("Error: rm requires a file name.");
                    } else {
                        rmCommand.delete(parts[1]);
                    }
                    break;
                case "mv":
                    if (parts.length < 3) {
                        System.out.println("Error: mv requires a source and a destination path.");
                    } else {
                        try {
                            mvCommand.move(parts[1], parts[2]);
                        } catch (IOException e) {
                            System.out.println("Error during move operation: " + e.getMessage());
                        }
                    }
                    break;
                case "cat":
                    handleCatCommand(parts);
                    break;
                case "cd":
                    if (parts.length < 2) {
                        System.out.println("Error: cd requires a directory path.");
                    } else {
                        cdCommand.changeDirectory(parts[1]);
                    }
                    break;

                case "pwd":
                    PwdCommand.printCurrentDirectory();
                    break;



                case "ls":
                    if (parts.length == 1) {
                        // No arguments, list current directory
                        List<String> currentContents = LsCommand.list(); // Call the normal ls method
                        if (!currentContents.isEmpty()) {
                            String joinedContents = String.join(", ", currentContents);
                            System.out.println("Contents of current directory: " + joinedContents);
                        }
                    } else if (parts.length == 2 && parts[1].equals("-r")) {
                        // Handle recursive listing
                        List<String> recursiveContents = LsRCommand.listRecursive(); // Call the recursive ls method
                        if (!recursiveContents.isEmpty()) {
                            String joinedContents = String.join(", ", recursiveContents);
                            System.out.println("Contents of current directory (recursive): " + joinedContents);
                        }
                    } else {
                        // Handle case with a directory argument
                        String directoryName = parts[1];
                        List<String> contents = LsCommand.list(directoryName);
                        if (!contents.isEmpty()) {
                            String joinedContents = String.join(", ", contents);
                            System.out.println("Contents of " + directoryName + ": " + joinedContents);
                        } else {
                            System.out.println("No contents found or invalid directory.");
                        }
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
        System.out.println("help            - Show available commands.");
        System.out.println("mkdir <name>    - Create a new directory.");
        System.out.println("mkdir <name>    - Create a new directory.");
        System.out.println("rmdir <name>    - Removes an empty existing directory.");
        System.out.println("touch <name>    - adds a new file if it doesnot exist and update  a file's timestamp if it exists.");
        System.out.println("rm <name>       - removes an existing file.");
        System.out.println("mv <source> <destination> - Moves a file or directory from source to destination. "
                + "If source and destination are in the same directory, it renames the file or directory.");
        System.out.println("cat <file1 name><file2 name> ... - If filenames are provided, it will display the content of those files.\n" +
                "If no filenames are provided, it will accept input from the user and echo it back until the user enters Ctrl+D (EOF signal)..");
        System.out.println("echo <output> > <filename>    - Overwriting a file.");
        System.out.println("echo <output> >> <filename>   - Appending to a file.");
        System.out.println("cd <path>             - Change directory.");
        System.out.println("pwd                   - Print current directory.");
        System.out.println("ls [-r]               - List directory contents (recursively if '-r').");
        System.out.println("exit                  - Exit the CLI.");
    }
    // Method to handle the 'cat' command specifically
    private static void handleCatCommand(String[] parts) {
        if (parts.length == 1) {
            // No file names provided, switch to user input mode
            catCommand.execute(new String[0]);
        } else {
            // Extract file names from parts and pass them to the catCommand
            String[] filenames = new String[parts.length - 1];
            System.arraycopy(parts, 1, filenames, 0, parts.length - 1);
            catCommand.execute(filenames);
        }
    }

}
