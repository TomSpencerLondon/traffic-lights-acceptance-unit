package org.example.adapter.in.console;

import java.io.IOException;
import java.util.Map;

import org.example.hexagon.application.IOHandler;
import org.example.hexagon.domain.Choice;
import org.example.hexagon.domain.TrafficLights;
import org.example.hexagon.application.port.SystemTimerInterface;
public class MainMenuController {

    public static final String EXPECTED_INPUT = "[0-3]";
    private static final String MENU_TEXT = """
            Menu:
            1. Add road
            2. Delete road
            3. Open system
            0. Quit""";

    private final IOHandler ioHandler;
    private final TrafficLights trafficLights;
    private final SystemTimerInterface systemTimer;

    public MainMenuController(IOHandler ioHandler, TrafficLights trafficLights, SystemTimerInterface systemTimer) {
        this.ioHandler = ioHandler;
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
        ioHandler.print("Bye!");
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
        ioHandler.print("Input road name:");
        var road = ioHandler.readLine();
        boolean added = trafficLights.addRoad(road);
        if (added) {
            ioHandler.printAndWait(road + " added");
        } else {
            ioHandler.printAndWait("Failed to add " + road);
        }
    }

    private void deleteRoad() {
        ioHandler.print("Input road name to delete:");
        var road = ioHandler.readLine();
        boolean deleted = trafficLights.deleteRoad(road);
        if (deleted) {
            ioHandler.printAndWait(road + " deleted");
        } else {
            ioHandler.printAndWait("Failed to delete " + road);
        }
    }

    private void openSystem() {
        systemTimer.setInSystemState(true);
        String systemInfo = systemTimer.getSystemInfo();
        ioHandler.print(systemInfo);
        ioHandler.readLine();
        systemTimer.setInSystemState(false);
    }

    private Choice getMenuChoice() throws IOException, InterruptedException {
        ioHandler.clearAndPrint(MENU_TEXT);
        return Choice.values()[ioHandler.readValidatedInteger(EXPECTED_INPUT, this::handleInvalidChoice)];
    }

    private void handleInvalidChoice() {
        ioHandler.printAndWait("Incorrect option");
        try {
            ioHandler.clearAndPrint(MENU_TEXT);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
