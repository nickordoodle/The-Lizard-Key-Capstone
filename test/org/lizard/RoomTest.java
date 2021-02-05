package org.lizard;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomTest {
    private Room room;
    private Item item;
    private Item item2;
    private Item item3;
    private Lock lock;
    private Event event;
    private final Directions directions = new Directions();

    @Before
    public void setUp() throws Exception {
        room = new Room("testRoom");
        item = new Item("testItem", "This is a test item", true);
        item2 = new Item("anotherTestingItem", "This is a test item", true);
        item3 = new Item("thirdTestingItem", "This is a test item", false);
        event = new Event(2, directions.getDirection("west"));
        lock = new Lock(item, "this is a lock", event);
        // Add item to room
        room.addItemToRoom(item);
        room.addItemToRoom(item2);
    }

    @Test
    public void addItemToRoom() {
        room.addItemToRoom(item3);
        // Room should now have 2 items in it
        assertEquals(3, room.getItems().size());
        // testingItemAddToRoom should appear in the room with proper name, description and canGrab
        assertEquals("thirdTestingItem", room.getItems().get(2).getName());
        assertEquals("This is a test item", room.getItems().get(2).getDescription());
        assertEquals(false, room.getItems().get(2).isGrabable());
    }

    @Test
    public void has() {
        Item hasItemTest = new Item("testingItemHas", "test item", false);
        assertEquals(true, room.has(item));
        assertEquals(false, room.has(hasItemTest));
        assertNotEquals(true, item3);
    }

    @Test
    public void grabItem() {
        // Item exist in room and is returned
        assertEquals(item2, room.grabItem(item2));
        assertNotEquals(null, room.grabItem(item));
        assertNotEquals(item2, room.grabItem(item));
        // Item does not exist in room and null is returned
        assertEquals(null, room.grabItem(item3));
    }

    @Test
    public void getLock() {
        assertEquals("west", room.getLock("west"));
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