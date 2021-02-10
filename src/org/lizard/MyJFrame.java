package org.lizard;

import org.lizard.util.Music;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MyJFrame extends JFrame implements ActionListener {

    Board board = new Board();
    Player player = new Player("Player");
    Combat combat = new Combat();
    Actions actions = new Actions(board, player, this, combat);
    GameDictionary gameDictionary = GameDictionary.getGameDictionary();
    TextParser parser = new TextParser(gameDictionary);
    String soundName = "princeofdarkness.wav";
    Clip clip = null;
    JTextArea rpsGame;
    JPanel promptPanel;
    JScrollPane scrollPane;
    boolean bossDead = false;
    String result;
    JLabel inputFromUser = new JLabel();
    JTextField textField = new JTextField();
    JButton enterGame;
    JFrame gameFrame;
    JPanel panel0;
    JTextArea textDisplay;
    JTextField desicionField = new JTextField();
    List<GameDictionary.Noun> nounList = null;
    boolean decisionListener = false;
    Command command = null;
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    JPanel panel4;
    JTextField numInput;

    boolean calledOnce = false;

    MyJFrame() {
        new Funsies("jump", "Good for you");
        new Funsies("hello", "hi");
        new Funsies("help", "examine something");
        new Funsies("where", "idk figure it out");
        new Funsies("what", "idk figure it out");

        gameFrame = new JFrame();

        try {
            playWelcomeScreen();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        gameFrame.setTitle("Lizard Key");
        gameFrame.setSize(1500, 1525);

        panel0 = new JPanel();
        panel0.setBackground(new Color(7, 87, 91));
        panel0.setBounds(0, 0, 1500, 100);
        panel1 = new JPanel();
        panel1.setBackground(new Color(7, 87, 91));
        panel1.setBounds(100, 100, 1500, 1325);
        panel1.setLayout(new BorderLayout());

        enterGame = new JButton("Enter Game");
        enterGame.setBounds(10, 150, 100, 300);


        JLabel welcome = new JLabel("Welcome to the Lizard Key!");
        welcome.setFont(new Font("IronWood", Font.BOLD, 30));
        welcome.setForeground(new Color(196, 223, 230));

        textDisplay = new JTextArea();
        DefaultCaret caret = (DefaultCaret) textDisplay.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        textDisplay.setCaretPosition(0);
        textDisplay.setText(Story.howToPlay());
        textDisplay.setLineWrap(true);
        textDisplay.setWrapStyleWord(true);
        textDisplay.setBorder(BorderFactory.createBevelBorder(1));
        textDisplay.setForeground(new Color(0, 60, 70));
        textDisplay.setFont(new Font("Comic Sans", Font.BOLD, 15));
        textDisplay.setEditable(false);
        textDisplay.setBackground(new Color(196, 223, 230));

        panel0.add(welcome, BorderLayout.CENTER);
        panel0.add(enterGame);
        panel1.add(textDisplay);

        gameFrame.add(panel0);
        gameFrame.add(panel1);

        gameFrame.setVisible(true);
        enterGame.addActionListener(this);
    }

    // Basic welcome / loading screen
    // Displays a picture via a URL
    // for the specified time in
    // Thread.sleep.  It is currently 5 secondss
    private void playWelcomeScreen() throws MalformedURLException {
        // Set up the game window to full screen with a scary image via a URL
        gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JLabel splashImage = new JLabel("", new ImageIcon(new URL("https://i.insider.com/562fbe249dd7cc1b008c528d?width=1100&format=jpeg&auto=webp")), SwingConstants.CENTER);
        gameFrame.getContentPane().add(splashImage);
        // Display for the duration of Thread.sleep time
        gameFrame.setVisible(true);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Clean up the frame, remove the image, change window size to normal
        gameFrame.remove(splashImage);
        gameFrame.dispose();
        gameFrame.setVisible(false);
        gameFrame.setExtendedState(JFrame.NORMAL);
    }
    public String decision(List<GameDictionary.Noun> nounList, Command command) {
        desicionField.setPreferredSize(new Dimension(500, 100));
        desicionField.setFont(new Font("Consolas", Font.CENTER_BASELINE, 15));
        desicionField.setForeground(Color.black);
        desicionField.setBackground(new Color(196, 223, 230));
        desicionField.setCaretColor(Color.BLACK);
        panel2.remove(textField);
        panel2.add(desicionField);
        desicionField.requestFocusInWindow();
        if (!decisionListener) {
            desicionField.addActionListener(this);
            decisionListener = true;
        }
        this.nounList = nounList;
        this.command = command;

        StringBuilder choices = new StringBuilder();
        for (int i = 0; i < nounList.size(); i++) {
            if (i == nounList.size() - 1) {
                choices.append(nounList.get(i).getName());
            } else {
                choices.append(nounList.get(i).getName()).append(" or ");
            }
        }
        return choices.toString();
    }

    // Handles the functionality of the button
    @Override
    public void actionPerformed(ActionEvent guiElement) {
        if (guiElement.getSource() == enterGame) {
            gameFrame.remove(panel0);
            gameFrame.remove(panel1);
            // Start the game music
            Music.playMusic("princeofdarkness.wav");
            gameScreen(Story.introduction());
        }
        if (guiElement.getSource() == desicionField) {
            result = desicionField.getText();

            desicionField.setText("");
            for (int i = 0; i < nounList.size(); i++) {
                if (nounList.get(i).getName().equals(result)) {
                    if (command.getNoun().length <= 1) {
                        command.setTargetNoun(new GameDictionary.Noun[]{nounList.get(i)});
                    } else {
                        command.setNoun(new GameDictionary.Noun[]{nounList.get(i)});
                    }

                    gameFrame.remove(panel1);
                    gameFrame.remove(panel2);
                    gameFrame.remove(panel3);
                    gameFrame.remove(panel4);
                    gameScreen(actions.execute(command));
                    gameFrame.setVisible(true);
                    return;
                }
            }
            gameFrame.remove(panel1);
            gameFrame.remove(panel2);
            gameFrame.remove(panel3);
            gameFrame.remove(panel4);
            gameScreen("you gotta be specific.");
            gameFrame.setVisible(true);
        }
        if (guiElement.getSource() == textField) {

            result = textField.getText();

            Command command = parser.parse(result);

            textDisplay.setFont(new Font("Consolas", Font.CENTER_BASELINE, 15));
            textDisplay.setForeground(Color.black);
            String response = actions.execute(command);

            textDisplay.setText(response);

            gameFrame.remove(panel4);

            panel4 = new MapView(board.rooms, board.getCurrentRoom().getName());
            panel4.setBounds(0, 750, 1500, 550);

            gameFrame.add(panel4);
            gameFrame.setBackground(Color.black);
            panel4.setVisible(true);
            gameFrame.setVisible(true);

            if (player.hasWinningKey && board.getCurrentRoom().getName().equals("keyRoom")) {
                gameFrame.remove(panel1);
                gameFrame.remove(panel2);
                gameFrame.remove(panel3);
                gameFrame.remove(panel4);
                winScreen();
            }

            if (board.getCurrentRoom().getEnemy() != null && !board.getCurrentRoom().getEnemy().enemyName.equals("Copernicus Rex Verwirrtheit Theodore")) {
                gameFrame.remove(panel1);
                gameFrame.remove(panel2);
                gameFrame.remove(panel3);
                gameFrame.remove(panel4);

                displayCombat();
            }

            if (response.equals("The sculpture, as you now know, was just Copernicus Rex Verwirrtheit Theodore. The same red liquid from the floor streams from his eyes.") && !bossDead) {
                gameFrame.remove(panel1);
                gameFrame.remove(panel2);
//                frame.remove(panel3);
                gameFrame.remove(panel3);
                gameFrame.remove(panel4);
                displayCombat();
                bossDead = true;
            }
            textField.setText("");
        }

        if (guiElement.getSource() == numInput) {

            rpsGame.setText(combat.playerTakesTurn(Integer.parseInt(numInput.getText())));
            if (combat.checkGameEndingStatus() == "Enemy won") {
                gameFrame.remove(promptPanel);
                gameFrame.remove(scrollPane);
                gameFrame.setVisible(true);
                gameOverScreen();
                if (clip != null) {
                    clip.stop();
                }
            } else if (combat.checkGameEndingStatus() == "You defeated the monster!") {

                if (combat.bossTime) {
                    combat.bossTime = false;
                    board.totalEnemies = -1;
                    gameFrame.remove(promptPanel);
                    gameFrame.remove(scrollPane);
                    gameScreen(actions.execute(new Event(99, board.allItems.get("sculpture"))));
                    gameFrame.setVisible(true);
                    if (clip != null) {
                        clip.stop();
                    }

                } else {
                    gameFrame.remove(promptPanel);
                    gameFrame.remove(scrollPane);
                    if (board.totalEnemies < 0) {
                        gameScreen("Copernicus Rex Verwirrtheit Theodore has fallen in his own world! His magic cape has fallen with him, and you are one step closer to freedom!");
                    } else {
                        gameScreen("You defeated the monster!");
                    }
                    gameFrame.setVisible(true);
                    if (clip != null) {
                        clip.stop();
                    }
                }

            }
            numInput.setText("");
        }
    }


    private void gameScreen(String initialPrint) {
        gameFrame.getContentPane().setBackground(new Color(200, 200, 200));
        gameFrame.setSize(1500, 1525);

        textDisplay = new JTextArea();
        DefaultCaret caret = (DefaultCaret) textDisplay.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        textDisplay.setCaretPosition(0);
        textDisplay.setText(initialPrint);
        textDisplay.setLineWrap(true);
        textDisplay.setWrapStyleWord(true);
        textDisplay.setBorder(BorderFactory.createBevelBorder(1));
        textDisplay.setForeground(new Color(0, 60, 70));
        textDisplay.setFont(new Font("Comic Sans", Font.BOLD, 15));
        textDisplay.setEditable(false);
        textDisplay.setBackground(new Color(196, 223, 230));

        JScrollPane scrollPane = new JScrollPane(textDisplay);
        scrollPane.setPreferredSize(new Dimension(700, 250));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


        inputFromUser.setText("What do you want to do?");
        inputFromUser.setBounds(0, 300, 2, 150);
        inputFromUser.setFont(new Font("Consolas", Font.CENTER_BASELINE, 20));

        textField.setPreferredSize(new Dimension(500, 100));
        textField.setFont(new Font("Consolas", Font.CENTER_BASELINE, 15));
        textField.setForeground(Color.black);
        textField.setBackground(new Color(196, 223, 230));
        textField.setCaretColor(Color.BLACK);


        panel1 = new JPanel();
        panel1.setBackground(new Color(102, 165, 173));
        panel1.setBounds(0, 0, 1500, 300);

        panel2 = new JPanel();
        panel2.setBackground(new Color(102, 165, 173));
        panel2.setBounds(0, 300, 1500, 125);

        panel3 = new JPanel();
        panel3.setBackground(Color.black);
        panel3.setBounds(0, 450, 1500, 550);

        panel1.add(scrollPane);
        panel2.add(inputFromUser);
        panel2.add(textField);

        panel4 = new MapView(board.rooms, board.getCurrentRoom().getName());
        panel4.setBounds(0, 750, 1500, 550);
        panel4.setBackground(Color.black);


        gameFrame.add(panel1);
        gameFrame.add(panel2);
        gameFrame.add(panel4);
        gameFrame.setBackground(Color.black);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(1500, 1000);
        if (!calledOnce) {
            textField.addActionListener(this);
            calledOnce = true;

        }
        textField.requestFocusInWindow();

        gameFrame.setVisible(true);
    }


    private void displayCombat() {

        Music.playMusic(soundName);

        rpsGame = new JTextArea();
        if (board.totalEnemies < 1) {
            rpsGame.setText("You have finally come face to face with Copernicus Rex Verwirrtheit Theodore!" +
                    "This is your chance to finally defeat your captor and gain the flight to the final key to escaping." +
                    "To defeat him, you must win in combat... or rock, paper, scissors." +
                    "\nPlease choose from the following numbers:" +
                    "\n1: ROCK" +
                    "\n2: PAPER" +
                    "\n3: SCISSOR");
        } else {
            rpsGame.setText("You have come face to face with a monster!" +
                    "To defeat it, you must win in combat... or rock, paper, scissors." +
                    "\nPlease choose from the following numbers:" +
                    "\n1: ROCK" +
                    "\n2: PAPER" +
                    "\n3: SCISSOR");
        }

        rpsGame.setPreferredSize(new Dimension(500, 100));
        rpsGame.setBounds(50, 50, 100, 100);
        rpsGame.setFont(new Font("Sans Script", Font.BOLD, 15));
        rpsGame.setLineWrap(true);
        rpsGame.setWrapStyleWord(true);
        rpsGame.setBorder(BorderFactory.createBevelBorder(1));
        rpsGame.setForeground(Color.black);
        rpsGame.setBackground(new Color(80, 196, 131));
        rpsGame.setEditable(false);
        numInput = new JTextField();
        numInput.setPreferredSize(new Dimension(500, 100));
        numInput.setBackground(new Color(241, 243, 206));

        scrollPane = new JScrollPane(rpsGame);
        scrollPane.setPreferredSize(new Dimension(500, 250));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel panel = new JPanel();

        panel.add(scrollPane);

        JLabel promptLabel = new JLabel();
        promptLabel.setText("What number do you choose?");
        promptLabel.setFont(new Font("Consolas", Font.CENTER_BASELINE, 20));
        promptLabel.setForeground(Color.white);
        promptPanel = new JPanel();
        promptPanel.add(promptLabel, BorderLayout.CENTER);
        promptPanel.add(numInput);
        promptPanel.setBackground(new Color(1, 11, 12));

        gameFrame.add(scrollPane, BorderLayout.NORTH);
        gameFrame.add(promptPanel);
        gameFrame.setVisible(true);
        gameFrame.setSize(700, 500);
        gameFrame.setResizable(false);
        combat.startCombat(player, board.getCurrentRoom(), board);
        numInput.addActionListener(this);
    }



    public void gameOverScreen() {
        gameFrame.remove(panel1);
        gameFrame.remove(panel2);

        ImageIcon lost = new ImageIcon(getClass().getResource("over.png"));

        JLabel imgLabel = new JLabel(lost);
        add(imgLabel);

        gameFrame.add(imgLabel);
        gameFrame.setVisible(true);

    }

    public void winScreen() {
        JTextArea winTextArea = new JTextArea("You use the lizard key on the door to exit." +
                "\nDarkness surrounds you and wind presses against you back as if the ground is being pulled beneath you." +
                "                                                                                                                         " +
                "\nYou close your eyes to avoid sickness, only for the movement around you to stop." +
                "                                                                                                                         " +
                "\nUpon opening your eyes, you are staring out a small window with people in white scrubs passing in a hall." +
                "                                                                                                                         " +
                "You turn around to see padded walls, only to realize that you have escaped Copernicus Rex Verwirrtheit Theodore for now.");
        winTextArea.setEditable(false);
        winTextArea.setLineWrap(true);
        winTextArea.setWrapStyleWord(true);
        winTextArea.setBorder(BorderFactory.createSoftBevelBorder(2));
        winTextArea.setForeground(Color.black);
        winTextArea.setFont(new Font("Broadway", Font.ITALIC, 17));
        winTextArea.setBackground(new Color(242, 92, 0));
        winTextArea.setPreferredSize(new Dimension(500, 285));

        JPanel pane = new JPanel();
        pane.add(winTextArea, BorderLayout.CENTER);
        pane.setBackground(new Color(175, 68, 37));

        gameFrame.add(pane);
        gameFrame.setSize(700, 600);
        gameFrame.setVisible(true);
        gameFrame.setResizable(false);
    }

}
