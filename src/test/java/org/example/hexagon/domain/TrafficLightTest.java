package org.example.hexagon.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TrafficLightTest {

    @Test
    void initialisesTrafficLightWithDefaultState() {
        TrafficLight trafficLight = new TrafficLight(1, 8);

        State state = trafficLight.state();
        int secondsRemaining = trafficLight.secondsRemaining();

        assertThat(state)
                .isEqualTo(State.OPEN);
        assertThat(secondsRemaining)
                .isEqualTo(8);
    }



}
