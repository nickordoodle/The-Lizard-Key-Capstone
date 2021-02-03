package org.lizard;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

public class Board {
    private Room currentRoom;
    private final Directions directions = new Directions();
    private Map<String, Room> allRooms = new HashMap<>();
    private Map<String, Map<String, String>> allExits = new HashMap<>();
    private Map<String, Item> allItems = new HashMap<>();
    private Map<Room, List<Map<String, String>>> roomLocks = new HashMap<>();


    public Board() {
        createMap();
    }

    public void createMap(){
        // Call XMLParser to gain access to a nodeList of all the rooms in the XML file
        NodeList nodeList = XMLParser("xml/Rooms.xml", "room");
        // Create a for loop to go the length of the nodeList
        for (int itr = 0; itr < nodeList.getLength(); itr++) {
            Node node = nodeList.item(itr);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                // Create a way to hold the exits for each room before placing them in allExits
                Map<String,String> exits = new HashMap<>();
                Element eElement = (Element) node;

                // Gain and save all relevant information needed to create a Room class object
                String roomId = eElement.getElementsByTagName("id").item(0).getTextContent();

                // Get roomName and create a new Room class object
                String roomName = eElement.getElementsByTagName("roomName").item(0).getTextContent();
                Room room = new Room(roomName);

                String roomDescription = eElement.getElementsByTagName("roomDescription").item(0).getTextContent();
                // Add description to Room class object
                room.setRoomDescription(roomDescription);

                List<Map<String, String>> locks  = new ArrayList<>();


                // Gain all exits for this particular room and save them in exits
                for (int itr2 = 0; itr2 < eElement.getElementsByTagName("exit").getLength(); itr2++) {
                    Map<String, String> holder = new HashMap<>();
                    // Create an object with all of the directions and the corresponding rooms for this particular Room class object
                    exits.put(eElement.getElementsByTagName("path").item(itr2).getTextContent(), eElement.getElementsByTagName("pathName").item(itr2).getTextContent());
                    if(eElement.getElementsByTagName("lockKey").item(itr2) != null) {
                        String lockKey = eElement.getElementsByTagName("lockKey").item(itr2).getTextContent();
                        String lockMessage = eElement.getElementsByTagName("lockMessage").item(itr2).getTextContent();
                        String commandInt = eElement.getElementsByTagName("commandInt").item(itr2).getTextContent();
                        String commandDirection = eElement.getElementsByTagName("path").item(itr2).getTextContent();



                        holder.put("commandDirection", commandDirection);
                        holder.put("lockKey", lockKey);
                        holder.put("lockMessage", lockMessage);
                        holder.put("commandInt", commandInt);
                        locks.add(holder);

                    }
                }
                roomLocks.put(room, locks);


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

        // Set starting room.
        this.currentRoom = allRooms.get("living");
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public String changeCurrentRoom(String direction) {
        Map<String, Room> exits = currentRoom.getExits();
        Lock lock = currentRoom.getLock(direction);

        if(lock != null) {
            return "Seems to be locked.";
        }
        if(exits.containsKey(direction)){
            currentRoom = exits.get(direction);
            return ("current room is " + currentRoom.getName() + "\n" + currentRoom.getRoomDescription());

        } else {
            return ("That is not an exit.");
        }

    }

    private NodeList XMLParser (String pathName, String tagName) {
        NodeList nodeList;
        try {
            // Creating a constructor of file class and parsing an XML file
            File file = new File(pathName);
            // An instance of factory that gives a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // An instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            nodeList = doc.getElementsByTagName(tagName);
            return nodeList;
        }
        catch (Exception e) {
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
                // entry2.getValue() will return the String room ("library", "kitchen", etc)
                Map.Entry<String, String> entry2 = iterator2.next();
                // Get the Room class object from allRooms to create a new exit with the proper room exit
                allRooms.get(entry.getKey()).createRoom(entry2.getKey(), allRooms.get(entry2.getValue()));
            }
        }
    }


    private void addItemsToRooms () {

        NodeList nodeList = XMLParser("xml/Items.xml", "item");
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

                // Create new instance of the item
                if (lockKey.equals("none")) {
                    Item roomItem = new Item(itemName);
                    allItems.put(itemName, roomItem);
                    // Add newly created item to its respective room
                    allRooms.get(roomName).addItemToRoom(roomItem);
                } else {
                    System.out.println("Item Name: " + itemName + " Item: " + allItems.get(lockKey) + " Lock Message: " + lockMessage + " Command Int: " + commandInt + " Command Direction: " + commandDirection);
                    Item roomItem = new Item(itemName, new Lock(allItems.get(lockKey), lockMessage, new Event(Integer.parseInt(commandInt), directions.getDirection(commandDirection))));

                    // Add newly created item to its respective room
                    allRooms.get(roomName).addItemToRoom(roomItem);
                }
            }
        }
    }
    public void addLocksToRooms() {
        for(Map.Entry<Room, List<Map<String, String>>> locksEntrySet: roomLocks.entrySet()) {
            Room room = locksEntrySet.getKey();
            for(int i = 0; i < locksEntrySet.getValue().size(); i++) {
                Map<String, String> lockMap = locksEntrySet.getValue().get(i);
                room.addLock(lockMap.get("commandDirection") ,new Lock(allItems.get(lockMap.get("lockKey")), lockMap.get("lockMessage"), new Event(Integer.parseInt(lockMap.get("commandInt")), directions.getDirection(lockMap.get("commandDirection")))));
            }
        }
    }
}


