package org.example.hexagon.domain;

public class Countdown {

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";

    private int secondsCounter;

    public Countdown(int initialSeconds) {
        this.secondsCounter = initialSeconds;
    }

    public void decrement(int totalTimeForAllRoads) {
        secondsCounter = secondsCounter == 1 ? totalTimeForAllRoads : secondsCounter - 1;
    }

    public String getState(int interval, String roadName) {
        return secondsCounter <= interval
                ? "%s is %sopen for %ds.%s".formatted(roadName, ANSI_GREEN, secondsCounter, ANSI_RESET)
                : "%s is %sclosed for %ds.%s".formatted(roadName, ANSI_RED, secondsCounter - interval, ANSI_RESET);
    }
}
