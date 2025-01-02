package org.example.adapter.in.console;

import org.example.hexagon.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SystemInfoTest {

    @Test
    void showsUpdateForSecondsPassedAndThreeRoadInfos() {
        RoadInfo first = RoadInfo.from(new RoadState(new Road("first"), State.OPEN, 8));
        RoadInfo second = RoadInfo.from(new RoadState(new Road("second"), State.CLOSED, 8));
        RoadInfo third = RoadInfo.from(new RoadState(new Road("third"), State.CLOSED, 16));

        SystemInfo systemInfo = SystemInfo.from(8, List.of(first, second, third));

        assertThat(systemInfo.formatSystemInfo())
                .isEqualTo(
                        """
                                ! 8s. have passed since system startup !
                                                                
                                first is [32mopen for 8s.[0m
                                second is [31mclosed for 8s.[0m
                                third is [31mclosed for 16s.[0m
                                                                
                                ! Press "Enter" to open menu !
                                """
                );
    }
}
