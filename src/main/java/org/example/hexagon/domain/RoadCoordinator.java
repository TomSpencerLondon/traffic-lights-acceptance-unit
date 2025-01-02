package org.example.hexagon.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoadCoordinator {

    private final Map<Road, TrafficLight> trafficLights;

    private final int capacity;
    private final int interval;
    private int currentSize;

    public RoadCoordinator(int capacity, int interval) {
        this.capacity = capacity;
        this.interval = interval;
        this.trafficLights = new HashMap<>();
        this.currentSize = 0;
    }

    public List<RoadState> getRoads() {
        return trafficLights.entrySet()
                .stream()
                .map(entry -> new RoadState(entry.getKey(), entry.getValue().state(), entry.getValue().secondsRemaining()))
                .collect(Collectors.toList());
    }

    public void addRoad(String name) {
        Road key = new Road(name);

        if (trafficLights.containsKey(key)) {
            throw new DuplicateRoadException();
        }

        for (Map.Entry<Road, TrafficLight> entry : trafficLights.entrySet()) {
            entry.getValue().resetSecondsRemaining();
        }

        trafficLights.put(key, new TrafficLight(currentSize + 1, interval));


        this.currentSize++;
    }

    public void deleteRoad(String road) {
        Road key = new Road(road);
        if (!trafficLights.containsKey(key)) {
            throw new RoadNotFoundException();
        }

        trafficLights.remove(key);
        this.currentSize--;
    }

    public void notifySecondPassed() {
        for (Map.Entry<Road, TrafficLight> entry : trafficLights.entrySet()) {
            entry.getValue().updateState(currentSize);
        }
    }

}
