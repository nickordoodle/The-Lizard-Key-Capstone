package org.lizard;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
            if(command.isAmbiguous()) {
                return disambiguate(command);
            }
        verb = command.getVerb();
        noun = command.getNoun() == null ? null : command.getNoun()[0];

        if(command.getTargetNoun() != null) {
            targetNoun = command.getTargetNoun()[0];
        }


        if (verb == null && noun == null) {
            return "Wrong command";

        }
        if (verb == null) {
            return (noun.getDescription());

        }

//        if(noun == null) {
//            return "WTF";
//        }
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
                case 99:
                    return bossAvailable("west", noun);
                case 2424:
                    return demonUnleashed();
            }
        }
        return null;
    }



    private String move(GameDictionary.Noun direction) {
        if(direction instanceof Directions.Direction) {
            if(player.hasWinningKey && board.getCurrentRoom().getName().equals("keyRoom")) {
                return "You use the lizard key on the door to exit.\n" +
                        "Darkness surrounds you and wind presses against you back as if the ground is being pulled beneath you.\n" +
                        "You close your eyes to avoid sickness, only for the movement around you to stop.\n" +
                        "Upon opening your eyes, you are staring out a small window with people in white scrubs passing in a hall.\n" +
                        "You turn around to see padded walls, only to realize that you have escaped Copernicus Rex Verwirrtheit Theodore's for now.";
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
            return ("That doesn't exist");

        } else if (!noun.isGrabable()) {
            return ("You can't even grab a " + noun.getName());
        } else if(player.getInventory().has(noun)) {
           return "You already have that in your inventory";
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

    public String use(GameDictionary.Noun noun, GameDictionary.Noun targetNoun) {
        if(targetNoun instanceof Directions.Direction) {
            return unlockExit(((Directions.Direction) targetNoun).getDirection(), noun);

        }

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

            this.execute(targetLock.getCommand());
            targetNoun.deleteLock();
            return targetLock.printDescription();

        }

        return null;
    }
    public String unlockExit(String direction, GameDictionary.Noun noun) {
        if(!player.getInventory().has(noun)) {
            return "You don't have that on your person";
        }
        Lock lock = board.getCurrentRoom().getLock(direction);
        if(lock != null && lock.getNoun().equals(noun)) {
            lock.printDescription();
            board.getCurrentRoom().removeLock(direction);
            return this.execute(lock.getCommand());
        } else {
            return "What did you think that would even accomplish?";
        }
    }
    public String bossAvailable(String direction, GameDictionary.Noun noun) {
        Lock lock = board.allRooms.get("egyptianRoom").getLock(direction);
        if(lock != null && lock.getNoun().equals(noun)) {
            board.allRooms.get("egyptianRoom").removeLock(direction);
            return lock.printDescription();
        } else {
            return "What did you think that would even accomplish?";
        }
    }

    private String examine(GameDictionary.Noun noun){
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
            for (Map.Entry<String, Room> entry: currentRoom.getExits().entrySet()) {
                if (roomsVisited.contains(entry.getValue())) {
                    displayRooms.put(entry.getKey(), entry.getValue().getName());
                } else {
                    displayRooms.put(entry.getKey(), "?");
                }
            }
            System.out.println(currentRoom.getItems());

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
            if (noun.getName().equals("items")) { //examine items
                return ("Items present in the " + currentRoom.getName() + " are: " + currentRoom.printItemsInRoom());
            } else if (currentRoom.has((Item) noun)) { //examine candle
                return noun.getDescription();
            } else {

                return (noun.getName() + " is not present in the  " + currentRoom.getName());
            }

        }
    }

    public String drop(GameDictionary.Noun noun) {
        GameDictionary.Noun droppedItem = player.getInventory().drop(noun);
        if(droppedItem == null) {
            return player.getName() + " how are you gonna drop something you don't have? How?";
        } else {
            board.getCurrentRoom().addItemToRoom(droppedItem);
            return "You dropped the " + droppedItem.getName() + " in the " + board.getCurrentRoom().getName();
        }

    }

    private String disambiguate(Command command) {
        GameDictionary.Noun[] noun = command.getNoun();
        GameDictionary.Noun[] targetNoun = command.getTargetNoun();

        Set<GameDictionary.Noun> availableNouns = new HashSet<>(player.getInventory().getItems());
        availableNouns.addAll(board.getCurrentRoom().getItems());

        Set<GameDictionary.Noun> nounSet = new HashSet<>(Arrays.asList(noun));

        nounSet.retainAll(availableNouns);

        if(nounSet.size() == 1) {
            noun = new GameDictionary.Noun[]{nounSet.iterator().next()};
        } else if(nounSet.size() > 1) {
            Iterator<GameDictionary.Noun> iterator = nounSet.iterator();
            noun = new GameDictionary.Noun[]{iterator.next()};
//            noun = specifyNoun(nounSet);
        } else {
            noun = null;
        }

        if(targetNoun != null) {
            Set<GameDictionary.Noun> targetNounSet = new HashSet<>(Arrays.asList(targetNoun));
            targetNounSet.retainAll(availableNouns);

            if(targetNounSet.size() == 1) {
                targetNoun = new GameDictionary.Noun[]{targetNounSet.iterator().next()};
            } else if(targetNounSet.size() > 1) {
                Iterator<GameDictionary.Noun> iterator = targetNounSet.iterator();
                noun = new GameDictionary.Noun[]{iterator.next()};
//                noun = specifyNoun(targetNounSet);
            }

        }

        if(targetNoun != null && targetNoun.length == 1) {
            return execute(new Command(command.getVerb(), noun, targetNoun));
        } else {
            return execute(new Command(command.getVerb(), noun));
        }

    }

    public GameDictionary.Noun[] specifyNoun(Set<GameDictionary.Noun> nounSet) {
        List<GameDictionary.Noun> nounList = new ArrayList<>(nounSet);

        while (true) {
            nounList.forEach(noun -> {
                System.out.println("You see a " + noun.getName());
            });
//            String userInput = Game.prompter.promptPlayer("Which one?");
            String userInput = frame.decision();
            List<GameDictionary.Noun> validNoun = new ArrayList<>();

            int i = 0;

            for (int j = 0; j < nounList.size(); j++) {
                if (nounList.get(j).getName().contains(userInput)) {
                    validNoun.add(nounList.get(j));
                }
            }

            if (validNoun.size() == 1) {
                return validNoun.toArray(new GameDictionary.Noun[1]);
            } else {
                System.out.println("Can you read? Pick one.");
            }
        }

    }
    private String demonUnleashed() {
        Enemy demon = new Enemy("Demon");
        demon.setEnemyHP(30);
        board.allRooms.get("whisperingPassage").setEnemy(demon);
        return "You hear whispers, loud whispers coming from the west.";
    }

}
