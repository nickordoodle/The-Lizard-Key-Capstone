package org.lizard;

public class Actions {
    private Board board;
    private Player player;
    private Room room;
    private GameDictionary gameDictionary;

    public Actions(Board board, Player player) {
        this.board = board;
        this.player = player;
    }

    public String execute(Command command) {


        if (command.getVerb() == null && command.getNoun() == null) {
            return "Wrong command";

        }
        if (command.getVerb() == null && command.getNoun() != null) {
            return (command.getNoun().getDescription());

        }
        if (command.getTargetNoun() != null && command.getVerb() != 4) {
            return ("You tried to use a " + command.getVerb() + " with 2 nouns? What you trying to break my code or something?");
        } else {
            switch (command.getVerb()) {
                case 1:
                    return grab(command.getNoun());

                case 2:
                    return move(command.getNoun());

                case 3:
                    return examine(command.getNoun());

                case 4:
                    return use(command.getNoun(), command.getTargetNoun());

            }
        }
        return null;
    }


    private String move(GameDictionary.Noun direction) {

        if (direction instanceof Direction) {
            return (board.changeCurrentRoom(((Direction) direction).getDirection()));
//            if(player.getHasKey() && board.getCurrentRoom().getName().equals("kitchen")) {
//                return ("YOU WIN!!!!!!!!!!!!!!!!!!!!");
//
//            }
        } else {
            return ("What??? you can't go there.");
        }

    }


    private String grab(GameDictionary.Noun noun) {
        if (noun == null) {
            return ("That doesn't exist");
        } else if (!noun.isGrabable()) {
            return ("You can't even grab a " + noun.getName());
        } else {

            //TODO grab item logic
            //get current room
            Room currentRoom = board.getCurrentRoom();
            //check if the room has the item else return fail
            Item grabbedItem = currentRoom.grabItem((Item) noun);
            //if it does exist pop it off room item list
            if (grabbedItem != null) {
                player.getInventory().add(grabbedItem);
                return ("You grabbed the " + noun.getName());
            } else {
                return ("You can't");
            }

        }
    }

    public String use(GameDictionary.Noun noun, GameDictionary.Noun targetNoun) {
        Lock targetLock = targetNoun.getLock();

        if (targetLock == null) {
            return ("Thats not how this works.");

        }
        if (!player.getInventory().has((Item) noun)) {
            return ("You don't have a " + noun.getName() + " in your inventory.");

        }
        if (!board.getCurrentRoom().has((Item) targetNoun) && !player.getInventory().has((Item) targetNoun)) {
            return ("There isn't a " + targetNoun.getName() + " here.");

        }
        if (targetLock.getNoun() == noun) {
            targetLock.printDescription();
            this.execute(targetLock.getCommand());
            targetNoun.deleteLock();

        }
        return null;
    }

    private String examine(GameDictionary.Noun noun) {


        Room currentRoom = board.getCurrentRoom();
        if (noun == null) { //examine
            return ("Examining room...\n " + currentRoom.getRoomDescription());


        } else if (!noun.isExaminable()) {
            return ("You can't examine " + noun.getName());
        } else {
            if (noun.getName().equals("items")) { //examine items

                return ("Items presnet in the " + currentRoom.getName() + " are: " + currentRoom.printItemsInRoom());
            } else if (currentRoom.has((Item) noun)) { //examine candle
                return ("Hi, I am a " + noun.getName());
            } else {

                return (noun.getName() + " is not present in the  " + currentRoom.getName());
            }

        }
    }
}
