package org.example.hexagon.application;

import org.example.hexagon.application.port.SystemTimerInterface;
import org.example.hexagon.application.port.SystemUpdateListener;
import org.example.hexagon.domain.TrafficCoordinator;

import java.util.Timer;
import java.util.TimerTask;

public class SystemTimer implements SystemTimerInterface {

    private final TrafficCoordinator trafficCoordinator;
    private final Timer timer;
    private final SystemUpdateListener listener;
    private boolean inSystemState = false;
    private int secondsPassed;

    public SystemTimer(TrafficCoordinator trafficCoordinator, SystemUpdateListener listener) {
        this.trafficCoordinator = trafficCoordinator;
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
        trafficCoordinator.notifySecondPassed();
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
                secondsPassed, trafficCoordinator.getRoadCapacity(), trafficCoordinator.getInterval(), trafficCoordinator.getRoadsLines());
    }

    @Override
    public void purge() {
        timer.cancel();
        timer.purge();
    }
}
