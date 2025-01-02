package org.example;

import org.example.adapter.in.console.Menu;
import org.example.hexagon.application.OutputRefresher;
import org.example.adapter.in.console.TrafficController;
import org.example.hexagon.application.IOHandler;
import org.example.hexagon.application.Printer;
import org.example.hexagon.domain.RoadCoordinator;
import org.example.hexagon.application.TimeTracker;
import org.example.hexagon.application.port.Clock;

import java.io.PrintStream;
import java.util.Scanner;

public class Main {

    public static final String INPUT_VALIDATION = "[1-9][0-9]{0,8}";

    public static void main(String[] args) {
        PrintStream printStream = new PrintStream(System.out);
        Printer printer = new Printer(printStream);
        IOHandler ioHandler = new IOHandler(printer, new Scanner(System.in));

        ioHandler.print("Welcome to the traffic management system!");
        int roadCapacity = queryNumber("Input the number of roads:", ioHandler);
        int interval = queryNumber("Input the interval:", ioHandler);

        OutputRefresher outputRefresher = new OutputRefresher(ioHandler);

        RoadCoordinator roadCoordinator = new RoadCoordinator(roadCapacity, interval);
        Clock clock = new TimeTracker(roadCoordinator, outputRefresher);
        Menu menu = new Menu(ioHandler, roadCoordinator, clock);
        new TrafficController(menu).start();
    }

    private static int queryNumber(String queryMessage, IOHandler ioHandler) {
        ioHandler.print(queryMessage);
        return ioHandler.readValidatedInteger(INPUT_VALIDATION, () -> ioHandler.print("Incorrect Input. Try again:"));
    }
}