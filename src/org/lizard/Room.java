package org.lizard;

import java.util.HashMap;
import java.util.Map;

public class Room {

    private String name;
    private String roomDescription;
    private Map<String, Room> exits = new HashMap<String, Room>();
    private int key = 0;


    public Room(String name){
        this.name = name;
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

    public void addKey(){
       key = 1;
    }

    //Accessor Methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
