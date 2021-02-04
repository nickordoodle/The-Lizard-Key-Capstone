package org.lizard;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Actions {
    private final Board board;
    private final Player player;
    private final MyJFrame frame;

    public Actions(Board board, Player player, MyJFrame frame) {
        this.board = board;
        this.player = player;
        this.frame = frame;
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
            return "Nah dawg";
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
            }
        }
        return null;
    }



    private String move(GameDictionary.Noun direction) {

        if(direction instanceof Directions.Direction) {
            if(player.hasWinningKey && board.getCurrentRoom().getName().equals("living")) {
                return "You used the key in the living room. You teleport and wake up from your dream. You notice its 7:30 am. time to go to work.";
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
        if(lock.getNoun().equals(noun)) {
            lock.printDescription();
            board.getCurrentRoom().removeLock(direction);
            return this.execute(lock.getCommand());
        } else {
            return "What did you think that would even accomplish?";
        }
    }

    private String examine(GameDictionary.Noun noun){
        Room currentRoom = board.getCurrentRoom();
        if (noun == null) { //examine
            return ("Examining room...\n " + currentRoom.getRoomDescription());


        } else if (!noun.isExaminable()) {
            return ("You can't examine " + noun.getName());
        } else {
            if (noun.getName().equals("items")) { //examine items
                return ("Items presnet in the " + currentRoom.getName() + " are: " + currentRoom.printItemsInRoom());
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
                System.out.println("You see a " + noun.getName());;
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

}
