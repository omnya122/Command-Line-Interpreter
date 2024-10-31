package org.os;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import org.os.commands.*;

public class CLI {

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
    private static final CdCommand cdCommand               = new CdCommand();

    private static LsCommand  lsCommand                    = new LsCommand();
    private static PwdCommand pwdCommand                   = new PwdCommand();
    private static LsRCommand lsRCommand                   = new LsRCommand();

    public static String currentDirectory                  = System.getProperty("user.dir");

    // Entry point
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
    private static void processCommand(String command)
    {
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

            switch (mainCommand)
            {
                case "help":
                    displayHelp();
                    break;

                case "mkdir":
                    if (parts.length < 2)
                    {
                        System.out.println("Error: mkdir requires a directory name.");
                    }
                    else
                    {
                        mkdirCommand.execute(parts[1]);
                    }
                    break;

                case "rmdir":
                    if (parts.length < 2)
                    {
                        System.out.println("Error: rmdir requires a directory name.");
                    }
                    else
                    {
                        rmdirCommand.execute(parts[1]);
                    }
                    break;

                case "touch":
                    if (parts.length < 2) {
                        System.out.println("Error: touch requires a file name.");
                    }
                    else
                    {
                        touchCommand.touch(parts[1]);
                    }
                    break;

                case "rm":
                    if (parts.length < 2)
                    {
                        System.out.println("Error: rm requires a file name.");
                    }
                    else
                    {
                        rmCommand.delete(parts[1]);
                    }
                    break;

                case "mv":
                    if (parts.length < 3)
                    {
                        System.out.println("Error: mv requires a source and a destination path.");
                    }
                    else
                    {
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
                        CdCommand.changeDirectory(parts[1]);
                    }
                    break;

                case "pwd":
                    PwdCommand.printCurrentDirectory();
                    break;



                case "ls":
                    if (parts.length == 1)
                    {
                        // No arguments, list current directory
                        List<String> currentContents = LsCommand.list(); // Call the normal ls method
                        if (!currentContents.isEmpty())
                        {
                            String joinedContents = String.join(", ", currentContents);
                            System.out.println("Contents of current directory: " + joinedContents);
                        }
                    }
                    else if (parts.length == 2 && parts[1].equals("-r"))
                    {
                        // Handle recursive listing
                        List<String> recursiveContents = LsRCommand.listRecursive(); // Call the recursive ls method
                        if (!recursiveContents.isEmpty())
                        {
                            String joinedContents = String.join(", ", recursiveContents);
                            System.out.println("Contents of current directory (recursive): " + joinedContents);
                        }
                    }
                    else
                    {
                        // Handle case with a directory argument
                        String directoryName = parts[1];
                        List<String> contents = LsCommand.list(directoryName);
                        if (!contents.isEmpty())
                        {
                            String joinedContents = String.join(", ", contents);
                            System.out.println("Contents of " + directoryName + ": " + joinedContents);
                        }
                        else
                        {
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
    private static void displayHelp()
    {
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
        System.out.println("cd <path>                        - Change directory.");
        System.out.println("pwd                              - Print current directory.");
        System.out.println("ls [-r]                          - List directory contents (recursively if '-r').");
        System.out.println("exit                             - Exit the CLI.");
    }

    // Method to handle the 'cat' command specifically
    private static void handleCatCommand(String[] parts)
    {
        if (parts.length == 1)
        {
            catCommand.execute(new String[0]);
        }
        else
        {
            String[] filenames = new String[parts.length - 1];
            System.arraycopy(parts, 1, filenames, 0, parts.length - 1);
            catCommand.execute(filenames);
        }
    }

    public static LsCommand getLsCommand() {
        return lsCommand;
    }

    public static void setLsCommand(LsCommand lsCommand) {
        CLI.lsCommand = lsCommand;
    }

    public static PwdCommand getPwdCommand() {
        return pwdCommand;
    }

    public static void setPwdCommand(PwdCommand pwdCommand) {
        CLI.pwdCommand = pwdCommand;
    }

    public static LsRCommand getLsRCommand() {
        return lsRCommand;
    }

    public static void setLsRCommand(LsRCommand lsRCommand) {
        CLI.lsRCommand = lsRCommand;
    }
}
