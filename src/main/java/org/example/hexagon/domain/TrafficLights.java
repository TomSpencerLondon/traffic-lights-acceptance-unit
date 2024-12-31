package org.example.hexagon.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrafficLights {

    private final List<Road> roads = new ArrayList<>();
    private final int roadCapacity;
    private final int interval;
    private int remainingInInterval;

    public TrafficLights(int roadCapacity, int interval) {
        this.roadCapacity = roadCapacity;
        this.interval = interval;
    }

    public int getRoadCapacity() {
        return roadCapacity;
    }

    public int getInterval() {
        return interval;
    }

    public String getRoadsLines() {
        return roads.stream().map(road -> road.getState(interval)).collect(Collectors.joining("\n"));
    }

    public boolean addRoad(String name) {
        if (roads.size() == roadCapacity) {
            return false;
        }
        if (roads.isEmpty()) {
            remainingInInterval = interval + 1;
        }
        roads.add(new Road(name, new Countdown(remainingInInterval + roads.size() * interval)));
        return true;
    }

    public boolean deleteRoad(String road) {
        return roads.removeIf(r -> r.getName().equals(road));
    }


    public void notifySecondPassed() {
        roads.forEach(road -> road.countDown(roads.size() * interval));
        remainingInInterval = remainingInInterval > 1 ? remainingInInterval - 1 : interval;
    }

}
