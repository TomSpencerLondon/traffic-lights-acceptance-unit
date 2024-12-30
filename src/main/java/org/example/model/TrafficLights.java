package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrafficLights implements TrafficLightsInterface {

    private final List<Road> roads = new ArrayList<>();
    private final int roadCapacity;
    private final int interval;
    private int remainingInInterval;

    public TrafficLights(int roadCapacity, int interval) {
        this.roadCapacity = roadCapacity;
        this.interval = interval;
    }

    public List<Road> getRoads() {
        return roads;
    }

    public int getRoadCapacity() {
        return roadCapacity;
    }

    public int getInterval() {
        return interval;
    }

    public String getRoadsLines() {
        return roads.stream().map(Road::getState).collect(Collectors.joining("\n"));
    }

    @Override
    public boolean addRoad(String road) {
        if (roads.size() == roadCapacity) {
            return false;
        }
        if (roads.isEmpty()) {
            remainingInInterval = interval + 1;
        }
        roads.add(new Road(road, remainingInInterval + roads.size() * interval));
        return true;
    }

    @Override
    public boolean deleteRoad(String road) {
        return roads.removeIf(r -> r.getName().equals(road));
    }


    public void notifySecondPassed() {
        roads.forEach(Road::countDown);
        remainingInInterval = remainingInInterval > 1 ? remainingInInterval - 1 : interval;
    }

    public class Road {

        private static final String ANSI_RED = "\u001B[31m";
        private static final String ANSI_RESET = "\u001B[0m";
        private static final String ANSI_GREEN = "\u001B[32m";

        private final String name;
        private int secondsCounter;

        public Road(String name, int secondsCounter) {
            this.name = name;
            this.secondsCounter = secondsCounter;
        }

        public String getName() {
            return name;
        }

        public int getSecondsCounter() {
            return secondsCounter;
        }

        public void setSecondsCounter(int secondsCounter) {
            this.secondsCounter = secondsCounter;
        }

        private void countDown() {
            secondsCounter = secondsCounter == 1 ? roads.size() * interval : secondsCounter - 1;
        }

        private void subtractInterval() {
            secondsCounter -= interval;
        }

        public String getState() {
            return secondsCounter <= interval // Condition true means road is open
                    ? "%s is %sopen for %ds.%s".formatted(name, ANSI_GREEN, secondsCounter, ANSI_RESET)
                    : "%s is %sclosed for %ds.%s".formatted(name, ANSI_RED, secondsCounter - interval, ANSI_RESET);
        }
    }
}
