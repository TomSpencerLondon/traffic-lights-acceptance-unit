package org.example.adapter.in.console;

import org.example.hexagon.application.IOHandler;
import org.example.hexagon.application.port.Clock;
import org.example.hexagon.domain.Choice;
import org.example.hexagon.domain.DuplicateRoadException;
import org.example.hexagon.domain.RoadCoordinator;
import org.example.hexagon.domain.RoadNotFoundException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MenuTest {

    @Test
    public void testMenuInitialization() {
        IOHandler ioHandler = mock(IOHandler.class);
        RoadCoordinator roadCoordinator = mock(RoadCoordinator.class);
        Clock clock = mock(Clock.class);

        Menu menu = new Menu(ioHandler, roadCoordinator, clock);

        assertNotNull(menu);
    }

    @Test
    public void testAddRoad() {
        IOHandler ioHandler = mock(IOHandler.class);
        RoadCoordinator roadCoordinator = mock(RoadCoordinator.class);

        when(ioHandler.readLine()).thenReturn("Main Street");

        Menu menu = new Menu(ioHandler, roadCoordinator, mock(Clock.class));

        menu.menuAction(Choice.ADD_ROAD).run();

        verify(ioHandler).print("Input road name:");
        verify(roadCoordinator).addRoad("Main Street");
        verify(ioHandler).printAndWait("Main Street added");
    }

    @Test
    public void testAddDuplicateRoad() throws DuplicateRoadException {
        IOHandler ioHandler = mock(IOHandler.class);
        RoadCoordinator roadCoordinator = mock(RoadCoordinator.class);

        when(ioHandler.readLine()).thenReturn("Main Street");
        doThrow(new DuplicateRoadException()).when(roadCoordinator).addRoad("Main Street");

        Menu menu = new Menu(ioHandler, roadCoordinator, mock(Clock.class));

        menu.menuAction(Choice.ADD_ROAD).run();

        verify(ioHandler).printAndWait("Failed to add Main Street");
    }

    @Test
    public void testDeleteRoad() {
        IOHandler ioHandler = mock(IOHandler.class);
        RoadCoordinator roadCoordinator = mock(RoadCoordinator.class);

        when(ioHandler.readLine()).thenReturn("Main Street");

        Menu menu = new Menu(ioHandler, roadCoordinator, mock(Clock.class));

        menu.menuAction(Choice.DELETE_ROAD).run();

        verify(ioHandler).print("Input road name to delete:");
        verify(roadCoordinator).deleteRoad("Main Street");
        verify(ioHandler).printAndWait("Main Street deleted");
    }


    @Test
    public void testDeleteNonExistentRoad() throws RoadNotFoundException {
        IOHandler ioHandler = mock(IOHandler.class);
        RoadCoordinator roadCoordinator = mock(RoadCoordinator.class);

        when(ioHandler.readLine()).thenReturn("NonExistentRoad");
        doThrow(new RoadNotFoundException()).when(roadCoordinator).deleteRoad("NonExistentRoad");

        Menu menu = new Menu(ioHandler, roadCoordinator, mock(Clock.class));

        menu.menuAction(Choice.DELETE_ROAD).run();

        verify(ioHandler).printAndWait("Failed to delete NonExistentRoad");
    }

    @Test
    public void testOpenSystem() {
        IOHandler ioHandler = mock(IOHandler.class);
        RoadCoordinator roadCoordinator = mock(RoadCoordinator.class);
        Clock clock = mock(Clock.class);

        when(clock.getSecondsPassed()).thenReturn(100);
        when(roadCoordinator.getRoads()).thenReturn(Collections.emptyList());
        when(ioHandler.readLine()).thenReturn("");

        Menu menu = new Menu(ioHandler, roadCoordinator, clock);

        menu.menuAction(Choice.OPEN_SYSTEM).run();

        verify(clock).setInSystemState(true);
        verify(ioHandler).print(anyString()); // Verify system info is printed
        verify(ioHandler).readLine();
        verify(clock).setInSystemState(false);
    }

    @Test
    public void testStop() {
        IOHandler ioHandler = mock(IOHandler.class);
        Clock clock = mock(Clock.class);

        Menu menu = new Menu(ioHandler, mock(RoadCoordinator.class), clock);

        menu.stop();

        verify(clock).stop();
        verify(ioHandler).print("Bye!");
    }



}
