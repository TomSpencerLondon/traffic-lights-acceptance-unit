package org.example.timer;

public interface SystemTimerInterface {
    void setInSystemState(boolean state);
    String getSystemInfo();
    void purge();
}
