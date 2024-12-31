package org.example.hexagon.application.port;

public interface SystemUpdateListener {
    void onSystemUpdate(int secondsPassed, String systemInfo);
}
