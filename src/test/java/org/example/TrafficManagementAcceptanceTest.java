package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

        assertThat(output)
                .contains("Welcome to the traffic management system!");
        assertThat(output)
                .contains("Input the number of roads:");
        assertThat(output)
                .contains("Input the interval:");

        assertThat(output)
                .contains("First added");
        assertThat(output)
                .contains("Second added");

        assertThat(output)
                .contains("First deleted");
        assertThat(output)
                .contains("Second is \u001B[31mclosed for 8s.\u001B[0m");

        // Verify program exit
        assertTrue(output.contains("Bye!"), "Should display goodbye message");
    }
}