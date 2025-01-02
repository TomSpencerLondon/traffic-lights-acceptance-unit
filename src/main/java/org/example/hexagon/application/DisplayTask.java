package org.example.hexagon.application;

import org.example.adapter.in.console.SystemInfo;
import org.example.hexagon.application.port.SystemUpdateListener;

import java.io.IOException;

public class DisplayTask implements SystemUpdateListener {

    private final IOHandler ioHandler;

    public DisplayTask(IOHandler ioHandler) {
        this.ioHandler = ioHandler;
    }

    @Override
    public void onSystemUpdate(int secondsPassed, SystemInfo systemInfo) {
        try {
            ioHandler.clearAndPrint(systemInfo.formatSystemInfo());
        } catch (IOException | InterruptedException e) {
            handleException(e);
        }
    }

    private void handleException(Exception e) {
        e.printStackTrace();
    }
}
