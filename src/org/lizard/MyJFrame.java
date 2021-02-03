package org.lizard;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyJFrame extends JPanel {

    Board board = new Board();
    Player player = new Player("Edgar");
    Actions actions = new Actions(board, player, this);
    GameDictionary gameDictionary = GameDictionary.getGameDictionary();
    TextParser parser = new TextParser(gameDictionary);

    String result;
    JLabel inputFromUser = new JLabel();
    JTextField textField = new JTextField();

    MyJFrame() {
        System.out.println(gameDictionary.getKnownWords());
        new Funsies("jump", "Good for you");
        new Funsies("hello", "hi");
        new Funsies("help", "examine something");
        new Funsies("where", "idk figure it out");
        new Funsies("what", "idk figure it out");

        JFrame frame = new JFrame();
        frame.setTitle("Lizard Game");
        frame.setSize(600,400);

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.PINK);

        JButton enterGame = new JButton("Enter Game");

        JLabel welcome = new JLabel("Welcome to the Lizard Game!");




        panel2.add(enterGame);
        panel2.add(welcome);
//        Border buttonBorder = BorderFactory.createLineBorder(Color.black, 3);


        frame.add(panel2);

        frame.setVisible(true);

        enterGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.remove(panel2);
                gameScreen();

            }

            private void gameScreen() {
                frame.getContentPane().setBackground(new Color(200,200,200));
                frame.setSize(600,400);


//                ImageIcon img = new ImageIcon("keys.jpg");

                JTextArea label1 = new JTextArea();
                label1.setText(Story.introduction());
                label1.setLineWrap(true);
                label1.setBounds(50,50,100,100);
                label1.setWrapStyleWord(true);
                label1.setBorder(BorderFactory.createBevelBorder(1));
                label1.setForeground(new Color(53, 180, 19));
                label1.setFont(new Font("Comic Sans",Font.BOLD, 12));
                label1.setEditable(false);

                JScrollPane scrollPane = new JScrollPane(label1);
                scrollPane.setPreferredSize(new Dimension(500,300));
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


                inputFromUser.setText("What do you want to do?");


                textField.setPreferredSize(new Dimension(500,100));
                textField.setFont(new Font("Consolas", Font.CENTER_BASELINE, 12));
                textField.setForeground(Color.white);
                textField.setBackground(new Color(49, 45, 45));
                textField.setCaretColor(Color.white);
                JButton submit = new JButton("Submit");

                JPanel top = new JPanel();
                top.setBounds(0,0,1200,700);

                JPanel center1 = new JPanel();


                JPanel bottom = new JPanel();

                top.add(scrollPane);
                center1.add(inputFromUser);
                center1.add(submit);
                center1.add(textField);

                frame.add(top,BorderLayout.NORTH);

                frame.add(center1);

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {

                        result = textField.getText();
                        Command command = parser.parse(result);

                        label1.setText(actions.execute(command));
                        textField.setText("");
                    }
                });
            }

        });


    }
    public String decision() {
        inputFromUser.setText("What do you want to do?");
        return textField.getText();
    }

}
