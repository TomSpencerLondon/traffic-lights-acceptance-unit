package org.example.controller;

import org.example.io.ConsolePrinter;
import org.example.timer.SystemUpdateListener;

import java.io.IOException;

public class DisplayTask implements SystemUpdateListener {

    private final ConsolePrinter printer;

    public DisplayTask(ConsolePrinter printer) {
        this.printer = printer;
    }

    @Override
    public void onSystemUpdate(int secondsPassed, String systemInfo) {
        try {
            printer.clearAndPrint(systemInfo);
        } catch (IOException | InterruptedException e) {
            handleException(e);
        }
    }

    private void handleException(Exception e) {
        e.printStackTrace();
    }
}
