package org.os.commands;

import java.io.File;

public class PipeCommand {

    private String currentDirectory = System.getProperty("user.dir");
    private static final MkdirCommand mkdirCommand = new MkdirCommand();
    private static final RmdirCommand rmdirCommand = new RmdirCommand();
    private static final TouchCommand touchCommand = new TouchCommand();
    private static final CatCommand catCommand = new CatCommand();
    private static final AppendCommand appendCommand = new AppendCommand();
    private static final EchoCommand echoCommand = new EchoCommand();
    private static final OverwriteCommand overwriteCommand = new OverwriteCommand();


    public String execute(String commandLine)
    {
        String[] commands = commandLine.split("\\|");
        String input = "";

        for (String command : commands) {
            String trimmedCommand = command.trim();
            if(!input.isEmpty())
                trimmedCommand += " ";

            // Pass the output of the current command as input to the next
            input = executeSingleCommand(trimmedCommand + input);
        }

        return input;
    }

    private String executeSingleCommand(String command)
    {
        if (command.startsWith("mkdir "))
        {
            String dirName = command.substring(6).trim();
            mkdirCommand.execute(dirName);
            return "";
        }
        else if (command.startsWith("rmdir "))
        {
            String dirName = command.substring(6).trim();
            rmdirCommand.execute(dirName);
            return "";
        }
        else if (command.startsWith("touch "))
        {
            String dirName = command.substring(6).trim();
            touchCommand.touch(currentDirectory + "/" + dirName);
            return "";
        }
        else if (command.startsWith("cat "))
        {
            String[] filenames = command.substring(4).trim().split(" ");
            return catCommand.execute(filenames);
        }
        else if (command.startsWith("cd "))
        {
            String dirName = command.substring(3).trim();
            File dir = new File(currentDirectory, dirName);
            if (dir.exists() && dir.isDirectory())
            {
                currentDirectory = dir.getAbsolutePath();
                return "";
            }
            else
            {
                return "Error: Directory not found.";
            }
        }
        else if (command.contains(">>"))
        {
            String[] parts = command.split(">>");
            if (parts.length == 2) {
                String output = parts[0].trim().replace("echo ", "");
                String filename = parts[1].trim();
                return appendCommand.execute(new String[]{output, filename}, currentDirectory);
            } else {
                return "Error: Invalid append syntax.";
            }
        }
        else if (command.contains(">"))
        {
            String[] parts = command.split(">");
            if (parts.length == 2)
            {
                String output = parts[0].trim().replace("echo ", "");
                String filename = parts[1].trim();
                return overwriteCommand.execute(new String[]{output, filename}, currentDirectory);
            }
            else
            {
                return "Error: Invalid append syntax.";
            }
        }
        else if (command.startsWith("echo "))
        {
            String s = command.substring(5);
            return echoCommand.execute(s);
        }
        else
        {
            return "Error: Command not recognized.";
        }
    }
}