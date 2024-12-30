package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;


class TrafficManagementAcceptanceTest {

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream)); // Redirect System.out to capture output
    }

    private void simulateInput(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in); // Redirect System.in
    }

    private String getCapturedOutput() {
        return outputStream.toString();
    }

    @Test
    void testAddAndDeleteRoads() {
        // Simulate user inputs
        simulateInput("""
                3
                8
                1
                First
                
                1
                Second
                
                2
                First
                
                3
                
                0
                """);

        Main.main(new String[0]);

        String output = getCapturedOutput();

        assertTrue(output.contains("Welcome to the traffic management system!"), "Should display welcome message");
        assertTrue(output.contains("Input the number of roads:"), "Should prompt for number of roads");
        assertTrue(output.contains("Input the interval:"), "Should prompt for interval");

        assertTrue(output.contains("First added"), "Should confirm 'First' road was added");
        assertTrue(output.contains("Second added"), "Should confirm 'Second' road was added");

        assertTrue(output.contains("First deleted"), "Should confirm 'First' road was deleted");

        // Verify road state with ANSI color codes ignored
        String regex = "Second is \\x1B\\[31mclosed for 9s\\.\\x1B\\[0m";
        assertTrue(output.matches("(?s).*" + regex + ".*"), "Should display 'Second' road as closed for 9s.");

        // Verify program exit
        assertTrue(output.contains("Bye!"), "Should display goodbye message");
    }
}