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


        g.fillRect(size, size, size * numOfCols, size * numOfRows);
        for (int i = 0; i < numOfCols; i += 1) {

            for (int j = 0; j < numOfRows; j += 1) {
                String roomName;
                if (rooms[i][j] == null) {
                    g.setColor(Color.black);
                    roomName = "";
                } else {

                    g.setColor(Color.BLUE);
                    roomName = rooms[i][j];

                }
                g.fillRect(i * size + 350, j * size + 430, size, size);
                g.setColor(Color.orange);
                g.drawRect(i * size + 350, j * size + 430, size, size);
                String joined = null;
                if (rooms[i][j] != null) {
                    String[] nameSplit = rooms[i][j].split(" ");

                    for (int v = 0; v < nameSplit.length; v++) {
                        if (v == 0) {
                            nameSplit[v] = String.valueOf(nameSplit[v].charAt(0)).toLowerCase() + nameSplit[v].substring(1);
                        } else {
                            nameSplit[v] = String.valueOf(nameSplit[v].charAt(0)).toUpperCase() + nameSplit[v].substring(1);
                        }
                    }
                    joined = String.join("", Arrays.asList(nameSplit));
                }


                if (rooms[i][j] != null && joined != null && joined.equals(currentRoom)) {
                    if (!((i == 3 || i == 4 || i == 2) && j == 4)) {
//                        g.setColor(Color.GREEN);
//                        g.drawRect(i * size + 350, j * size + 430, size, size);
                        try {
                            BufferedImage image = ImageIO.read(new File("./person.jpeg"));
                            g.drawImage(image, i * size + 350, j * size + 430, size, size, null);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    // Set the text to black for current room for better contrast
                    setBoxText(g, i, j, roomName, Color.BLACK);

                } else {
                    // Set the text to white for visited rooms besides your current room for better contrast
                    setBoxText(g, i, j, roomName, Color.WHITE);

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
