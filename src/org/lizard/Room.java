package org.lizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room extends GameDictionary.Noun {

    private Map<String, Room> exits = new HashMap<>();
    private Map<String, Lock> locks = new HashMap<>();
    private List<Item> items = new ArrayList<>();
    private String roomDescription;


    public Room(String name) {
        super(name);
        this.setExaminable(true);
    }

    public void addItemToRoom(Item item) {
        items.add(item);
    }


    public boolean has(Item item) {
        return items.contains(item);
    }


    public Item grabItem(Item item) {
        int index = items.indexOf(item);
        if (index != -1) {
            Item returnedItem = items.get(index);
            items.remove(item);
            return returnedItem;
        } else {
            return null;
        }

    }

    public String printItemsInRoom() {
        StringBuilder itemsInRoom = new StringBuilder();

        for (Item item : items) {
            itemsInRoom.append(item.getName() + ", ");
        }
        if (itemsInRoom.length() > 2) {
            itemsInRoom.setLength(itemsInRoom.length() - 2);
        } else { //TODO
            return "There are no items present in the room! ";
        }
        return itemsInRoom.toString();
    }

    public void addLock(String direction, Lock lock) {

    }

    public void createRoom(String direction, Room newExit) {
        exits.put(direction, newExit);
    }

    //Accessor Methods
    public String getName() {
        return super.getName();
    }

    public Map<String, Room> getExits() {
        return exits;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }
}
