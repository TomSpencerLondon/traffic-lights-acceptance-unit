package org.example.hexagon.application.port;

public interface SystemTimerInterface {
    void setInSystemState(boolean state);
    String getSystemInfo();
    void purge();
}
