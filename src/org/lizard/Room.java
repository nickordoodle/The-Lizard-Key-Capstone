package org.lizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room extends GameDictionary.Noun {

    private Map<String, Room> exits = new HashMap<String, Room>();
    private List<Item> items = new ArrayList<>();

    public Room(String name){
        super(name);
        this.setExaminable(true);
    }

    public void addItemToRoom(Item item) {
        items.add(item);
    }

    public Item grabItem() {
        return items.remove(0);
    }

   public void createRoom(String direction, Room newExit) {
       exits.put(direction, newExit);
   }

   public Room goToRoom( String direction){
       if(exits.containsKey(direction)){
           return exits.get(direction);
       }
       else{
           return null;
       }
    }

    public String getName() {
        return super.getName();
    }

    public Map<String, Room> getExits() {
        return exits;
    }
}
