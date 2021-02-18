package org.lizard;

import org.lizard.util.Screen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MapView extends JPanel {
    int numOfCols = 8;
    int numOfRows = 5;
    private final int size = Screen.MAP_SQUARE_SIZE;
    String currentRoom = null;
    String[][] rooms = new String[numOfCols][numOfRows];

    MapView(String[][] rooms, String currentRoom) {
        this.rooms = rooms;
        this.currentRoom = currentRoom;
    }

    // Paints the Map using the Graphics object
    // that is passed in.  You will see 'fillRect', 'drawRect',
    // and other methods that do the bulk work in this method
    public void paint(Graphics g) {


        for (int column = 0; column < numOfCols; column++) {

            for (int row = 0; row < numOfRows; row++) {
                String roomName;
                if (rooms[column][row] == null) {
                    // Set undiscovered room properties and view
                    g.setColor(Color.black);
                    g.fillRect(column * size + 350, row * size + 430, size, size);
                    roomName = "";
                } else {
                    // Set discovered/visited rooms that player is not currently in
                    g.setColor(Color.WHITE);
                    g.fillRect(column * size + 350, row * size + 430, size, size);
//                    try {
//                        BufferedImage image = ImageIO.read(new File("./four-colored-door-room.jpeg"));
//                        g.drawImage(image, column * size + 350, row * size + 430, size, size, null);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                    roomName = rooms[column][row];

                }

                String joined = null;
                if (rooms[column][row] != null) {
                    String[] nameSplit = rooms[column][row].split(" ");

                    for (int v = 0; v < nameSplit.length; v++) {
                        if (v == 0) {
                            nameSplit[v] = String.valueOf(nameSplit[v].charAt(0)).toLowerCase() + nameSplit[v].substring(1);
                        } else {
                            nameSplit[v] = String.valueOf(nameSplit[v].charAt(0)).toUpperCase() + nameSplit[v].substring(1);
                        }
                    }
                    joined = String.join("", Arrays.asList(nameSplit));
                }


                if (rooms[column][row] != null && joined != null && joined.equals(currentRoom)) {
                    if (!((column == 3 || column == 4 || column == 2) && row == 4)) {

                        try {
                            BufferedImage image = ImageIO.read(new File("./person-v2.png"));
                            g.drawImage(image, column * size + 380, row * size + 450, size-20, size-50, null);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    // Set the text to black for current room for better contrast
                    setBoxText(g, column, row, roomName, Color.BLACK);

                } else {
                    // Set the text to white for visited rooms besides your current room for better contrast
                    setBoxText(g, column, row, roomName, Color.BLACK);
                }


                try {
                    BufferedImage image = ImageIO.read(new File("./four-walls-aerial-view-v5.png"));
                    g.drawImage(image, column * size + 350, row * size + 430, size, size, null);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setBoxText(Graphics g, int i, int j, String current, Color color) {
        g.setColor(color);
        Font font = new Font("Arial", Font.BOLD, 10);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int x = ((size - fm.stringWidth(current)) / 2);
        int y = ((size - fm.getHeight()) / 2) + fm.getAscent();

        g.drawString(current, i * size + x + 350, j * size + (size / 2) + 430);
    }

}
