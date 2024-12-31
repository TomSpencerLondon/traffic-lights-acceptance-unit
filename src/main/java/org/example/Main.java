package org.example;

import org.example.hexagon.application.DisplayTask;
import org.example.adapter.in.console.TrafficController;
import org.example.hexagon.application.IOHandler;
import org.example.hexagon.domain.RoadCoordinator;
import org.example.hexagon.application.SystemTimer;
import org.example.hexagon.application.port.SystemTimerInterface;

import java.io.PrintStream;
import java.util.Scanner;

public class Main {

    public static final String INPUT_VALIDATION = "[1-9][0-9]{0,8}";

    public static void main(String[] args) {
        IOHandler ioHandler = new IOHandler(new PrintStream(System.out), new Scanner(System.in));

        ioHandler.print("Welcome to the traffic management system!");
        int roadCapacity = queryNumber("Input the number of roads:", ioHandler);
        int interval = queryNumber("Input the interval:", ioHandler);

        RoadCoordinator roadCoordinator = new RoadCoordinator(roadCapacity, interval);
        DisplayTask displayTask = new DisplayTask(ioHandler);
        SystemTimerInterface systemTimer = new SystemTimer(roadCoordinator, displayTask);

        new TrafficController(ioHandler, roadCoordinator, systemTimer).start();
    }

    private static int queryNumber(String queryMessage, IOHandler ioHandler) {
        ioHandler.print(queryMessage);
        return ioHandler.readValidatedInteger(INPUT_VALIDATION, () -> ioHandler.print("Incorrect Input. Try again:"));
    }
}