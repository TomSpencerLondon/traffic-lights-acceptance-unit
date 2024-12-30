package org.example.io;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class IOHandler {

    private final PrintStream out;
    private final Scanner scanner;

    public IOHandler(PrintStream out, Scanner scanner) {
        this.out = out;
        this.scanner = scanner;
    }

    // Print information to the console
    public void print(String message) {
        out.println(message);
    }

    // Print information and wait for user input
    public void printAndWait(String message) {
        out.println(message);
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    // Clear the console and print a message
    public void clearAndPrint(String message) throws IOException, InterruptedException {
        clear();
        print(message);
    }

    // Read a line of input from the user
    public String readLine() {
        return scanner.nextLine();
    }

    // Validate user input against a regex and return the valid input as an integer
    public int readValidatedInteger(String regex, Runnable invalidAction) {
        String input = readLine();
        while (!input.matches(regex)) {
            invalidAction.run();
            input = readLine();
        }
        return Integer.parseInt(input);
    }

    // Clear the console
    private void clear() throws IOException, InterruptedException {
        var clearCommand = System.getProperty("os.name").contains("Windows")
                ? new ProcessBuilder("cmd", "/c", "cls")
                : new ProcessBuilder("clear");
        clearCommand.inheritIO().start().waitFor();
    }
}

