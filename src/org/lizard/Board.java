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
    private Map<String, Room> allRooms = new HashMap<>();
    private Map<String, Map<String, String>> allExits = new HashMap<>();

    public Board(){
        createMap();
    }

    public void createMap(){
        try
        {
//creating a constructor of file class and parsing an XML file
            File file = new File("xml/Rooms.xml");
//an instance of factory that gives a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//an instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
//            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("room");
// create a for loop to go the length of the nodeList
            for (int itr = 0; itr < nodeList.getLength(); itr++)

            {
                Node node = nodeList.item(itr);
//                System.out.println("\nNode Name :" + node.getNodeName());
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Map<String,String> exits = new HashMap<String, String>(); // for testing purposes
                    Element eElement = (Element) node;

                    // Gain and save all relevant information needed to create a Room class object
                    String roomId = eElement.getElementsByTagName("id").item(0).getTextContent();

                    // Get roomName and create a new Room class object
                    String roomName = eElement.getElementsByTagName("roomName").item(0).getTextContent();
                    Room room = new Room(roomName);

                    String roomDescription = eElement.getElementsByTagName("roomDescription").item(0).getTextContent();

                    // Gain all exits for this particular room and save them in exits
                    for (int itr2 = 0; itr2 < eElement.getElementsByTagName("exit").getLength(); itr2++) {
                        // Create an object with all of the directions and the corresponding rooms for this particular Room class object
                        exits.put(eElement.getElementsByTagName("path").item(itr2).getTextContent(), eElement.getElementsByTagName("pathName").item(itr2).getTextContent());
                    }

                    // Gain all items listed in room and add to Room class object
                    for (int itr3 = 0; itr3 < eElement.getElementsByTagName("items").getLength(); itr3++) {
                        String roomItem = eElement.getElementsByTagName("item").item(itr3).getTextContent();
                        Item newItem = new Item(roomItem);
                        room.addItemToRoom(newItem);
                        System.out.println(roomItem);
                    }

                    // Add the new exits Map object with the corresponding room name to allExits in order to build the Room class object exits later
                    allExits.put(roomName, exits);

                    // Add description to Room class object
                    room.setRoomDescription(roomDescription);

                    // Add the new Room to allRooms, so after all rooms and exits have been put together, exits can be assigned to the correct rooms
                    allRooms.put(roomName, room);
                }
            }

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
        catch (Exception e)
        {
            e.printStackTrace();
        }


        Item match = new Item("match");
        Item candle = new Item("candle", new Lock(match, "The candle has lit. You run west!", new Command(2, new Direction("west"))));
//        closet.addItemToRoom(match);
//        living.addItemToRoom(candle);
//        treasureRoom.addItemToRoom(new Item("key"));


//        this.currentRoom = living;
        this.currentRoom = allRooms.get("living");

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
