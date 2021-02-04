package org.lizard;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomTest {
    Room room = new Room("testRoom");
    Item item = new Item("testItem", "This is a test item", true);

    @Before
    public void setUp() throws Exception {
        // Add item to room
        room.addItemToRoom(item);
    }

    @Test
    public void addItemToRoom() {
        Item item2 = new Item("testingItemAddToRoom", "This is a test item", true);
        room.addItemToRoom(item2);
        // Room should now have 2 items in it
        assertEquals(2, room.getItems().size());
        // testingItemAddToRoom should appear in the room with proper name, description and canGrab
        assertEquals("testingItemAddToRoom", room.getItems().get(1).getName());
        assertEquals("This is a test item", room.getItems().get(1).getDescription());
        assertEquals(true, room.getItems().get(1).isGrabable());
    }

    @Test
    public void has() {
//        assertEquals();
    }

    @Test
    public void grabItem() {
    }

    @Test
    public void getLock() {
    }

    @Test
    public void printItemsInRoom() {
    }

    @Test
    public void addLock() {
    }

    @Test
    public void removeLock() {
    }

    @Test
    public void createRoom() {
    }

    @Test
    public void getName() {
    }

    @Test
    public void getExits() {
    }

    @Test
    public void getRoomDescription() {
    }

    @Test
    public void getItems() {
    }

    @Test
    public void setRoomDescription() {
    }
}