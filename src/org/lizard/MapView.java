package org.lizard;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MapView extends JPanel {
    int rows = 8;
    int cols = 8;
    int size = 100;
    String currentRoom = null;
    String[][] rooms = new String[rows][cols];

   MapView(String[][] rooms, String currentRoom) {
       this.rooms = rooms;
       this.currentRoom = currentRoom;
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
                String joined = null;
                if(rooms[i][j] != null) {
                    String[] nameSplit = rooms[i][j].split(" ");

                    for(int v = 0; v < nameSplit.length; v++) {
                        if(v == 0) {
                            nameSplit[v] = String.valueOf(nameSplit[v].charAt(0)).toLowerCase() + nameSplit[v].substring(1);
                        } else {
                            nameSplit[v] = String.valueOf(nameSplit[v].charAt(0)).toUpperCase()+ nameSplit[v].substring(1);
                        }
                    }
                    joined = String.join("", Arrays.asList(nameSplit));
                }


                if(rooms[i][j] != null && joined != null && joined.equals(currentRoom)) {
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
//    public static void main(String[] args){
//        JFrame frame = new JFrame();
//        frame.setSize(1000,800);
//        frame.getContentPane().add(new MapView());
//        frame.setLocationRelativeTo(null);
//        frame.setBackground(Color.BLACK);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//    }
}
