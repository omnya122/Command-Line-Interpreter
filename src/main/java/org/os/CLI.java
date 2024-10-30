package org.os;
import java.io.IOException;
import java.util.Scanner;
import org.os.commands.*;

public class CLI
{
    private static final Scanner   scanner                 = new Scanner(System.in);
    private static final RmCommand rmCommand               = new RmCommand();
    private static final MvCommand mvCommand               = new MvCommand();
    private static final CatCommand catCommand             = new CatCommand();
    private static final PipeCommand pipeCommand           = new PipeCommand();
    private static final EchoCommand echoCommand           = new EchoCommand();
    private static final MkdirCommand mkdirCommand         = new MkdirCommand();
    private static final RmdirCommand rmdirCommand         = new RmdirCommand();
    private static final TouchCommand touchCommand         = new TouchCommand();
    private static final AppendCommand appendCommand       = new AppendCommand();
    private static final OverwriteCommand overwriteCommand = new OverwriteCommand();

    public static String currentDirectory = System.getProperty("user.dir");

    public static void main(String[] args)
    {
        System.out.println("Welcome to the CLI! If you want to see available commands, type 'help'.");

        // Infinite loop to process commands until 'exit' is entered
        while (true) {
            System.out.print("> ");  // CLI prompt
            String command = scanner.nextLine().trim();

            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Exiting CLI. Goodbye!");
                break;
            }
            processCommand(command);
        }
    }

    // Method to process the command
    private static void processCommand(String command) {

        if (command.contains("|"))
        {
            String result = pipeCommand.execute(command);
            if (!result.isEmpty())
            {
                System.out.println(result);
            }
            return;
        }
        if (command.startsWith("echo "))
        {
            String echoText = command.substring(5).trim();
            if (echoText.contains(" > "))
            {
                String[] outputAndFile = echoText.split(" > ", 2);
                if (outputAndFile.length == 2)
                {
                    String output = outputAndFile[0].trim();
                    String filename = outputAndFile[1].trim();
                    String result = overwriteCommand.execute(new String[]{output, filename}, currentDirectory);
                    System.out.println(result);
                }
                else
                {
                    System.out.println("Error: Incorrect format. Use 'echo <output> > <filename>'.");
                }
            }
            else if (echoText.contains(" >> "))
            {
                String[] outputAndFile = echoText.split(" >> ", 2);
                if (outputAndFile.length == 2)
                {
                    String output = outputAndFile[0].trim();
                    String filename = outputAndFile[1].trim();
                    String result = appendCommand.execute(new String[]{output, filename}, currentDirectory);
                    System.out.println(result);
                }
                else
                {
                    System.out.println("Error: Incorrect format. Use 'echo <output> >> <filename>'.");
                }
            }
            else
            {
                    String result = echoCommand.execute(echoText);
                    System.out.println(result);
            }
        }
        else
        {
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

                default:
                    System.out.println("Error: Command not recognized.");
            }
        }
    }

    // Method to display available commands
    private static void displayHelp() {
        System.out.println("Available commands:");
        System.out.println("help                             - Show available commands.");
        System.out.println("mkdir <name>                     - Create a new directory.");
        System.out.println("rmdir <name>                     - Remove an empty existing directory.");
        System.out.println("touch <name>                     - Add a new file if it does not exist, or update a file's timestamp if it exists.");
        System.out.println("rm <name>                        - Remove an existing file.");
        System.out.println("mv <source> <destination>        - Move a file or directory from source to destination.");
        System.out.println("                                   If source and destination are in the same directory, rename the file or directory.");
        System.out.println("cat <file1> <file2> ...          - Display the content of specified files.");
        System.out.println("                                   If no filenames are provided, accept input from the user and display it.");
        System.out.println("                                   End input with Ctrl+D (EOF signal).");
        System.out.println("echo <output> > <filename>       - Overwrite a file with the specified output.");
        System.out.println("echo <output> >> <filename>      - Append output to a file.");
        System.out.println("command_1 | command_2 | ...      - Send the output of one command as input to the next in a chain.");
        System.out.println("exit                             - Exit the CLI.");

    }
    // Method to handle the 'cat' command specifically
    private static void handleCatCommand(String[] parts)
    {
        if (parts.length == 1)
        {
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
