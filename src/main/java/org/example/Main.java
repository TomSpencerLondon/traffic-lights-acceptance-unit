package org.example;

import org.example.controller.DisplayTask;
import org.example.controller.MainMenuController;
import org.example.io.ConsolePrinter;
import org.example.model.TrafficLights;
import org.example.model.TrafficLightsInterface;
import org.example.timer.SystemTimer;
import org.example.timer.SystemTimerInterface;

import java.io.PrintStream;
import java.util.Scanner;

public class Main {

    public static final String INPUT_VALIDATION = "[1-9][0-9]{0,8}";

    public static void main(String[] args) {
        ConsolePrinter printer = new ConsolePrinter(new PrintStream(System.out));
        Scanner scanner = new Scanner(System.in);
        printer.printInfo("Welcome to the traffic management system!");
        int roadCapacity = queryNumber("Input the number of roads:", printer, scanner);
        int interval = queryNumber("Input the interval:", printer, scanner);
        TrafficLightsInterface trafficLights = new TrafficLights(roadCapacity, interval);

        DisplayTask displayTask = new DisplayTask(printer);

        SystemTimerInterface systemTimer = new SystemTimer(trafficLights, displayTask);

        new MainMenuController(printer, scanner, trafficLights, systemTimer).start();
    }

    private static int queryNumber(String queryMessage, ConsolePrinter printer, Scanner scanner) {
        printer.printInfo(queryMessage);
        return scanIntegerValidated(() -> printer.printInfo("Incorrect Input. Try again:"), scanner);
    }

    private static int scanIntegerValidated(Runnable invalidAction, Scanner scanner) {
        String input = scanner.nextLine();
        while (!input.matches(INPUT_VALIDATION)) {
            invalidAction.run();
            input = scanner.nextLine();
        }
        return Integer.parseInt(input);
    }
}