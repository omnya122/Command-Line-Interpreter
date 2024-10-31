package org.os.commands;

public class PwdCommand {
    public static void printCurrentDirectory()
    {
        System.out.println("Current directory: " + CdCommand.getCurrentDirectory());
    }
}
