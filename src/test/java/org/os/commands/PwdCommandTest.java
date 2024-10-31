package org.os.commands;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PwdCommandTest {
    @Test
    public void testPrintCurrentDirectory() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream)); // Redirect System.out

        PwdCommand.printCurrentDirectory();

        System.setOut(originalOut);
        String expectedOutput = "Current directory: " + CdCommand.getCurrentDirectory() + System.lineSeparator();

        assertEquals(expectedOutput, outputStream.toString());
    }
}
