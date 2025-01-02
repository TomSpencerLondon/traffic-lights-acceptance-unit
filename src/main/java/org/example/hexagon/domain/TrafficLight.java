package org.example.hexagon.domain;

public class TrafficLight {

    private int position;
    private final int interval;
    private State state;

    private int secondsRemaining;

    public TrafficLight(int position, int interval) {
        this.position = position;
        this.interval = interval;
        this.state = this.position == 1 ? State.OPEN : State.CLOSED;
        this.secondsRemaining = calculateDelay();
    }

    public int secondsRemaining() {
        return secondsRemaining;
    }

    public State state() {
        return state;
    }

    public void updateState(int lastPosition) {
        this.secondsRemaining--;

        if (isTimeToCheckState()) {
            this.position = this.position == 1 ? lastPosition : this.position - 1;
            this.state = this.position == 1 ? State.OPEN : State.CLOSED;
            this.secondsRemaining = calculateDelay();
        }
    }

    public void resetSecondsRemaining() {
        this.secondsRemaining = calculateDelay();
    }

    private boolean isTimeToCheckState() {
        return secondsRemaining % interval == 0;
    }

    private int calculateDelay() {
        if (position <= 2) {
            return interval;
        }

        return (position - 1) * interval;
    }
}
