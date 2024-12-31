package org.example.hexagon.domain;

public class Road {

    private final String name;
    private final Countdown countdown;

    public Road(String name, Countdown countdown) {
        this.name = name;
        this.countdown = countdown;
    }

    public String getName() {
        return name;
    }

    public void countDown(int totalTimeForAllRoads) {
        countdown.decrement(totalTimeForAllRoads);
    }

    public String getState(int interval) {
        return countdown.getState(interval, name);
    }

}
