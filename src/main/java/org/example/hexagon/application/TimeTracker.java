package org.example.hexagon.application;

import org.example.adapter.in.console.RoadInfo;
import org.example.adapter.in.console.SystemInfo;
import org.example.hexagon.application.port.Clock;
import org.example.hexagon.application.port.SystemUpdateListener;
import org.example.hexagon.domain.RoadCoordinator;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class TimeTracker implements Clock {

    private final RoadCoordinator roadCoordinator;
    private final Timer timer;
    private final SystemUpdateListener listener;
    private boolean inSystemState = false;
    private int secondsPassed;

    public TimeTracker(RoadCoordinator roadCoordinator, SystemUpdateListener listener) {
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
        List<RoadInfo> roadInfos = roadCoordinator.getRoads().stream()
                .map(RoadInfo::from)
                .collect(Collectors.toList());
        SystemInfo systemInfo = SystemInfo.from(secondsPassed, roadInfos);
        listener.onSystemUpdate(secondsPassed, systemInfo.formatSystemInfo());
    }

    @Override
    public void setInSystemState(boolean inSystemState) {
        this.inSystemState = inSystemState;
    }

    @Override
    public void stop() {
        timer.cancel();
        timer.purge();
    }
}
