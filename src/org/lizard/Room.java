package org.lizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room extends GameDictionary.Noun {

    private final Map<String, Room> exits = new HashMap<>();
    private final Map<String, Lock> locks = new HashMap<>();
    private final List<GameDictionary.Noun> items = new ArrayList<>();
    private String roomDescription;


    public Room(String name){
        super(name);
        this.setExaminable(true);
    }

    public void addItemToRoom(GameDictionary.Noun item) {
        items.add(item);
    }

    public boolean has(GameDictionary.Noun item) {
        return items.contains(item);
    }

    public GameDictionary.Noun grabItem(Item item) {
        int index = items.indexOf(item);
        if(index != -1) {
            GameDictionary.Noun returnedItem = items.get(index);
            items.remove(item);
            return returnedItem;
        } else {
            return null;
        }

    }
    public Lock getLock(String direction) {
        return locks.get(direction);
    }
    public void addLock(String direction, Lock lock) {
        locks.put(direction, lock);
    }

    public Lock removeLock(String direction) {
        return locks.remove(direction);
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
        if(items.size() > 0) {
            StringBuilder roomSB = new StringBuilder();
            roomSB.append(roomDescription + "\n");
            roomSB.append("\nYou look for items in this room.\n");
            for (GameDictionary.Noun item : items) {
                roomSB.append("Oh it's a " + item.getName() + ". " + item.getDescription() + "\n");
            }
            return roomSB.toString();
        }
        return roomDescription;
    }
    public List<GameDictionary.Noun> getItems() {
        return items;
    }
    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }
}
