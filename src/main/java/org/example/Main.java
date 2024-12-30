package org.example;

import org.example.controller.DisplayTask;
import org.example.controller.MainMenuController;
import org.example.io.IOHandler;
import org.example.model.TrafficLights;
import org.example.model.TrafficLightsInterface;
import org.example.timer.SystemTimer;
import org.example.timer.SystemTimerInterface;

import java.io.PrintStream;
import java.util.Scanner;

public class Main {

    public static final String INPUT_VALIDATION = "[1-9][0-9]{0,8}";

    public static void main(String[] args) {
        IOHandler ioHandler = new IOHandler(new PrintStream(System.out), new Scanner(System.in));

        ioHandler.print("Welcome to the traffic management system!");
        int roadCapacity = queryNumber("Input the number of roads:", ioHandler);
        int interval = queryNumber("Input the interval:", ioHandler);

        TrafficLightsInterface trafficLights = new TrafficLights(roadCapacity, interval);
        DisplayTask displayTask = new DisplayTask(ioHandler);
        SystemTimerInterface systemTimer = new SystemTimer(trafficLights, displayTask);

        new MainMenuController(ioHandler, trafficLights, systemTimer).start();
    }

    private static int queryNumber(String queryMessage, IOHandler ioHandler) {
        ioHandler.print(queryMessage);
        return ioHandler.readValidatedInteger(INPUT_VALIDATION, () -> ioHandler.print("Incorrect Input. Try again:"));
    }
}