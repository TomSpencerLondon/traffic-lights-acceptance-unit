package org.example.model;

public interface TrafficLightsInterface {
    boolean addRoad(String road);
    boolean deleteRoad(String road);
    void initialize(int roadCapacity, int interval);

    int getRoadCapacity();

    int getInterval();

    String getRoadsLines();

    void notifySecondPassed();
}
