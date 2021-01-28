package org.lizard;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Room currentRoom;
    private Map<String, Room> rooms = new HashMap<>();

    public Board(){
        createMap();
    }

    public void createMap(){

        Room living = new Room("living");
        Room library = new Room("library");
        Room bedroom = new Room("bedroom");
        Room kitchen = new Room("kitchen");

        living.createRoom("east",library);
        library.createRoom("west",living);
        living.createRoom("west",bedroom);
        bedroom.createRoom("east",living);
        living.createRoom("south",kitchen);
        kitchen.createRoom("north",living);

        living.addItemToRoom(new Item("key"));

        System.out.println(living.grabItem().isGrabable());

        this.currentRoom = living;


    }

    public void changeCurrentRoom(String direction){
        Map<String, Room> exits = currentRoom.getExits();

        if(exits.containsKey(direction)){
            currentRoom = exits.get(direction);
            System.out.println("current room is "+ currentRoom.getName());
        }
        else{
            System.out.println("Does not contain key");
        }

    }
}
