package org.lizard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyJFrame extends JFrame implements ActionListener {

    Board board = new Board();
    Player player = new Player("Edgar");
    Combat combat = new Combat();
    Actions actions = new Actions(board, player, this, combat);
    GameDictionary gameDictionary = GameDictionary.getGameDictionary();
    TextParser parser = new TextParser(gameDictionary);

    JTextArea rpsGame;
    JPanel promptPanel;
    JScrollPane scrollPane;
    MapView mapView;

    String result;
    JLabel inputFromUser = new JLabel();
    JTextField textField = new JTextField();
    JButton enterGame;
    JFrame frame;
    JPanel panel0;
    JTextArea textDisplay;
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    JPanel panel4;
    JPanel panel5;
    JTextField numInput;
    String numFromUser;
    boolean calledOnce=false;

    MyJFrame() {
        new Funsies("jump", "Good for you");
        new Funsies("hello", "hi");
        new Funsies("help", "examine something");
        new Funsies("where", "idk figure it out");
        new Funsies("what", "idk figure it out");

        frame = new JFrame();
        frame.setTitle("Lizard Game");
        frame.setSize(600,400);

        panel0 = new JPanel();
        panel0.setBackground(new Color(7, 87, 91));

        enterGame = new JButton("Enter Game");
        enterGame.setBounds(10,150,100,300);


        JLabel welcome = new JLabel("Welcome to the Lizard Game!");
        welcome.setFont(new Font("IronWood", Font.BOLD, 30));
        welcome.setForeground(new Color(196, 223, 230));

        panel0.add(welcome,BorderLayout.NORTH);
        panel0.add(enterGame);

        frame.add(panel0);

        frame.setVisible(true);
        enterGame.addActionListener(this);
            }

    public String decision() {
        inputFromUser.setText("What do you want to do? ");
        return inputFromUser.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==enterGame){
            frame.remove(panel0);
            gameScreen(Story.introduction());
        }
        if(e.getSource()==textField){

            result = textField.getText();

            Command command = parser.parse(result);


            textDisplay.setFont(new Font("Consolas", Font.CENTER_BASELINE, 15));
            textDisplay.setForeground(Color.black);
            textDisplay.setText(actions.execute(command));

            frame.remove(panel5);

            panel5 = new MapView(board.rooms, board.getCurrentRoom().getName());
            panel5.setBounds(0,750,1500,550);

            frame.add(panel5);
            frame.setBackground(Color.black);
            panel5.setVisible(true);
            frame.setVisible(true);

            if(board.getCurrentRoom().getEnemy() != null)){
                frame.remove(panel1);
                frame.remove(panel2);
                frame.remove(panel3);
                frame.remove(panel4);
                frame.remove(panel5);
                displayCombat();

            }
            textField.setText("");
        }

        if(e.getSource()==numInput){

            boolean combatEnded = false;

                rpsGame.setText(combat.playerTakesTurn(Integer.parseInt(numInput.getText())));
                if(combat.checkGameEndingStatus()=="Enemy won") {
                    frame.remove(promptPanel);
                    frame.remove(scrollPane);
                    frame.setVisible(true);
                    gameOverScreen();

                }
                else if(combat.checkGameEndingStatus()=="Player won"){

                    if(combat.bossTime) {
                        combat.bossTime = false;
                        board.totalEnemies = -1;
                        frame.remove(promptPanel);
                        frame.remove(scrollPane);
                        gameScreen(actions.execute(new Event(99, board.allItems.get("Boss-Key"))));
                        frame.setVisible(true);

                    } else {
                        frame.remove(promptPanel);
                        frame.remove(scrollPane);
                        gameScreen("Player won");
                        frame.setVisible(true);
                    }



                }

                numInput.setText("");

        }

    }


    private void gameScreen(String initialPrint) {
        frame.getContentPane().setBackground(new Color(200,200,200));
        frame.setSize(600,400);

        textDisplay = new JTextArea();
        textDisplay.setText(initialPrint);
        textDisplay.setLineWrap(true);
        textDisplay.setWrapStyleWord(true);
        textDisplay.setBorder(BorderFactory.createBevelBorder(1));
        textDisplay.setForeground(new Color(0, 60, 70));
        textDisplay.setFont(new Font("Comic Sans",Font.BOLD, 15));
        textDisplay.setEditable(false);
        textDisplay.setBackground(new Color(196, 223, 230));

        JScrollPane scrollPane = new JScrollPane(textDisplay);
        scrollPane.setPreferredSize(new Dimension(700,250));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


        inputFromUser.setText("What do you want to do?");
        inputFromUser.setBounds(0,300,2,150);
        inputFromUser.setFont(new Font("Consolas", Font.CENTER_BASELINE, 20));

        textField.setPreferredSize(new Dimension(500,100));
        textField.setFont(new Font("Consolas", Font.CENTER_BASELINE, 15));
        textField.setForeground(Color.black);
        textField.setBackground(new Color(196, 223, 230));
        textField.setCaretColor(Color.BLACK);


        panel1 = new JPanel();
        panel1.setBackground(new Color(102, 165, 173));
        panel1.setBounds(0,0,1500,300);

        panel2 = new JPanel();
        panel2.setBackground(new Color(102, 165, 173));
        panel2.setBounds(0,300,500,125);


        panel3 = new JPanel();
        panel3.setBackground(new Color(102, 165, 173));
        panel3.setBounds(500,300,1000,125);

        panel4 = new JPanel();
        panel4.setBackground(Color.black);
        panel4.setBounds(0,450,1500,550);

        panel1.add(scrollPane);
        panel2.add(inputFromUser);
        panel3.add(textField);

        panel5 = new MapView(board.rooms, board.getCurrentRoom().getName());
        panel5.setBounds(0,750,1500,550);
        panel5.setBackground(Color.black);


        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel5);
        frame.setBackground(Color.black);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500,1000);
        if (!calledOnce) {
            textField.addActionListener(this);
            calledOnce = true;

        }

        frame.setVisible(true);
    }



    private void displayCombat(){

        rpsGame = new JTextArea();
        rpsGame.setText("You have encountered MONSTER GOBLIN! " +
                "The only way to win MONSTER GOBLIN is to win 5 games of ROCK-PAPER-SCISSOR." +
                "\nPlease choose from the following numbers:" +
                "\n1: ROCK" +
                "\n2: PAPER" +
                "\n3: SCISSOR");
        rpsGame.setPreferredSize(new Dimension(500,100));
        rpsGame.setBounds(50,50,100,100);
        rpsGame.setFont(new Font("Sans Script", Font.BOLD, 15 ));
        rpsGame.setLineWrap(true);
        rpsGame.setWrapStyleWord(true);
        rpsGame.setBorder(BorderFactory.createBevelBorder(1));
        rpsGame.setForeground(new Color(246, 41, 0));
        rpsGame.setEditable(false);


        numInput = new JTextField();
        numInput.setPreferredSize(new Dimension(500,100));
        numInput.setBackground(new Color(241, 243, 206));

        scrollPane = new JScrollPane(rpsGame);
        scrollPane.setPreferredSize(new Dimension(500,300));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel panel = new JPanel();

        panel.add(scrollPane);

        JLabel promptLabel = new JLabel();
        promptLabel.setText("What number do you choose?");
        promptPanel = new JPanel();
        promptPanel.add(promptLabel, BorderLayout.CENTER);
        promptPanel.add(numInput);

        frame.add(scrollPane, BorderLayout.NORTH);
        frame.add(promptPanel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        combat.startCombat(player, board.getCurrentRoom(), board);
        numInput.addActionListener(this);

    }

    public void gameOverScreen(){
        frame.remove(panel1);
        frame.remove(panel2);

        ImageIcon lost = new ImageIcon(getClass().getResource("over.png"));

        JLabel imgLabel = new JLabel(lost);
        add(imgLabel);

        frame.add(imgLabel);
        frame.setVisible(true);

    }

}
