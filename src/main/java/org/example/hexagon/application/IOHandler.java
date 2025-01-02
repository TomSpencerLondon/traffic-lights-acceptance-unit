package org.example.hexagon.application;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class IOHandler {

    private final Printer printer;
    private final Scanner scanner;

    public IOHandler(Printer printer, Scanner scanner) {
        this.printer = printer;
        this.scanner = scanner;
    }

    public void print(String message) {
        printer.print(message);
    }

    public void printAndWait(String message) {
        printer.print(message);
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    public void clearAndPrint(String message) throws IOException, InterruptedException {
        clear();
        print(message);
    }

    public void clearAndPrintMenu() throws IOException, InterruptedException {
        clear();
        printer.printMenu();
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public int readValidatedInteger(String regex, Runnable invalidAction) {
        String input = readLine();
        while (!input.matches(regex)) {
            invalidAction.run();
            input = readLine();
        }
        return Integer.parseInt(input);
    }

    private void clear() throws IOException, InterruptedException {
        var clearCommand = System.getProperty("os.name").contains("Windows")
                ? new ProcessBuilder("cmd", "/c", "cls")
                : new ProcessBuilder("clear");
        clearCommand.inheritIO().start().waitFor();
    }
}

