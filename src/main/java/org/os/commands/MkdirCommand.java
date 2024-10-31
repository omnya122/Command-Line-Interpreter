package org.os.commands;

import java.io.File;

public class MkdirCommand
{
    public void execute(String dirName)
    {
        File directory = new File(dirName);
        if (directory.exists())
        {
            System.out.println("Error: Directory already exists.");
        }
        else if (directory.mkdir())
        {
            System.out.println("Directory '" + dirName + "' created successfully.");
        }
        else
        {
            System.out.println("Error: Failed to create the directory.");
        }
    }
}
