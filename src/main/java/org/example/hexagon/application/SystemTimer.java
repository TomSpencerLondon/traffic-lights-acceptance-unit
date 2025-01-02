package org.example.hexagon.application;

import org.example.adapter.in.console.SystemInfo;
import org.example.hexagon.application.port.SystemTimerInterface;
import org.example.hexagon.application.port.SystemUpdateListener;
import org.example.hexagon.domain.RoadCoordinator;

import java.util.Timer;
import java.util.TimerTask;

public class SystemTimer implements SystemTimerInterface {

    private final RoadCoordinator roadCoordinator;
    private final Timer timer;
    private final SystemUpdateListener listener;
    private boolean inSystemState = false;
    private int secondsPassed;

    public SystemTimer(RoadCoordinator roadCoordinator, SystemUpdateListener listener) {
        this.roadCoordinator = roadCoordinator;
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
        roadCoordinator.notifySecondPassed();
        if (inSystemState) {
            notifyListener();
        }
    }

    public int getSecondsPassed() {
        return secondsPassed;
    }

    private void notifyListener() {
        SystemInfo systemInfo = new SystemInfo(secondsPassed, roadCoordinator);
        listener.onSystemUpdate(secondsPassed, systemInfo);
    }

    @Override
    public void setInSystemState(boolean inSystemState) {
        this.inSystemState = inSystemState;
    }

    @Override
    public void purge() {
        timer.cancel();
        timer.purge();
    }
}
