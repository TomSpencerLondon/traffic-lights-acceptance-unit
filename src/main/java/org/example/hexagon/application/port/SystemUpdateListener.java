package org.example.hexagon.application.port;

import org.example.adapter.in.console.SystemInfo;

public interface SystemUpdateListener {
    void onSystemUpdate(int secondsPassed, String message);
}
