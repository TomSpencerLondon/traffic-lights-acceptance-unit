package org.example.hexagon.application.port;

public interface SystemTimerInterface {
    void setInSystemState(boolean state);
    void purge();
    int getSecondsPassed();
}
