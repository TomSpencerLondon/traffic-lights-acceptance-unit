package org.example.adapter.in.console;

import org.example.hexagon.domain.Road;
import org.example.hexagon.domain.RoadState;
import org.example.hexagon.domain.State;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RoadInfoTest {

    @Test
    void formattedRoadInfoTextFromRoadState() {
        RoadState roadState = new RoadState(new Road("first"), State.OPEN, 8);

        RoadInfo roadInfo = RoadInfo.from(roadState);


        assertThat(roadInfo.text())
                .isEqualTo(
                        """
                                first is [32mopen for 8s.[0m
                                """
                );
    }
}
