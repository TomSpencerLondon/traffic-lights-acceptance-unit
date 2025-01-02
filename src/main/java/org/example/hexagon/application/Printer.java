package org.example.hexagon.application;

import java.io.IOException;
import java.io.PrintStream;

public class Printer {
    private static final String MENU_TEXT = """
            Menu:
            1. Add road
            2. Delete road
            3. Open system
            0. Quit""";

    private final PrintStream out;

    public Printer(PrintStream out) {
        this.out = out;
    }


    public void print(String message) {
        out.println(message);
    }

    public void printMenu() {
        out.println(MENU_TEXT);
    }
}
