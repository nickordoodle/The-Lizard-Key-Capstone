package org.lizard;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.*;
import java.util.List;

public class Board {
    private Room currentRoom;
    private final Directions directions = new Directions();
    Map<String, Room> allRooms = new HashMap<>();
    private Map<String, Map<String, String>> allExits = new HashMap<>();
    Map<String, Item> allItems = new HashMap<>();
    String[][] rooms = new String[8][8];
    int totalEnemies = 3;

    public Board() {
        createMap();
        addToVisitedRooms("key room");
    }

    public Map<String, Item> getAllItems() {
        return this.allItems;
    }

    public void addToVisitedRooms(String roomNombre) {
        switch (roomNombre) {
            case "floating room":
                rooms[7][0] = "Floating Room";
                break;
            case "land of the dead":
                rooms[6][0] = "Land of the Dead";
                break;
            case "egyptian room":
                rooms[5][0] = "Egyptian Room";
                break;
            case "art room":
                rooms[4][0] = "Art Room";
                break;
            case "engraving cave":
                rooms[7][1] = "Engraving Cave";
                break;

            case "loud room":
                rooms[6][1] = "Loud Room";
                break;

            case "swinging stairs":
                rooms[5][1] = "Swinging Stairs";
                break;

            case "library":
                rooms[6][2] = "Library";
                break;

            case "key room":
                rooms[5][2] = "Key Room";
                break;

            case "psych ward":
                rooms[4][2] = "Psych Ward";
                break;

            case "coal mine":
                rooms[7][3] = "Coal Mine";
                break;

            case "creaky path":
                rooms[6][3] = "Creaky Path";
                break;

            case "kitchen":
                rooms[5][3] = "Kitchen";
                break;

            case "closet":
                rooms[4][3] = "Closet";
                break;

            case "riddle room":
                rooms[3][3] = "Riddle Room";
                break;

            case "whispering passage":
                rooms[2][3] = "Whispering Passage";
                break;

            case "treasure room":
                rooms[1][3] = "Treasure Room";
                break;

            case "river":
                rooms[0][3] = "River";
                break;

            case "volcano":
                rooms[1][4] = "Volcano";
                break;

            case "secret passage":
                rooms[2][4] = "Secret Passage";
                rooms[3][4] = "Secret Passage";
                rooms[4][4] = "Secret Passage";
                rooms[5][4] = "Secret Passage";
        }
    }

    private void createMap() {
        // Call XMLParser to gain access to a nodeList of all the rooms in the XML file
        NodeList nodeList = XMLParser("Rooms.xml", "room");
        // Create a for loop to go the length of the nodeList
        for (int itr = 0; itr < nodeList.getLength(); itr++) {
            Node node = nodeList.item(itr);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                // Create a way to hold the exits for easch room before placing them in allExits
                Map<String, String> exits = new HashMap<>();
                Element eElement = (Element) node;

                // Gain and save all relevant information needed to create a Room class object
                String roomId = eElement.getElementsByTagName("id").item(0).getTextContent();

                // Get roomName and create a new Room class object
                String roomName = eElement.getElementsByTagName("roomName").item(0).getTextContent();
                Room room = new Room(roomName);

                String roomDescription = eElement.getElementsByTagName("roomDescription").item(0).getTextContent();
                // Add description to Room class object
                room.setRoomDescription(roomDescription);

                // List of all of the possible locked paths in this Room class object
                List<Map<String, String>> locks = new ArrayList<>();


                // Gain all exits for this particular room and save them in exits
                for (int itr2 = 0; itr2 < eElement.getElementsByTagName("exit").getLength(); itr2++) {
                    // Create an object with all of the directions and the corresponding rooms for this particular Room class object
                    exits.put(eElement.getElementsByTagName("path").item(itr2).getTextContent(), eElement.getElementsByTagName("pathName").item(itr2).getTextContent());

                }

                // Add the new exits Map object with the corresponding room name to allExits in order to build the Room class object exits later
                allExits.put(roomName, exits);

                // Add the new Room to allRooms, so after all rooms and exits have been put together, exits can be assigned to the correct rooms
                allRooms.put(roomName, room);
            }
        }
        // Add all exits to their respective rooms
        addExitsToRooms();
        // Add all items to their respective rooms
        addItemsToRooms();
        // Add locks to rooms
        addLocksToRooms();
        // Add all monsters/enemies to board
        addMonstersToBoard();

        // Set starting room.
        this.currentRoom = allRooms.get("key room");
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public String changeCurrentRoom(String direction) {
        Map<String, Room> exits = getCurrentRoom().getExits();
        Lock lock = getCurrentRoom().getLock(direction);
        String result = "";
        if (exits.containsKey(direction)) {
            if (lock == null || !lock.isLocked()) {
                setCurrentRoom(exits.get(direction));
                addToVisitedRooms(getCurrentRoom().getName());
                result = "You have entered the " + getCurrentRoom().getName() + "\n\n" + getCurrentRoom().getRoomDescription();
            } else {
                if ((getCurrentRoom().getName().equalsIgnoreCase("Treasure Room") && direction.equalsIgnoreCase("west")) || getCurrentRoom().getName().equalsIgnoreCase("River") && direction.equalsIgnoreCase("east")) {
                    result = "I know you aren't a magician, but if you were maybe you could use something from your bag of tricks to help you cross the river.";
                } else if (getCurrentRoom().getName().equalsIgnoreCase("") && direction.equalsIgnoreCase("east")) {
                    result = "The door is locked. Maybe you can find something to open it?";
                } else if (getCurrentRoom().getName().equalsIgnoreCase("Egyptian Room") && direction.equalsIgnoreCase("west")) {
                    result = "Because you in the Egyptian Room and you are a huge fan of 80s rock band, The Bangles. You try to walk like an Egyptian, but nothing happens. Come back here when you have rid this world of evil.";
                } else if (getCurrentRoom().getName().equalsIgnoreCase("Egyptian Room") && direction.equalsIgnoreCase("east")) {
                    result = "The door is locked. You feel something sinister on the other side. It sends chills down to your bones.";
                }
            }
        } else {
            result = "You can't go that way!";
        }
        return result;
    }

    public String teleportRoom(Room newRoom) {
        setCurrentRoom(newRoom);
        return "In the blink of an eye, you use the magic cape to teleport to " + getCurrentRoom().getName() + "." + "\n\n" + getCurrentRoom().getRoomDescription();
    }

    private NodeList XMLParser(String pathName, String tagName) {
        NodeList nodeList;
        try {
            // Create a class loader to retrieve the resource
            InputStream resourceInputStream = getClass().getClassLoader().getResourceAsStream(pathName);

            // An instance of factory that gives a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // An instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();

            // Parse the stream containing resource data
            Document doc = db.parse(resourceInputStream);
            doc.getDocumentElement().normalize();
            nodeList = doc.getElementsByTagName(tagName);

            return nodeList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void addExitsToRooms() {
        // Iterate over the nested Map objects in order to add all exits to their respective room
        Iterator<Map.Entry<String, Map<String, String>>> iterator = allExits.entrySet().iterator();
        while (iterator.hasNext()) {
            // When calling entry.getKey(), you will get the String room name from allExits
            // When calling entry.getValue(), you will get the Map<String, String> of the respective rooms exits
            Map.Entry<String, Map<String, String>> entry = iterator.next();

            // Create a second iterator for the inner Map
            Iterator<Map.Entry<String, String>> iterator2 = entry.getValue().entrySet().iterator();
            while (iterator2.hasNext()) {
                // entry2.getKey() will return the String direction ("west", "north", etc)
                // entry2.getValue() will return the String room ("Library", "Kitchen", etc)
                Map.Entry<String, String> entry2 = iterator2.next();
                // Get the Room class object from allRooms to create a new exit with the proper room exit
                allRooms.get(entry.getKey()).createRoom(entry2.getKey(), allRooms.get(entry2.getValue()));
            }
        }
    }


    private void addItemsToRooms() {

        NodeList nodeList = XMLParser("Items.xml", "item");
        // Iterate over each item
        for (int itr = 0; itr < nodeList.getLength(); itr++) {
            // Make a node from nodeList at the current index
            Node node = nodeList.item(itr);
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                // Make the node an element to gain access to text content
                Element itemElement = (Element) node;

                // Save values of item
                String itemId = itemElement.getElementsByTagName("id").item(0).getTextContent();
                String itemName = itemElement.getElementsByTagName("name").item(0).getTextContent();
                String roomName = itemElement.getElementsByTagName("roomName").item(0).getTextContent();
                String lockKey = itemElement.getElementsByTagName("lockKey").item(0).getTextContent();
                String lockMessage = itemElement.getElementsByTagName("lockMessage").item(0).getTextContent();
                String commandInt = itemElement.getElementsByTagName("commandInt").item(0).getTextContent();
                String commandDirection = itemElement.getElementsByTagName("commandDirection").item(0).getTextContent();
                String itemDescription = itemElement.getElementsByTagName("description").item(0).getTextContent();
                String canGrab = itemElement.getElementsByTagName("canGrab").item(0).getTextContent();

                // Create new instance of the item
                if (lockKey.equalsIgnoreCase("none")) {
                    Item roomItem = new Item(itemName, itemDescription, Boolean.parseBoolean(canGrab));
                    allItems.put(itemName, roomItem);
                    // Add newly created item to its respective room
                    allRooms.get(roomName).addItemToRoom(roomItem);
                } else {
                    GameDictionary.Noun noun;
                    if (commandDirection.equalsIgnoreCase("")) {
                        noun = allItems.get(lockKey);
                    } else {
                        noun = directions.getDirection(commandDirection);
                    }
                    Item roomItem = new Item(itemName, new Lock(allItems.get(lockKey), lockMessage, new Event(Integer.parseInt(commandInt), noun)), itemDescription, Boolean.parseBoolean(canGrab));
                    // Add newly created item to its respective room
                    allRooms.get(roomName).addItemToRoom(roomItem);
                }
            }
        }
    }

    private void addMonstersToBoard() {

        NodeList nodeList = XMLParser("Enemies.xml", "enemy");
        // Iterate over each item
        for (int itr = 0; itr < nodeList.getLength(); itr++) {
            // Make a node from nodeList at the current index
            Node node = nodeList.item(itr);
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                // Make the node an element to gain access to text content
                Element monsterElement = (Element) node;

                String enemyName = monsterElement.getElementsByTagName("name").item(0).getTextContent();
                String enemyHP = monsterElement.getElementsByTagName("hp").item(0).getTextContent();
                String roomName = monsterElement.getElementsByTagName("roomName").item(0).getTextContent();

                // Create enemy
                Enemy enemy = new Enemy(enemyName, Integer.parseInt(enemyHP));

                // Add enemy to respective room
                allRooms.get(roomName).setEnemy(enemy);
            }
        }
    }

    public boolean enemyDied() {
        totalEnemies--;
        return totalEnemies == 1;
    }

    private void addLocksToRooms() {
        NodeList nodeList = XMLParser("RoomLocks.xml", "room");
        // Iterate over each rooms
        for (int itr = 0; itr < nodeList.getLength(); itr++) {
            // Make a node from nodeList at the current index
            Node node = nodeList.item(itr);
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                // Make the node an element to gain access to text content
                Element itemElement = (Element) node;

                // Save values of the lock
                String roomName = itemElement.getElementsByTagName("roomName").item(0).getTextContent();
                String commandDirection = itemElement.getElementsByTagName("path").item(0).getTextContent();
                String lockKey = itemElement.getElementsByTagName("lockKey").item(0).getTextContent();
                String lockMessage = itemElement.getElementsByTagName("lockMessage").item(0).getTextContent();
                String commandInt = itemElement.getElementsByTagName("commandInt").item(0).getTextContent();

                Room room = allRooms.get(roomName);
                Lock newLock = new Lock(allItems.get(lockKey), lockMessage, new Event(Integer.parseInt(commandInt), directions.getDirection(commandDirection)));
                newLock.setLocked(true);
                room.addLock(commandDirection, newLock);
            }
        }
    }

    String howToPlayInGame() {
        return """             
                Welcome to a text-based game, The Lizard Key! 
                Your path is a dangerous one, there are two enemies lurking in the shadows that you must have the courage to defeat. Only then can you face your biggest opponent Mr.Rex the sole reason you are trapped in this frightful universe. After defeating Mr.Rex get the 'Lizard Key' and unlock your door of salvation in the 'Key Room'. Look for clues, pick up items, and don't be afraid to explore, but most of all stay alive!
                                
                How to Play:\s
                                
                To perform an action, type your command in the text field box and hit enter.

                Examples of possible commands are:\s

                'go east' - to travel east, or substitute 'east' with west, south, or north to travel in a specified direction.

                'examine room' - to see items and doors in current room.
                                
                'grab healing brownies' - to get and store healing brownies in your inventory for later use.
                                
                'eat healing brownies' - to eat healing brownies and heal yourself.

                'examine locked chest' - to examine the locked chest (locked chest can be substituted for any item in your current room).

                'inventory' - to see items in your inventory.

                'grab knife' - to grab a knife and store a knife in your inventory (substitute knife for item you wish to pick up).

                'drop knife' - to drop a knife (substitute knife for item you wish to drop from your inventory).
                                
                'get skeleton key' - to get and store skeleton key in your inventory to unlock a suitable door.

                'use skeleton key on east' - to unlock a locked door to the east of the Egyptian Room.
                                
                'use chest key on locked chest' - to unlock locked chest.
                                
                'teleport <room>' - does what it says if you have what it takes to pull it off?
                                
                'use magic cape on west' - to go over the river to the west of Treasure Room and 'use magic cape on east' to go back to Treasure Room.\s""";

    }

    static String howToPlay() {
        return """
                Welcome to a text-based game, The Lizard Key! 
                Your path is a dangerous one, there are two enemies lurking in the shadows that you must have the courage to defeat. Only then can you face your biggest opponent Mr.Rex the sole reason you are trapped in this frightful universe. After defeating Mr.Rex get the 'Lizard Key' and unlock your door of salvation in the 'Key Room'. Look for clues, pick up items, and don't be afraid to explore, but most of all stay alive!
                                
                How to Play:\s
                                
                To perform an action, type your command in the text field box and hit enter.

                Examples of possible commands are:\s

                'go east' - to travel east, or substitute 'east' with west, south, or north to travel in a specified direction.

                'examine room' - to see items and doors in current room.
                                
                'grab healing brownies' - to get and store healing brownies in your inventory for later use.
                                
                'eat healing brownies' - to eat healing brownies and heal yourself.

                'examine locked chest' - to examine the locked chest (locked chest can be substituted for any item in your current room).

                'inventory' - to see items in your inventory.

                'grab knife' - to grab a knife and store a knife in your inventory (substitute knife for item you wish to pick up).

                'drop knife' - to drop a knife (substitute knife for item you wish to drop from your inventory).
                                
                'get skeleton key' - to get and store skeleton key in your inventory to unlock a suitable door.

                'use skeleton key on east' - to unlock a locked door to the east of the Egyptian Room.
                                
                'use chest key on locked chest' - to unlock locked chest.
                                
                'teleport <room>' - does what it says if you have what it takes to pull it off?
                                
                'use magic cape on west' - to go over the river to the west of Treasure Room and 'use magic cape on east' to go back to Treasure Room.\s""";

    }

    String introduction() {
        return """
                Like a dream, your thoughts carry you to an unknown universe. Four bare walls keep you as a prisoner to an overly bright room with no windows. Shelves with millions of keys in varying sizes, shapes, and colors line the floors like passageways to doors around you.
                                
                What lies behind each door is unknown to you, but your options are limited, for this is no dream, and you are not imagining what you are seeing. The keys you touch are as real as the keys to your home. The floor you stand on is as hard as the surface beneath your bed.

                Like many others before you, you have become a pet to a man named Copernicus Rex Verwirrtheit Theodore, locksmith, creator of worlds, and master of confusion. With no two worlds being the same, Mister Theodore has created each maze uniquely odd and fantastically tumultuous for every mouse searching for its cheese.
                                
                It is up to you to find the way out of this prison and away from Mister Theodore's grasp, for if you do not, you will forever be in an endless loop of rooms that lead to other rooms,the final and single exit a mystery never to be given away freely.""";

    }
}


