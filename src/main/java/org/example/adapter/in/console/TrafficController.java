package org.example.adapter.in.console;

import org.example.hexagon.application.IOHandler;
import org.example.hexagon.application.port.Clock;
import org.example.hexagon.domain.Choice;
import org.example.hexagon.domain.RoadCoordinator;

import java.io.IOException;

public class TrafficController {

    private final Menu menu;

    public TrafficController(Menu menu) {
        this.menu = menu;
    }

    public void start() {
        try {
            menuLoop();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        menu.stop();
    }

    private void menuLoop() throws IOException, InterruptedException {
        Choice choice;
        do {
            choice = menu.menuChoice();
            Runnable action = menu.menuAction(choice);
            if (action != null) {
                action.run();
            }
        } while (choice != Choice.QUIT);
    }
}
