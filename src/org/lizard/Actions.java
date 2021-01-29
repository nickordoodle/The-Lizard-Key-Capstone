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
        if(command.getVerb() == null ){
            System.out.println("Wrong command");
        }
        else{
            switch(command.getVerb()) {
                case 1:
                    grab(command.getNoun());
                    break;
                case 2:
                    move(command.getNoun());
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

}
