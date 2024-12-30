package org.example.io;

import java.io.IOException;
import java.util.Scanner;
import java.io.PrintStream;

public class ConsolePrinter {

    private final PrintStream out;

    public ConsolePrinter(PrintStream out) {
        this.out = out;
    }

    public void printInfo(String message) {
        out.println(message);
    }

    public void printInfoAndWaitForReturn(Scanner scanner, String message) {
        out.println(message);
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    private void clear() throws IOException, InterruptedException {
        var clearCommand = System.getProperty("os.name").contains("Windows")
                ? new ProcessBuilder("cmd", "/c", "cls")
                : new ProcessBuilder("clear");
        clearCommand.inheritIO().start().waitFor();
    }

    public void clearAndPrint(String menuText) throws IOException, InterruptedException {
        clear();
        printInfo(menuText);
    }
}