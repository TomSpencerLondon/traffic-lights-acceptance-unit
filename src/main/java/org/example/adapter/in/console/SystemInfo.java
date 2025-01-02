package org.example.adapter.in.console;

import org.example.hexagon.domain.RoadState;
import org.example.hexagon.domain.State;
import org.example.hexagon.domain.Road;
import org.example.hexagon.domain.RoadCoordinator;

import java.util.List;
import java.util.stream.Collectors;

public class SystemInfo {

    private final int secondsPassed;
    private final List<String> formattedRoadStates;

    public SystemInfo(int secondsPassed, RoadCoordinator roadCoordinator) {
        this.secondsPassed = secondsPassed;
        this.formattedRoadStates = roadCoordinator.getRoads().stream()
                .map(this::formatRoadState)
                .collect(Collectors.toList());
    }

    private String formatRoadState(RoadState roadState) {
        String color = roadState.getState() == State.OPEN ? "\u001B[32m" : "\u001B[31m";
        String reset = "\u001B[0m";
        String state = roadState.getState() == State.OPEN ? "open" : "closed";

        return "%s is %s%s for %ds.%s".formatted(roadState.getRoad().name(), color, state, roadState.getTimeRemaining(), reset);
    }

    public String formatSystemInfo() {
        return String.format(
                "! %ds. have passed since system startup !\n\n%s\n\n! Press \"Enter\" to open menu !",
                secondsPassed,
                String.join("\n", formattedRoadStates)
        );
    }
}
