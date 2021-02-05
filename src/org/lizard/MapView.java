package org.lizard;

import javax.swing.*;
import java.awt.*;

public class MapView extends JPanel {
    int rows = 8;
    int cols = 8;
    int size = 100;
    String currentRoom = "Library";
    String[][] rooms = new String[rows][cols];

    MapView() {
//        rooms[7][0] = "Floating Room";
//        rooms[6][0] = "Land of the Dead";
//        rooms[5][0] = "Egyptian Room";
//        rooms[4][0] = "Art Room";
//        rooms[7][1] = "Engraving Room";
//        rooms[6][1] = "Loud Room";
        rooms[5][1] = "Swinging Stairs";
        rooms[6][2] = "Library";
        rooms[5][2] = "Key Room";
//        rooms[4][2] = "Psych Ward";
//        rooms[7][3] = "Coal Mine";
//        rooms[6][3] = "Creaky Path";
//        rooms[5][3] = "Kitchen";
//        rooms[4][3] = "Closet";
//        rooms[3][3] = "Riddle Room";
//        rooms[2][3] = "Whispering Room";
//        rooms[1][3] = "Treasure Room";
//        rooms[0][3] = "River";
//        rooms[1][4] = "Volcano";
//        rooms[2][4] = "Secret Passage";
//        rooms[3][4] = "Secret Passage";
//        rooms[4][4] = "Secret Passage";
//        rooms[5][4] = "Secret Passage";


    }
    public void paint(Graphics g){


        g.fillRect(size, size, size*rows, size*cols);
        for(int i = 0; i < 8; i+=1){
            for(int j = 0; j < 8; j+=1){
                String current;
                if(rooms[i][j] == null) {
                    g.setColor(Color.black);
                    current = "";
                } else {

                    g.setColor(Color.BLUE);
                    current = rooms[i][j];

                }
                g.fillRect(i * size,j * size, size, size);
                g.setColor(Color.BLACK);
                g.drawRect(i * size,j * size, size, size);

                if(rooms[i][j] != null && rooms[i][j].equals(currentRoom)) {
                    g.setColor(Color.ORANGE);
                    g.fillOval(i * size+ size/2, j * size + 10 , 20, 20);
                }

                g.setColor(Color.GREEN);
                Font font = new Font("Arial", Font.BOLD, 12);
                g.setFont(font);
                FontMetrics fm = g.getFontMetrics();
                int x = ((size - fm.stringWidth(current)) / 2);
                int y = ((size - fm.getHeight()) / 2) + fm.getAscent();

                g.drawString(current, i * size + x, j * size + (size/2));

            }
        }

    }
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setSize(1000,800);
        frame.getContentPane().add(new MapView());
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.BLACK);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
