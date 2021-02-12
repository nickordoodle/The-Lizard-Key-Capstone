package org.lizard;

import org.lizard.util.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MapView extends JPanel {
    int numOfCols = 8;
    int numOfRows = 6;
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
                String current;
                if (rooms[i][j] == null) {
                    g.setColor(Color.black);
                    current = "";
                } else {

                    g.setColor(Color.BLUE);
                    current = rooms[i][j];

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
                        g.setColor(Color.ORANGE);
                        g.fillOval(i * size + size / 2 + 350, j * size + 440, 20, 20);
                    }

                }

                g.setColor(Color.GREEN);
                Font font = new Font("Arial", Font.BOLD, 10);
                g.setFont(font);
                FontMetrics fm = g.getFontMetrics();
                int x = ((size - fm.stringWidth(current)) / 2);
                int y = ((size - fm.getHeight()) / 2) + fm.getAscent();

                g.drawString(current, i * size + x + 350, j * size + (size / 2) + 430);

            }
        }

    }

}
