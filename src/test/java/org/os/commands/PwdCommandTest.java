package org.os.commands;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PwdCommandTest {
    @Test
    public void testPrintCurrentDirectory() {
        // Capture the output of printCurrentDirectory
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out; // Store the original System.out
        System.setOut(new PrintStream(outputStream)); // Redirect System.out

        // Call the method to test
        PwdCommand.printCurrentDirectory();

        // Restore the original System.out
        System.setOut(originalOut);

        // Prepare expected output
        String expectedOutput = "Current directory: " + CdCommand.getCurrentDirectory() + System.lineSeparator();

        // Assert that the output matches the expected output
        assertEquals(expectedOutput, outputStream.toString());
    }
}
