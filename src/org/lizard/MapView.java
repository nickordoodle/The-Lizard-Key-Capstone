package org.lizard;

import org.lizard.util.Screen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(column * size + 350, row * size + 430, size, size);
                    roomName = rooms[column][row];

                }

                String joined = null;
                if (rooms[column][row] != null) {
                    String[] nameSplit = rooms[column][row].split(" ");

                    for (int v = 0; v < nameSplit.length; v++) {
                        nameSplit[v] = nameSplit[v].toLowerCase();
//                        if (v == 0) {
//                            nameSplit[v] = String.valueOf(nameSplit[v].charAt(0)).toLowerCase() + nameSplit[v].substring(1);
//                        } else {
//                            nameSplit[v] = String.valueOf(nameSplit[v].charAt(0)).toUpperCase() + nameSplit[v].substring(1);
//                        }
                    }
                    joined = String.join(" ", Arrays.asList(nameSplit));
                }

                // Draws the current room of the player
                if (rooms[column][row] != null && joined != null && joined.equalsIgnoreCase(currentRoom)) {
                    if (!((column == 3 || column == 4 || column == 2) && row == 4)) {

                        try {
                            g.setColor(Color.WHITE);
                            g.fillRect(column * size + 350, row * size + 430, size, size);

                            InputStream resourceInputStream = getClass().getClassLoader().getResourceAsStream("person-v2.png");

                            BufferedImage image = ImageIO.read(resourceInputStream);
                            g.drawImage(image, column * size + 370, row * size + 440, size, size - 25, null);

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
                    InputStream resourceInputStream = getClass().getClassLoader().getResourceAsStream("four-walls-aerial-view-v6.png");

                    BufferedImage image = ImageIO.read(resourceInputStream);
                    g.drawImage(image, column * size + 350, row * size + 430, size, size, null);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setBoxText(Graphics g, int numRows, int numCols, String current, Color color) {
        g.setColor(color);
        Font font = new Font("Arial", Font.BOLD, 12);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int offsetX = ((size - fm.stringWidth(current)) / 2);
        int offsetY = ((size - fm.getHeight()) / 2) + fm.getAscent();

        try {
            String[] parsedWords = current.split(" ");
            // The offset to adjust for every word
            int heightOffset = 0;
            for (int i = 0; i < parsedWords.length; i++) {
                g.drawString(
                        parsedWords[i],
                        numRows * size + 375,
                        numCols * size + (size / 2) + 420 + heightOffset);
                heightOffset += 15;
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

    }

}
