
//MOST UPDATED

package org.lizard;

import org.lizard.constants.GameInformation;
import org.lizard.util.Music;
import org.lizard.util.Screen;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    JLabel imageLabel;
    JTextField textField = new JTextField();
    JButton enterGame, quitGame;
    JFrame frame;
    JPanel titlePanel;
    JTextArea mainStoryText, instructionsTxt;
    JTextField desicionField = new JTextField();
    List<GameDictionary.Noun> nounList = null;
    boolean decisionListener = false;
    Command command = null;
    JPanel instructionsPanel, storyPanel, mapPanel;
    JPanel inputPanel;
    JTextField numInput;
    BufferedImage img;

    boolean calledOnce=false;

    MyJFrame() {
        new Funsies("jump", "Good for you");
        new Funsies("hello", "hi");
        new Funsies("help", "examine something");
        new Funsies("where", "idk figure it out");
        new Funsies("what", "idk figure it out");

        frame = createGameJFrame();

        //create background image
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("bluePuppeteer.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert img != null;
        Image bgImg = img.getScaledInstance(540, 500, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(bgImg);
        frame.setContentPane(new JLabel(imageIcon));
        imageLabel = new JLabel(new ImageIcon(img));
        frame.setBackground(Color.black);

        //button to play the game
        enterGame = new JButton("Enter Game");

        int buttonWidth = 200;
        int buttonHeight = 60;
        // Subtract width and height so we account for the button itself
        int xCoordinate =  (Screen.WIDTH - buttonWidth) / 2;
        int yCoordinate =  (Screen.HEIGHT - buttonHeight) / 2;
        // Set the button's properties
        enterGame.setBounds(xCoordinate, yCoordinate, buttonWidth, buttonHeight);
        enterGame.addActionListener(this);

        //button to play the game
        quitGame = new JButton("Quit Game");
        quitGame.setBounds(990,415,120,40);
        quitGame.addActionListener(this);

        //panel with the game title
        titlePanel = new JPanel();
        titlePanel.setBackground(Color.black);
        titlePanel.setBounds(0, 80, 1500, 80);
        JLabel welcome = new JLabel("The Lizard Key Game!");
        welcome.setFont(new Font("IronWood", Font.BOLD, 30));
        welcome.setForeground(Color.green);

        //instructions for the game
        instructionsPanel = new JPanel();
        instructionsPanel.setBackground(Color.blue);
        instructionsPanel.setBounds(10,100,1500,500);
        instructionsPanel.setLayout(new BorderLayout());

        //instructions text
        instructionsTxt = new JTextArea();
        instructionsTxt.setText(board.howToPlayInGame());
        instructionsTxt.setLineWrap(true);
        instructionsTxt.setWrapStyleWord(true);
        instructionsTxt.setForeground(new Color(0, 60, 70));//light blue
        instructionsTxt.setFont(new Font("Comic Sans",Font.BOLD, 15));
        instructionsTxt.setEditable(false);
        instructionsTxt.setBackground(Color.white);

        //add text to the instructions panel
        titlePanel.add(welcome,BorderLayout.CENTER);
        instructionsPanel.add(instructionsTxt);

        // Add components to the frame
        frame.add(titlePanel);
        frame.add(enterGame);
        frame.add(imageLabel);
        //frame.add(instructionsPanel);
        // Make the frame visible to the player
        frame.setVisible(true);

    }

    // Creates the initial JFrame and sets basic properties
    private JFrame createGameJFrame() {
        JFrame gameFrame = new JFrame();
        // Set title and use String text from GameInformation Constants
        gameFrame.setTitle(GameInformation.TITLE);
        // Set frame to full screen via modifying its extended state
        gameFrame.setSize(1600,1060);
        gameFrame.setResizable(false);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return gameFrame;
    }

    public String decision(List<GameDictionary.Noun> nounList, Command command) {
        desicionField.setPreferredSize(new Dimension(250,40));
        desicionField.setFont(new Font("Consolas", Font.BOLD, 15));
        desicionField.setForeground(Color.blue);
        desicionField.setBackground(Color.white);
        desicionField.setCaretColor(Color.blue);

        inputPanel.remove(textField);
        frame.repaint();
        frame.revalidate();

        inputPanel.add(desicionField);
        desicionField.requestFocusInWindow();
        if(!decisionListener) {
            desicionField.addActionListener(this);
            decisionListener = true;
        }
        this.nounList = nounList;
        this.command = command;

        StringBuilder choices = new StringBuilder();
        for(int i = 0; i < nounList.size(); i++) {
            if(i == nounList.size() - 1) {
                choices.append(nounList.get(i).getName());
            } else {
                choices.append(nounList.get(i).getName()).append(" or ");
            }
        }
        return choices.toString();
    }

    public void createGameView() {

        img = null;
        try {
            img = ImageIO.read(new File("blackPuppeteer.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert img != null;

        imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setLayout(null);
        imageLabel.setBackground(Color.black);
        imageLabel.setBounds(300,0, 990,135);
//        frame.add(imageLabel); //shows correct location but doesn't remove img

        img = null;
        try {
            img = ImageIO.read(new File("lizard.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert img != null;

        imageLabel = new JLabel(new ImageIcon(img.getScaledInstance(420, 500, Image.SCALE_SMOOTH)));
        imageLabel.setLayout(null);
        imageLabel.setBackground(Color.black);
        imageLabel.setBounds(30,260, 420,500);
//        frame.add(imageLabel); //shows correct location but doesn't remove img

        frame.setBackground(Color.black);

        frame.add(quitGame);
        titlePanel.setBounds(50,50, 450,80);
        frame.add(titlePanel);
        gameScreen(board.introduction());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==quitGame){
            frame.dispose();
            System.exit(0);
        }
        if(e.getSource()==enterGame){
//            gameScreen(Story.introduction()); //shows main game
            // Remove components from earlier
            frame.getContentPane().removeAll();
            frame.repaint();
            frame.revalidate();

            createGameView();
//            Music.playMusic("princeofdarkness.wav");

            mapPanel = new MapView(board.rooms, board.getCurrentRoom().getName());
            mapPanel.setBounds(100,40,1000,Screen.HEIGHT);

            frame.add(mapPanel);
            frame.revalidate();
            frame.repaint();

        }
        if(e.getSource()==desicionField) {
            result = desicionField.getText();

            desicionField.setText("");
            for (GameDictionary.Noun noun : nounList) {
                if (noun.getName().equals(result)) {
                    if (command.getNoun().length <= 1) {
                        command.setTargetNoun(new GameDictionary.Noun[]{noun});
                    } else {
                        command.setNoun(new GameDictionary.Noun[]{noun});
                    }

//                    frame.remove(instructionsPanel);
                    frame.remove(inputPanel);
//                    frame.remove(mapPanel);
                    frame.repaint();
                    frame.revalidate();

                    gameScreen(actions.execute(command));
//                    frame.setVisible(true);//?? why
                    return;
                }
            }
//            frame.remove(instructionsPanel);
            frame.remove(inputPanel);
//            frame.remove(mapPanel);
            frame.repaint();
            frame.revalidate();

            gameScreen("you gotta be specific.");
//            frame.setVisible(true);??
        }
        if(e.getSource()==textField){
            result = textField.getText();
            Command command = parser.parse(result);
            String response = actions.execute(command);
            mainStoryText.setText(response);

            //this code shows map with updted orange oval.
            frame.remove(mapPanel);
            frame.repaint();
            frame.revalidate();

            mapPanel = new MapView(board.rooms, board.getCurrentRoom().getName());
            mapPanel.setBounds(100,40,1000,Screen.HEIGHT);
            frame.add(mapPanel);

            // Winning condition check - player has winning key
            if(player.hasWinningKey && board.getCurrentRoom().getName().equals("keyRoom")){
//                frame.remove(instructionsPanel);
//                frame.remove(inputPanel);
//                frame.remove(mapPanel);
//                frame.repaint();
//                frame.revalidate();

                winScreen();
            }

            if(board.getCurrentRoom().getEnemy() != null && !board.getCurrentRoom().getEnemy().enemyName.equals("Copernicus Rex Verwirrtheit Theodore") ){
//                frame.remove(instructionsPanel);
                frame.remove(inputPanel);
//                frame.remove(mapPanel);
                frame.repaint();
                frame.revalidate();

                displayCombat();
            }

            if(response.equals("The sculpture, as you now know, was just Copernicus Rex Verwirrtheit Theodore. The same red liquid from the floor streams from his eyes.") && !bossDead) {
                frame.remove(inputPanel);
//                frame.remove(mapPanel);
                frame.repaint();
                frame.revalidate();

                displayCombat();
                bossDead = true;
            }
            textField.setText("");
        }

        if(e.getSource()==numInput){

            rpsGame.setText(combat.playerTakesTurn(Integer.parseInt(numInput.getText())));
            if(combat.checkGameEndingStatus().equals("Enemy won")) {
                frame.remove(promptPanel);
                frame.remove(scrollPane);
                frame.repaint();
                frame.setVisible(true);
                frame.repaint();
                frame.revalidate();

                gameOverScreen();
                if(clip != null) {
                    clip.stop();
                }
            }
            else if(combat.checkGameEndingStatus().equals("You defeated the monster!")){

                if(combat.bossTime) {
                    combat.bossTime = false;
                    board.totalEnemies = -1;
                    frame.remove(promptPanel);
                    frame.remove(scrollPane);
                    frame.repaint();
                    frame.revalidate();

                    gameScreen(actions.execute(new Event(99, board.allItems.get("sculpture"))));
                    frame.setVisible(true);
                    if(clip != null) {
                        clip.stop();
                    }

                } else {
                    frame.remove(promptPanel);
                    frame.remove(scrollPane);
                    frame.repaint();
                    frame.revalidate();


                    if (board.totalEnemies < 0) {
                        gameScreen("Copernicus Rex Verwirrtheit Theodore has fallen in his own world! His magic cape has fallen with him, and you are one step closer to freedom!");
                    } else {
                        gameScreen("You defeated the monster!");
                    }
                    frame.setVisible(true);
                    if(clip != null) {
                        clip.stop();
                    }
                }

            }
            numInput.setText("");
        }
    }

    private void gameScreen(String initialPrint) {
//        frame.getContentPane().setBackground(Color.red); //i think shows red color if wrong way.
//        frame.setSize(1500,1525);

        //main story text
        mainStoryText = new JTextArea();
        mainStoryText.setText(initialPrint);
        mainStoryText.setLineWrap(true);
        mainStoryText.setWrapStyleWord(true);
//        mainStoryText.setBorder(BorderFactory.createBevelBorder(1));
        mainStoryText.setForeground(Color.white);
        mainStoryText.setFont(new Font("Comic Sans",Font.PLAIN, 15));
        mainStoryText.setEditable(false);
        mainStoryText.setBackground(Color.black);

        //makes story text scrollable
        JScrollPane scrollPane = new JScrollPane(mainStoryText);
        scrollPane.setPreferredSize(new Dimension(700,250));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        //input prompt
        inputFromUser.setText("What do you want to do?");
        inputFromUser.setFont(new Font("Consolas", Font.BOLD, 20));
        inputFromUser.setForeground(Color.green);

        //area where user enters input
        textField.setPreferredSize(new Dimension(250,40));
        textField.setFont(new Font("Consolas", Font.BOLD, 15));
        textField.setForeground(Color.blue);
        textField.setBackground(Color.white);
        textField.setCaretColor(Color.blue);//text curser color

        //panel that contains the story text
        storyPanel = new JPanel();
        storyPanel.setBackground(Color.black);
        storyPanel.setBounds(-100,130,1800,280);

        //panel where the input is located.
        inputPanel = new JPanel();
        inputPanel.setBackground(Color.black);
        inputPanel.setBounds(-190,410,1800,60);

        storyPanel.add(scrollPane);
        inputPanel.add(inputFromUser);
        inputPanel.add(textField);

        frame.add(storyPanel);
        frame.add(inputPanel);

        if (!calledOnce) {
            textField.addActionListener(this);
            calledOnce = true;

        }
        textField.requestFocusInWindow();

        frame.setVisible(true);
    }

    //fighting scene with the enemy.
    private void displayCombat(){
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

        rpsGame.setPreferredSize(new Dimension(500,100));
        rpsGame.setBounds(50,50,100,100);
        rpsGame.setFont(new Font("Sans Script", Font.BOLD, 15 ));
        rpsGame.setLineWrap(true);
        rpsGame.setWrapStyleWord(true);
        rpsGame.setBorder(BorderFactory.createBevelBorder(1));
        rpsGame.setForeground(Color.black);
        rpsGame.setBackground(Color.CYAN);
        rpsGame.setEditable(false);

        numInput = new JTextField();
        numInput.setPreferredSize(new Dimension(500,100));
        numInput.setBackground(Color.white);

        scrollPane = new JScrollPane(rpsGame);
        scrollPane.setPreferredSize(new Dimension(500,250));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel panel = new JPanel();
        panel.add(scrollPane);

        JLabel promptLabel = new JLabel();
        promptLabel.setText("What number do you choose?");
        promptLabel.setFont(new Font("Consolas", Font.BOLD, 20));
        promptLabel.setForeground(Color.white);
        promptPanel = new JPanel();
        promptPanel.add(promptLabel, BorderLayout.CENTER);
        promptPanel.add(numInput);
        promptPanel.setBackground(Color.blue);

        frame.add(scrollPane, BorderLayout.NORTH);
        frame.add(promptPanel);
        frame.setVisible(true);
        frame.setSize(700,500);
        frame.setResizable(false);
        combat.startCombat(player, board.getCurrentRoom(), board);
        numInput.addActionListener(this);
    }

    public void gameOverScreen(){
//        frame.remove(instructionsPanel);
        frame.remove(inputPanel);
        frame.repaint();
        frame.revalidate();


        ImageIcon lost = new ImageIcon(getClass().getResource("over.png"));

        JLabel imgLabel = new JLabel(lost);
        add(imgLabel);

        frame.add(imgLabel);
        frame.setVisible(true);

    }

    public void winScreen(){
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
        winTextArea.setForeground(Color.green);
        winTextArea.setFont(new Font("Broadway",Font.ITALIC, 17));
        winTextArea.setBackground(Color.GREEN);
        winTextArea.setPreferredSize(new Dimension(500,285));

        JPanel pane = new JPanel();
        pane.add(winTextArea,BorderLayout.CENTER);
        pane.setBackground(Color.green);

        frame.add(pane);
        frame.setSize(700,600);
        frame.setVisible(true);
        frame.setResizable(false);
    }

}
