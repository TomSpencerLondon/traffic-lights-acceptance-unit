package org.example.timer;

public interface SystemTimerInterface {
    void addSecondsPassed();
    void notifySecondPassed();
    boolean isSystemActive();
    void setInSystemState(boolean state);
    String getSystemInfo();
    void purge();
}
