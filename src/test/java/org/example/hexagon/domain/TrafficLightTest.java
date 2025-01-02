package org.example.hexagon.domain;

import org.junit.jupiter.api.Test;

public class TrafficLightTest {

    @Test
    void thirdInQueueShouldTransitionToCorrectPositionAndTimingAfterThreshold() {
        TrafficLight trafficLight = new TrafficLight(3, 8);

        trafficLight.updateState(4);
    }
}
