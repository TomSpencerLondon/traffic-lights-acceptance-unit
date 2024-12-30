package org.example.timer;

import org.example.model.TrafficLightsInterface;

import java.util.Timer;
import java.util.TimerTask;

public class SystemTimer implements SystemTimerInterface {

    private final TrafficLightsInterface trafficLights;
    private final Timer timer;
    private final SystemUpdateListener listener; // Listener for updates
    private boolean inSystemState = false;
    private int secondsPassed;

    public SystemTimer(TrafficLightsInterface trafficLights, SystemUpdateListener listener) {
        this.trafficLights = trafficLights;
        this.listener = listener;
        this.timer = new Timer("QueueThread", true);
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateSystemTime();
            }
        }, 0, 1000);
    }

    private void updateSystemTime() {
        secondsPassed++;
        trafficLights.notifySecondPassed();
        if (inSystemState) {
            notifyListener();
        }
    }

    private void notifyListener() {
        String systemInfo = getSystemInfo();
        listener.onSystemUpdate(secondsPassed, systemInfo);
    }

    @Override
    public void setInSystemState(boolean inSystemState) {
        this.inSystemState = inSystemState;
    }

    @Override
    public String getSystemInfo() {
        return String.format("! %ds. have passed since system startup !\n! Number of roads: %d !\n! Interval: %d !\n\n%s\n\n! Press \"Enter\" to open menu !",
                secondsPassed, trafficLights.getRoadCapacity(), trafficLights.getInterval(), trafficLights.getRoadsLines());
    }

    @Override
    public void purge() {
        timer.cancel();
        timer.purge();
    }

    @Override
    public void addSecondsPassed() {
        secondsPassed++;
    }

    @Override
    public void notifySecondPassed() {
        trafficLights.notifySecondPassed();
    }

    @Override
    public boolean isSystemActive() {
        return inSystemState;
    }
}
