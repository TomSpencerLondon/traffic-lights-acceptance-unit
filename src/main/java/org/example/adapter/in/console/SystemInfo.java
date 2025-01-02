package org.example.adapter.in.console;

import java.util.List;
import java.util.stream.Collectors;

public class SystemInfo {

    private final int secondsPassed;
    private final List<RoadInfo> roadInfos;

    SystemInfo(int secondsPassed, List<RoadInfo> roadInfos) {
        this.secondsPassed = secondsPassed;
        this.roadInfos = roadInfos;
    }

    public static SystemInfo from(int secondsPassed, List<RoadInfo> roadInfos) {
        return new SystemInfo(secondsPassed, roadInfos);
    }

    public String formatSystemInfo() {
        return String.format(
                "! %ds. have passed since system startup !\n\n%s\n! Press \"Enter\" to open menu !\n",
                secondsPassed,
                roadInfos.stream().map(RoadInfo::text).collect(Collectors.joining())
        );
    }
}
