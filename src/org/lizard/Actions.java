package org.lizard;

import java.awt.*;
import java.util.List;

public class Actions {
    private Board board;
    private Player player;

    public Actions(Board board, Player player) {
        this.board = board;
        this.player = player;
    }

    public void execute(Command command) {


        if (command.getVerb() == null && command.getNoun() == null){
            System.out.println("Wrong command");
            return;
        }
        if(command.getVerb() == null && command.getNoun() != null) {
            System.out.println(command.getNoun().getDescription());
            return;
        }
        if(command.getTargetNoun() != null && command.getVerb() != 4) {
            System.out.println("You tried to use a " + command.getVerb() + " with 2 nouns? What you trying to break my code or something?");
        }
        else{
            switch(command.getVerb()) {
                case 1:
                    grab(command.getNoun());
                    break;
                case 2:
                    move(command.getNoun());
                    break;
                case 3:
                    examine(command.getNoun());
                    break;
                case 4:
                    use(command.getNoun(), command.getTargetNoun());
                    break;
                case 5:
                    drop(command.getNoun());
                    break;
            }
        }
    }

    private void move(GameDictionary.Noun direction) {

        if(direction instanceof Direction) {
            board.changeCurrentRoom(((Direction) direction).getDirection());
            if(player.getHasKey() && board.getCurrentRoom().getName().equals("kitchen")) {
                System.out.println("YOU WIN!!!!!!!!!!!!!!!!!!!!");
                System.exit(1);
            }
        } else {
            System.out.println("What??? you can't go there.");
        }

    }


    private void grab(GameDictionary.Noun noun) {
        if(noun == null) {
            System.out.println("That doesn't exist");
        }
        else if(!noun.isGrabable()) {
            System.out.println("You can't even grab a " + noun.getName());
        } else {

            //TODO grab item logic
            //get current room
            Room currentRoom = board.getCurrentRoom();
            //check if the room has the item else return fail
            Item grabbedItem = currentRoom.grabItem((Item) noun);
            //if it does exist pop it off room item list
            if(grabbedItem != null) {
                player.getInventory().add(grabbedItem);
                System.out.println("You grabbed the " + noun.getName());
            } else {
                System.out.println("You can't");
            }

        }
    }

    public void use(GameDictionary.Noun noun, GameDictionary.Noun targetNoun) {
        if(targetNoun instanceof Direction) {
            unlockExit(((Direction) targetNoun).getDirection(), noun);
            return;
        }
        Lock targetLock = targetNoun.getLock();

        if(targetLock == null) {
            System.out.println("Thats not how this works.");
            return;
        }
        if(!player.getInventory().has((Item) noun)) {
            System.out.println("You don't have a " + noun.getName() + " in your inventory.");
            return;
        }
        if(!board.getCurrentRoom().has((Item) targetNoun) && !player.getInventory().has((Item) targetNoun)) {
            System.out.println("There isn't a " + targetNoun.getName() + " here.");
            return;
        }
        if(targetLock.getNoun() == noun) {
            targetLock.printDescription();
            this.execute(targetLock.getCommand());
            targetNoun.deleteLock();

        }
    }
    public void unlockExit(String direction, GameDictionary.Noun noun) {
        if(!player.getInventory().has((Item) noun)) {
            System.out.println("You don't have that on your person");
            return;
        }
        Lock lock = board.getCurrentRoom().getLock(direction);
        if(lock.getNoun().equals(noun)) {
            lock.printDescription();
            board.getCurrentRoom().removeLock(direction);
            this.execute(lock.getCommand());
        }
    }

    private void examine(GameDictionary.Noun noun){
       Room currentRoom = board.getCurrentRoom();
        if(noun == null){
            System.out.println("Examining room...");
            //prints description of the current room
            System.out.println(currentRoom.getRoomDescription());

        }
        else{
            System.out.println(noun.getDescription());
        }

        //if noun, check if that noun is in the currentRoom
        //if it is, get noun.getDescription

    }

    public void drop(GameDictionary.Noun noun) {
        Item droppedItem = player.getInventory().drop((Item) noun);
        if(droppedItem == null) {
            System.out.println(player.getName() + " how are you gonna drop something you don't have? How?");
        } else {
            board.getCurrentRoom().addItemToRoom(droppedItem);
            System.out.println("You dropped the " + droppedItem.getName() + " in the " + board.getCurrentRoom().getName());
        }

    }
}
