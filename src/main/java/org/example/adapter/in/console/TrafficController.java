package org.example.adapter.in.console;

import org.example.hexagon.application.IOHandler;
import org.example.hexagon.application.port.Clock;
import org.example.hexagon.domain.Choice;
import org.example.hexagon.domain.DuplicateRoadException;
import org.example.hexagon.domain.RoadCoordinator;
import org.example.hexagon.domain.RoadNotFoundException;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class TrafficController {

    public static final String EXPECTED_INPUT = "[0-3]";
    private static final String MENU_TEXT = """
            Menu:
            1. Add road
            2. Delete road
            3. Open system
            0. Quit""";

    private final IOHandler ioHandler;
    private final RoadCoordinator roadCoordinator;
    private final Clock clock;
    private final Map<Choice, Runnable> choices;

    public TrafficController(IOHandler ioHandler, RoadCoordinator roadCoordinator, Clock clock) {
        this.ioHandler = ioHandler;
        this.roadCoordinator = roadCoordinator;
        this.clock = clock;
        this.choices = Map.of(
                Choice.ADD_ROAD, this::addRoad,
                Choice.DELETE_ROAD, this::deleteRoad,
                Choice.OPEN_SYSTEM, this::openSystem
        );
    }

    public void start() {
        try {
            menuLoop();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        clock.stop();
        ioHandler.print("Bye!");
    }

    private void menuLoop() throws IOException, InterruptedException {
        Choice choice;
        do {
            choice = menuChoice();
            Runnable action = menuAction(choice);
            if (action != null) {
                action.run();
            }
        } while (choice != Choice.QUIT);
    }

    private Runnable menuAction(Choice choice) {
        return choices.get(choice);
    }

    private void addRoad() {
        ioHandler.print("Input road name:");
        var road = ioHandler.readLine();

        try {
            roadCoordinator.addRoad(road);
        } catch (DuplicateRoadException e) {
            ioHandler.printAndWait("Failed to add " + road);
        }

        ioHandler.printAndWait(road + " added");
    }

    private void deleteRoad() {
        ioHandler.print("Input road name to delete:");
        var road = ioHandler.readLine();

        try {
            roadCoordinator.deleteRoad(road);
        } catch (RoadNotFoundException e) {
            ioHandler.printAndWait("Failed to delete " + road);
        }

        ioHandler.printAndWait(road + " deleted");
    }

    private void openSystem() {
        clock.setInSystemState(true);

        SystemInfo systemInfo = new SystemInfo(clock.getSecondsPassed(), roadCoordinator.getRoads().stream()
                .map(RoadInfo::from)
                .collect(Collectors.toList()));
        ioHandler.print(systemInfo.formatSystemInfo());

        ioHandler.readLine();
        clock.setInSystemState(false);
    }

    private Choice menuChoice() throws IOException, InterruptedException {
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
