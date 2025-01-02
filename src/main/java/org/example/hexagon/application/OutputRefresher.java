package org.example.hexagon.application;

import org.example.hexagon.application.port.SystemUpdateListener;

import java.io.IOException;

public class OutputRefresher implements SystemUpdateListener {

    private final IOHandler ioHandler;

    public OutputRefresher(IOHandler ioHandler) {
        this.ioHandler = ioHandler;
    }

    @Override
    public void onSystemUpdate(int secondsPassed, String message) {
        try {
            ioHandler.clearAndPrint(message);
        } catch (IOException | InterruptedException e) {
            handleException(e);
        }
    }

    private void handleException(Exception e) {
        e.printStackTrace();
    }
}
