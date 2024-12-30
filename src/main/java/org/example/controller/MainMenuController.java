package org.example.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import org.example.io.ConsolePrinter;
import org.example.model.TrafficLightsInterface;
import org.example.timer.SystemTimerInterface;
public class MainMenuController {

    public static final String EXPECTED_INPUT = "[0-3]";
    private static final String MENU_TEXT = """
            Menu:
            1. Add road
            2. Delete road
            3. Open system
            0. Quit""";

    private final ConsolePrinter printer;
    private final Scanner scanner;
    private final TrafficLightsInterface trafficLights;
    private final SystemTimerInterface systemTimer;

    public MainMenuController(ConsolePrinter printer, Scanner scanner, TrafficLightsInterface trafficLights, SystemTimerInterface systemTimer) {
        this.printer = printer;
        this.scanner = scanner;
        this.trafficLights = trafficLights;
        this.systemTimer = systemTimer;
    }

    public void start() {
        try {
            mainMenuLoop();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        systemTimer.purge();
        printer.printInfo("Bye!");
    }

    private void mainMenuLoop() throws IOException, InterruptedException {
        Choice choice;
        do {
            choice = getMenuChoice();
            Runnable action = getMenuAction(choice);
            if (action != null) {
                action.run();
            }
        } while (choice != Choice.QUIT);
    }

    private Runnable getMenuAction(Choice choice) {
        return Map.<Choice, Runnable>of(
                Choice.ADD_ROAD, this::addRoad,
                Choice.DELETE_ROAD, this::deleteRoad,
                Choice.OPEN_SYSTEM, this::openSystem
        ).get(choice);
    }

    private void addRoad() {
        printer.printInfo("Input road name:");
        var road = scanner.nextLine();
        boolean added = trafficLights.addRoad(road);
        if (added) {
            printer.printInfoAndWaitForReturn(scanner, road + " added");
        } else {
            printer.printInfoAndWaitForReturn(scanner, "Failed to add " + road);
        }
    }

    private void deleteRoad() {
        printer.printInfo("Input road name to delete:");
        var road = scanner.nextLine();
        boolean deleted = trafficLights.deleteRoad(road);
        if (deleted) {
            printer.printInfoAndWaitForReturn(scanner, road + " deleted");
        } else {
            printer.printInfoAndWaitForReturn(scanner, "Failed to delete " + road);
        }
    }

    private void openSystem() {
        systemTimer.setInSystemState(true);
        String systemInfo = systemTimer.getSystemInfo();
        printer.printInfo(systemInfo);
        scanner.nextLine(); // Wait on Return press
        systemTimer.setInSystemState(false);
    }

    private Choice getMenuChoice() throws IOException, InterruptedException {
        printer.clearAndPrint(MENU_TEXT);
        return Choice.values()[scanIntegerValidated(this::handleInvalidChoice)];
    }

    private void handleInvalidChoice() {
        printer.printInfoAndWaitForReturn(scanner, "Incorrect option");
        try {
            printer.clearAndPrint(MENU_TEXT);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private int scanIntegerValidated(Runnable invalidAction) {
        String input = scanner.nextLine();
        while (!input.matches(EXPECTED_INPUT)) {
            invalidAction.run();
            input = scanner.nextLine();
        }
        return Integer.parseInt(input);
    }
}
