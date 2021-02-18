package org.lizard;

import java.util.*;

public class Actions {
    private final Board board;
    private final Player player;
    private final MyJFrame frame;
    private Combat combat;
    private List<Room> roomsVisited = new ArrayList<>();
    private boolean capeAdded = false;

    public Actions(Board board, Player player, MyJFrame frame, Combat combat) {
        this.board = board;
        this.player = player;
        this.frame = frame;
        this.combat = combat;
        roomsVisited.add(board.getCurrentRoom());
    }


    public String execute(Command command) {

        GameDictionary.Noun noun;
        GameDictionary.Noun targetNoun = null;
        Integer verb;
//        if (command.isAmbiguous()) {
//            return disambiguate(command);
//        }
        verb = command.getVerb();
        noun = command.getNoun() == null ? null : command.getNoun()[0];

        if (command.getTargetNoun() != null && command.getTargetNoun().length == 1) {
            targetNoun = command.getTargetNoun()[0];
        }


        if (verb == null && noun == null) {
            return "Wrong command";

        }
        if (verb == null) {
            return (noun.getDescription());

        }


        if (targetNoun == null && verb == 4) {
            return "Can't do that!";
        } else {
            switch (verb) {
                case 1:
                    return grab(noun);
                case 2:
                    return move(noun);
                case 3:
                    return examine(noun);
                case 4:
                    return use(noun, targetNoun);
                case 5:
                    return drop(noun);
                case 6:
//                    return delete(noun);
                case 7:
//                    return create(noun);
                case 8:
//                    return changeDescription();
                case 9:
                    return consumeHealingItem(noun);
                case 10:
                    return CheatCode.givePlayerAllKeysBesidesLizardKey(player.getInventory(), board);
                case 11:
                    return CheatCode.givePlayerSpecificItem(noun, player.getInventory(), board);
//                case 12:
//                    return CheatCode.openAllRooms(noun, player.getInventory(), board);
                case 99:
                    return bossAvailable("west", noun);
                case 1000:
                    return board.howToPlayInGame();
                case 2424:
                    return demonUnleashed();
            }
        }
        return null;
    }

    // Attempts to move in a certain given direction
    private String move(GameDictionary.Noun direction) {
        if (direction instanceof Directions.Direction) {
            // Winning condition and text if located in key room with lizard key
            if (player.hasWinningKey && board.getCurrentRoom().getName().equals("keyRoom")) {
                return "You use the lizard key on the door to exit.\n" +
                        "Darkness surrounds you and wind presses against your back as if the ground is being pulled beneath you.\n" +
                        "You close your eyes to avoid sickness, only for the movement around you to stop.\n" +
                        "Upon opening your eyes, you are staring out a small window with people in white scrubs passing in a hall.\n" +
                        "You turn around to see padded walls, only to realize that you have escaped Copernicus Rex Verwirrtheit Theodore for now.";
            }
            if (!roomsVisited.contains(board.getCurrentRoom())) {
                roomsVisited.add(board.getCurrentRoom());
            }
            return board.changeCurrentRoom(((Directions.Direction) direction).getDirection());
        } else {
            return ("What??? you can't go there.");
        }

    }


    private String grab(GameDictionary.Noun noun) {

        if (noun == null) {
            return ("You don't see anything like that.");
        } else if (!noun.isGrabable()) {
            return ("You cannot take " + noun.getName() + ".");
        } else if (player.getInventory().has(noun)) {
            return "You already have " + noun.getName() + " in your inventory.";
        } else {
            //get current room
            Room currentRoom = board.getCurrentRoom();
            //check if the room has the item else return fail
            GameDictionary.Noun grabbedItem = currentRoom.grabItem((Item) noun);
            //if it does exist pop it off room item list
            if (grabbedItem != null) {
                player.getInventory().add(grabbedItem);
                return ("You grabbed the " + noun.getName());
            } else {
                return ("You can't");
            }

        }
    }

    // Tries to use an item or "noun" that is passed in
    public String use(GameDictionary.Noun noun, GameDictionary.Noun targetNoun) {
        if (targetNoun instanceof Directions.Direction) {
            return unlockExit(((Directions.Direction) targetNoun).getDirection(), noun);
        }

        Lock targetLock = targetNoun.getLock();

        if (targetLock == null) {
            return ("That's not how this works.");

        }
        if (!player.getInventory().has((Item) noun)) { // Check inventory
            return ("You don't have a " + noun.getName() + " in your inventory.");

        }
        if (!board.getCurrentRoom().has((Item) targetNoun) && !player.getInventory().has((Item) targetNoun)) {
            return ("There isn't a " + targetNoun.getName() + " here.");

        }
        if (targetLock.getNoun() == noun) {

            this.execute(targetLock.getCommand());
            targetNoun.deleteLock();
            return targetLock.printDescription();

        }

        return null;
    }

    public String unlockExit(String direction, GameDictionary.Noun noun) {
        if (!player.getInventory().has(noun)) {
            return "You don't have a " + noun.getName() + ".";
        }
        Lock lock = board.getCurrentRoom().getLock(direction);
        if (lock != null && lock.getNoun().equals(noun)) {
            lock.printDescription();
            board.getCurrentRoom().removeLock(direction);
            return this.execute(lock.getCommand());
        } else {
            return "What did you think that would even accomplish?";
        }
    }

    public String bossAvailable(String direction, GameDictionary.Noun noun) {
        Lock lock = board.allRooms.get("egyptianRoom").getLock(direction);
        if (lock != null && lock.getNoun().equals(noun)) {
            board.allRooms.get("egyptianRoom").removeLock(direction);
            return lock.printDescription();
        } else {
            return "What did you think that would even accomplish?";
        }
    }

    private String examine(GameDictionary.Noun noun) {
        Room currentRoom = board.getCurrentRoom();
        Map<String, String> displayRooms = new HashMap<>();

        // If Rex/Boss was defeated, add the magic room to artRoom
        if (board.totalEnemies < -1 && !capeAdded) {
            Item magicCape = board.allItems.get("magic cape");
            // Add magic cape to artRoom and remove it from river
            board.allRooms.get("artRoom").addItemToRoom(magicCape);
            board.allRooms.get("river").removeItemFromRoom(magicCape);
            // Set cape added to true so only one cape gets added
            capeAdded = true;
        }
        if (noun == null) { //examine
            for (Map.Entry<String, Room> entry : currentRoom.getExits().entrySet()) {
                if (roomsVisited.contains(entry.getValue())) {
                    displayRooms.put(entry.getKey(), entry.getValue().getName());
                } else {
                    displayRooms.put(entry.getKey(), "?");
                }
            }
            String paths = "\nAvailable Paths: ";
            for (Map.Entry<String, String> entry : displayRooms.entrySet()) {
                paths = paths.concat(entry.getKey() + ": " + entry.getValue() + "\n");
            }
            return ("Examining room...\n " + currentRoom.getRoomDescription()) +
                    "\nItems present in the " + currentRoom.getName() + " are: " + currentRoom.printItemsInRoom() + "\n" +
                    paths;

        } else if (!noun.isExaminable()) {
            return ("You can't examine " + noun.getName());
        } else {
            if (noun instanceof Room) {
                if (noun == board.getCurrentRoom()) {
                    return board.getCurrentRoom().getRoomDescription();
                } else {
                    return "Your not in the " + noun.getName();
                }
            }
            if (noun.getName().equals("inventory")) {
                return player.getInventory().getDescription();
            }
            if (noun.getName().equals("items")) { //examine items
                return ("Items present in the " + currentRoom.getName() + " are: " + currentRoom.printItemsInRoom());
            } else if (currentRoom.has(noun) || player.getInventory().has(noun)) { //examine candle
                return noun.getDescription();
            } else {

                return (noun.getName() + " is not present in the  " + currentRoom.getName());
            }

        }
    }

    public String drop(GameDictionary.Noun noun) {
        GameDictionary.Noun droppedItem = player.getInventory().drop(noun);
        if (droppedItem == null) {
            return player.getName() + " how are you gonna drop something you don't have? How?";
        } else {
            board.getCurrentRoom().addItemToRoom(droppedItem);
            return "You dropped the " + droppedItem.getName() + " in the " + board.getCurrentRoom().getName();
        }

    }

//    private String disambiguate(Command command) {
//        GameDictionary.Noun[] noun = command.getNoun();
//        GameDictionary.Noun[] targetNoun = command.getTargetNoun();
//
//        Set<GameDictionary.Noun> availableNouns = new HashSet<>(player.getInventory().getItems());
//        availableNouns.addAll(board.getCurrentRoom().getItems());
//
//        Set<GameDictionary.Noun> nounSet = new HashSet<>(Arrays.asList(noun));
//
//        nounSet.retainAll(availableNouns);
//
//        if (nounSet.size() == 1) {
//            noun = new GameDictionary.Noun[]{nounSet.iterator().next()};
//        } else if (nounSet.size() > 1) {
//            Iterator<GameDictionary.Noun> iterator = nounSet.iterator();
//            return frame.decision(new ArrayList<>(nounSet), command);
//
//        } else {
//            noun = null;
//        }
//
//        if (targetNoun != null) {
//            System.out.println(Arrays.toString(targetNoun));
//            Set<GameDictionary.Noun> targetNounSet = new HashSet<>(Arrays.asList(targetNoun));
//            targetNounSet.retainAll(availableNouns);
//
//            if (targetNounSet.size() == 1) {
//                targetNoun = new GameDictionary.Noun[]{targetNounSet.iterator().next()};
//            } else if (targetNounSet.size() > 1) {
//                Iterator<GameDictionary.Noun> iterator = targetNounSet.iterator();
//                return frame.decision(new ArrayList<>(targetNounSet), command);
//            }
//
//        }
//
//        if (targetNoun != null && targetNoun.length == 1) {
//            return execute(new Command(command.getVerb(), noun, targetNoun));
//        } else {
//            return execute(new Command(command.getVerb(), noun));
//        }
//
//    }

    // Unleashes the demon/enemy and places the demon
    // in the whisperingPassage room
    private String demonUnleashed() {
        Enemy demon = new Enemy("Demon");
        demon.setEnemyHP(30);
        board.allRooms.get("whisperingPassage").setEnemy(demon);
        return "You hear whispers, loud whispers coming from the west.";
    }

    // Consumes a healing type of item
    // Increases the players health unless the player tries
    // to use healing brownies
    private String consumeHealingItem(GameDictionary.Noun noun) {
        if (!noun.getName().equals("healing brownies")) {
            return "You can't eat that!";
        }
        player.playerHP += 100;
        player.inventory.consumeItem(noun);
        return "Consumed " + noun.getName() + "! Your health has increased to " + player.playerHP + "!";
    }


    // Cheat code class to be used for future implementation
    // Contains all of the game cheat codes to enhance testing
    // and give the option for the player to use cheats
    static class CheatCode {

        // A cheat code that grabs all the keys
        // in the game and gives them to player
        // NOTE this does not give the player
        // the lizard key
        static String givePlayerAllKeysBesidesLizardKey(Player.Inventory inv, final Board board) {
            // Check for negative case and handle
            if (inv == null || board == null) {
                return "Oops! Cheat code get all keys besides lizard key did not work!";
            }
            Map<String, Item> allItems = board.getAllItems();
            // Iterate over the map entries
            for (Map.Entry<String, Item> item : allItems.entrySet()) {
                Item currItem = item.getValue();
                // Check if the item is a key but not the winning key
                if (currItem.getName().contains("key")
                        && !currItem.getName().equals("lizard key")) {
                    // Add to the player's inventory
                    inv.add(currItem);
                }
            }

            return "You activated a secret cheat code. You now have all" +
                    " of the keys in the game!!!";
        }

        static String givePlayerSpecificItem(GameDictionary.Noun requestedItem, Player.Inventory inv, final Board board) {

            try {
                // Get the name of the item
                String itemName = requestedItem.getName();
                Map<String, Item> allItems = board.getAllItems();
                // Iterate over the map entries
                for (Map.Entry<String, Item> item : allItems.entrySet()) {
                    String currItemName = item.getValue().getName();
                    // Check if it is the requested item
                    if (currItemName.contains(itemName)) {
                        // Add to the player's inventory
                        inv.add(item.getValue());
                    }
                }
            } catch (NullPointerException e) {
                return "Oops! Cheat code get " + requestedItem + " item did not work! Try" +
                        " cheatcodeget <item>";
            }

            return (requestedItem.getName() + " added to inventory!");
        }

//        static String openAllRooms(Map<String, Room> allRooms, Player.Inventory inv, final Board board) {
//
//            try {
//                // Iterate over the map entries
//                for (Map.Entry<String, Room> room : allRooms.entrySet()) {
//                    // Check if it is the requested item
//                    roomsVisited.add(room);
//
//                }
//            } catch (NullPointerException e) {
//                return "Oops! Cheat code all rooms didn't work";
//            }
//
//            return ("All rooms marked as visited!");
//        }


    }

}
