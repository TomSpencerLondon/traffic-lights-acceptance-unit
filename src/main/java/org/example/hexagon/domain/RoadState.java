package org.example.hexagon.domain;

public class RoadState {

    private final Road road;
    private final State state;

    private int timeRemaining;

    public RoadState(Road road, State state, int timeRemaining) {
        this.road = road;
        this.state = state;
        this.timeRemaining = timeRemaining;
    }

    public Road getRoad() {
        return road;
    }

    public State getState() {
        return state;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }
}

