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
//        Room library = new Room("library");
//        Room loudRoom = new Room("loud room");
        Room bedroom = new Room("bedroom");
        Room kitchen = new Room("kitchen");
//        Room creekyPath = new Room("creeky path");
//        Room coalMine = new Room("coal mine");
//        Room slideRoom = new Room("slide room");
        Room secretPassage = new Room("secretPassage");
        Room torchRoom = new Room("torch room");
        Room treasureRoom = new Room("treasureRoom");
        Room riddleRoom = new Room("riddle room");
        Room whisperingPassage = new Room("whispering passage");
        Room closet = new Room("closet");
//        Room swingingStairs = new Room("swinging stairs");
//        Room egyptianRoom = new Room("egyptian Room");
//        Room landOfDead = new Room("land of dead room");
//        Room artRoom = new Room("art room");
//        Room engravingCave = new Room("engraving cave");
//        Room floatingRoom = new Room("floating room");

        living.setRoomDescription("\nHeat radiates from an oversized fireplace with stockings hanging dangerously too close to flames, likely causing the extra singed aroma.\n" +
                "An old night stand features a stack of books that could compete with the Leaning Tower of Pisa.\n" +
                "Across the room, a portrait of a man with knotted hair, fractured glasses, and a twisted smile, stares in your direction.");
        bedroom.setRoomDescription("\nAn unmade child's bed sits in the center of the room, the foot of the bed facing the wall.\n" +
                "Several dolls with cracked and fractured faces, follow you as you move around the room.\n" +
                "From beneath the bed, a chill crawls up your legs, up your back, and around the sides of your neck.");
        closet.setRoomDescription("\nA single light dimly guides you through a small space.\n" +
                "Packed tight with clothes from eras that don't make sense to one another, you struggle to push your way through.\n" +
                "Several shoe boxes lay atop the shelves, covered in dust.");
        riddleRoom.setRoomDescription("\nThe empty room bares a tulip filled wallpaper with a single portion ripped away to reveal a riddle.\n" +
                "With gold shining high in the sky, a bird cannot soar if it cannot fly.\n" +
                "A stream that flows is a life to grow, a stream not of water may death undergo.\n" +
                "Unlocking a secret is not a forfeit, go forward with the lizard to be rewarded.");
        kitchen.setRoomDescription("\nLike darts, knives hang from the vaulted ceiling in varying sizes.\n" +
                "Across the white marble island, an oven blasts heat in your direction, carrying the smell of blackened cookies.\n" +
                "As if the walls were made of paper, conversations can be heard in the other room.");
        whisperingPassage.setRoomDescription("\nWith each step you take down the cobblestone path, a footstep behind you can be heard.\n" +
                "Whispers in your ear sound as if someone is right behind you, but upon turning around, no one is there.\n" +
                "In the center of the path is a rotted bench with fresh flowers and a photograph.");
        treasureRoom.setRoomDescription("\nPiles upon piles of gold and gems climb to the top of the room, surrounding a platinum river flowing through the middle.\n" +
                "A single lizard shaped key stands on a single pedestal in the center of the river.");
        torchRoom.setRoomDescription("\nTiki torches line a maze through the room.\n" +
                "In the center of the room is an in-ground hot tub filled with lava, bubbling over the sides.");
        secretPassage.setRoomDescription("\nLong and narrow before you, an empty passage shows a single door at the opposite end.");

//        living.createRoom("east",library);
//        library.createRoom("west",living);
        living.createRoom("west",bedroom);
        bedroom.createRoom("east",living);
        living.createRoom("south",kitchen);
        kitchen.createRoom("north",living);
//        library.createRoom("south",creekyPath);
//        creekyPath.createRoom("north",library);
//        creekyPath.createRoom("east",coalMine);
//        coalMine.createRoom("west",creekyPath);
//        library.createRoom("north",loudRoom);
//        loudRoom.createRoom("south",library);
//        loudRoom.createRoom("east",engravingCave);
//        engravingCave.createRoom("west",loudRoom);
//        engravingCave.createRoom("north",floatingRoom);
//        floatingRoom.createRoom("west",landOfDead);
//        landOfDead.createRoom("west",egyptianRoom);
//        egyptianRoom.createRoom("east",landOfDead);
//        egyptianRoom.createRoom("west",artRoom);
//        artRoom.createRoom("east", egyptianRoom);
//        egyptianRoom.createRoom("south",swingingStairs);
//        swingingStairs.createRoom("north",egyptianRoom);
//        swingingStairs.createRoom("south",living);
//        living.createRoom("north",swingingStairs);
        bedroom.createRoom("south",closet);
        closet.createRoom("north",bedroom);
        closet.createRoom("west",riddleRoom);
        riddleRoom.createRoom("east",closet);
        riddleRoom.createRoom("west",whisperingPassage);
        whisperingPassage.createRoom("west",treasureRoom);
        treasureRoom.createRoom("south",torchRoom);
        torchRoom.createRoom("east",secretPassage);
        secretPassage.createRoom("north",kitchen);



        Item match = new Item("match");
        Item candle = new Item("candle", new Lock(match, "The candle has lit. You run west!", new Command(2, new Direction("west"))));
        closet.addItemToRoom(match);
        living.addItemToRoom(candle);
        treasureRoom.addItemToRoom(new Item("key"));


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
