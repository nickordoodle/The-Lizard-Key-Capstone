package org.lizard;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyJFrame extends JFrame implements ActionListener {

    Board board = new Board();
    Player player = new Player("Edgar");
    Actions actions = new Actions(board, player, this);
    GameDictionary gameDictionary = GameDictionary.getGameDictionary();
    TextParser parser = new TextParser(gameDictionary);
    Combat combat = new Combat();
    JTextArea rpsGame;
    JPanel promptPanel;
    JScrollPane scrollPane;

    String result;
    JLabel inputFromUser = new JLabel();
    JTextField textField = new JTextField();
    JButton enterGame;
    JFrame frame;
    JPanel panel2;
    JTextArea label1;
    JPanel top;
    JPanel center1;
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

        panel2 = new JPanel();
        panel2.setBackground(new Color(7, 87, 91));

        enterGame = new JButton("Enter Game");
        enterGame.setBounds(10,150,100,300);


        JLabel welcome = new JLabel("Welcome to the Lizard Game!");
        welcome.setFont(new Font("IronWood", Font.BOLD, 30));
        welcome.setForeground(new Color(196, 223, 230));



        panel2.add(welcome,BorderLayout.NORTH);
        panel2.add(enterGame);
//        Border buttonBorder = BorderFactory.createLineBorder(Color.black, 3);

        frame.add(panel2);

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
            frame.remove(panel2);
            gameScreen(Story.introduction());
        }
        if(e.getSource()==textField){

            result = textField.getText();
            Command command = parser.parse(result);
            label1.setFont(new Font("Consolas", Font.CENTER_BASELINE, 15));
            label1.setForeground(Color.black);
            label1.setText(actions.execute(command));

            if(label1.getText().contains("swingingStairs")){
                frame.remove(top);
                frame.remove(center1);
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
                    frame.remove(promptPanel);
                    frame.remove(scrollPane);
                    gameScreen("Player won");
                    frame.setVisible(true);

                }
                numInput.setText("");

        }

    }


    private void gameScreen(String initialPrint) {
        frame.getContentPane().setBackground(new Color(200,200,200));
        frame.setSize(600,400);

        label1 = new JTextArea();
        label1.setText(initialPrint);
        label1.setLineWrap(true);
        label1.setBounds(50,50,100,100);
        label1.setWrapStyleWord(true);
        label1.setBorder(BorderFactory.createBevelBorder(1));
        label1.setForeground(new Color(0, 60, 70));
        label1.setFont(new Font("Comic Sans",Font.BOLD, 12));
        label1.setEditable(false);
        label1.setBackground(new Color(196, 223, 230));


        JScrollPane scrollPane = new JScrollPane(label1);
        scrollPane.setPreferredSize(new Dimension(500,300));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        inputFromUser.setText("What do you want to do?");
        inputFromUser.setFont(new Font("Consolas", Font.CENTER_BASELINE, 20));

        textField.setPreferredSize(new Dimension(500,100));
        textField.setFont(new Font("Consolas", Font.CENTER_BASELINE, 15));
        textField.setForeground(Color.black);
        textField.setBackground(new Color(196, 223, 230));
        textField.setCaretColor(Color.BLACK);

        top = new JPanel();
        top.setBounds(0,0,1200,700);
        top.setBackground(new Color(102, 165, 173));

        center1 = new JPanel();
        center1.setBackground(new Color(102, 165, 173));

        JPanel bottom = new JPanel();

        top.add(scrollPane);
        center1.add(inputFromUser);
        center1.add(textField);

        frame.add(top,BorderLayout.NORTH);
        frame.add(center1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,500);
        frame.setResizable(false);
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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

        combat.startCombat(player, new Enemy("Goblin"));
        numInput.addActionListener(this);

    }

    public void gameOverScreen(){
        frame.remove(top);
        frame.remove(center1);

        ImageIcon lost = new ImageIcon(getClass().getResource("over.png"));

        JLabel imgLabel = new JLabel(lost);
        add(imgLabel);

        frame.add(imgLabel);
        frame.setVisible(true);

    }

}
