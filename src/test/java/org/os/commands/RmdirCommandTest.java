package org.os.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RmdirCommandTest {

    @BeforeEach
    void setUp() {
        // Code to set up before each test
        // For example, you might create a temporary directory for testing
    }

    @AfterEach
    void tearDown() {
        // Code to clean up after each test
        // For example, delete the temporary directory if it exists
    }

    @Test
    void testRmdir() {
        // Your test code here
        // For example, you could test if a directory is successfully removed
        assertEquals(1, 1); // Example assertion; replace this with your actual test
    }

    @Test
    void testRmdirNonExistentDirectory() {
        // Test case for trying to remove a non-existent directory
        // You can assert that the proper exception is thrown or that a specific error message is returned
    }

    @Test
    void testRmdirWithFiles() {
        // Test case for trying to remove a directory that is not empty
        // You can assert that the proper exception is thrown or that a specific error message is returned
    }
}
