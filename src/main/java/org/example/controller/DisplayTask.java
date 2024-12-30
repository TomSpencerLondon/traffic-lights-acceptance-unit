package org.example.controller;

import org.example.io.IOHandler;
import org.example.timer.SystemUpdateListener;

import java.io.IOException;

public class DisplayTask implements SystemUpdateListener {

    private final IOHandler ioHandler;

    public DisplayTask(IOHandler ioHandler) {
        this.ioHandler = ioHandler;
    }

    @Override
    public void onSystemUpdate(int secondsPassed, String systemInfo) {
        try {
            ioHandler.clearAndPrint(systemInfo);
        } catch (IOException | InterruptedException e) {
            handleException(e);
        }
    }

    private void handleException(Exception e) {
        e.printStackTrace();
    }
}
