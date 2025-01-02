package org.example.adapter.in.console;

import org.example.hexagon.domain.RoadState;
import org.example.hexagon.domain.State;

public class RoadInfo {

    private final String name;
    private final String color;
    private final String state;
    private final int timeRemaining;
    private final String normal;

    public RoadInfo(String name, String color, String state, int timeRemaining, String normal) {
        this.name = name;
        this.color = color;
        this.state = state;
        this.timeRemaining = timeRemaining;
        this.normal = normal;
    }

    public static RoadInfo from(RoadState roadState) {
        String green = "\u001B[32m";
        String red = "\u001B[31m";
        String normal = "\u001B[0m";
        String color = roadState.getState() == State.OPEN ? green : red;
        String state = roadState.getState() == State.OPEN ? "open" : "closed";
        return new RoadInfo(roadState.getRoad().name(), color, state, roadState.getTimeRemaining(), normal);
    }

    public String text() {
        return "%s is %s%s for %ds.%s\n".formatted(name, color, state, timeRemaining, normal);
    }
}
