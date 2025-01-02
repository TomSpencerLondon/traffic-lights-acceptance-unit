package org.example.hexagon.application.port;

public interface Clock {
    void setInSystemState(boolean state);
    void stop();
    int getSecondsPassed();
}
