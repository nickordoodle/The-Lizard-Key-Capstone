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
                case 4:
                    use(command.getNoun(), command.getTargetNoun());
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
            //add it to player inventory
        }
    }

    public void use(GameDictionary.Noun noun, GameDictionary.Noun targetNoun) {
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
}
