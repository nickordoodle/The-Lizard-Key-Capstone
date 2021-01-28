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
        Room loudRoom = new Room("loud room");
        Room bedroom = new Room("bedroom");
        Room kitchen = new Room("kitchen");
        Room creekyPath = new Room("creeky path");
        Room coalMine = new Room("coal mine");
        Room slideRoom = new Room("slide room");
        Room secretPassage = new Room("secretPassage");
        Room torchRoom = new Room("torch room");
        Room treasureRoom = new Room("treasureRoom");
        Room riddleRoom = new Room("riddle room");
        Room whisperingPassage = new Room("whispering passage");
        Room closet = new Room("closet");
        Room swingingStairs = new Room("swinging stairs");
        Room egyptianRoom = new Room("egyptian Room");
        Room landOfDead = new Room("land of dead room");
        Room artRoom = new Room("art room");
        Room engravingCave = new Room("engraving cave");
        Room floatingRoom = new Room("floating room");

        living.setRoomDescription("This is the living room! You can see a sofa in the middle and a picture of mountains hanging on the wall!");
        bedroom.setRoomDescription("This is the bedroom! You can see bed and a bed-side table with a lamp on top of it!");
        closet.setRoomDescription("This is a closet! The room is messy with clothes lying all over the floor!");
        riddleRoom.setRoomDescription("This is a riddle room. Look around if you can find some riddle questions to help you escape your situation!");

        living.createRoom("east",library);
        library.createRoom("west",living);
        living.createRoom("west",bedroom);
        bedroom.createRoom("east",living);
        living.createRoom("south",kitchen);
        kitchen.createRoom("north",living);
        library.createRoom("south",creekyPath);
        creekyPath.createRoom("north",library);
        creekyPath.createRoom("east",coalMine);
        coalMine.createRoom("west",creekyPath);
        library.createRoom("north",loudRoom);
        loudRoom.createRoom("south",library);
        loudRoom.createRoom("east",engravingCave);
        engravingCave.createRoom("west",loudRoom);
        engravingCave.createRoom("north",floatingRoom);
        floatingRoom.createRoom("west",landOfDead);
        landOfDead.createRoom("west",egyptianRoom);
        egyptianRoom.createRoom("east",landOfDead);
        egyptianRoom.createRoom("west",artRoom);
        artRoom.createRoom("east", egyptianRoom);
        egyptianRoom.createRoom("south",swingingStairs);
        swingingStairs.createRoom("north",egyptianRoom);
        swingingStairs.createRoom("south",living);
        living.createRoom("north",swingingStairs);
        bedroom.createRoom("south",closet);
        closet.createRoom("north",bedroom);
        closet.createRoom("west",riddleRoom);
        riddleRoom.createRoom("east",closet);
        riddleRoom.createRoom("west",whisperingPassage);
        whisperingPassage.createRoom("west",treasureRoom);
        treasureRoom.createRoom("south",torchRoom);
        torchRoom.createRoom("east",secretPassage);
        secretPassage.createRoom("north",kitchen);




        living.addItemToRoom(new Item("key"));


        this.currentRoom = living;


    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void changeCurrentRoom(String direction){
        Map<String, Room> exits = currentRoom.getExits();

        if(exits.containsKey(direction)){
            currentRoom = exits.get(direction);
            System.out.println("current room is "+ currentRoom.getName());
            System.out.println(currentRoom.getRoomDescription());
        }
        else{
            System.out.println("That is not an exit.");
        }

    }
}
