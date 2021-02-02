package org.lizard;

import org.lizard.user.Prompter;

import java.util.Scanner;

public class Game {
    public static GameDictionary gameDictionary = GameDictionary.getGameDictionary();
    private static Player player = new Player("Edgar");
    public static TextParser parser = new TextParser(gameDictionary);
    public static Prompter prompter = new Prompter(new Scanner(System.in));
    private static Board board = new Board();
    private static Actions actions = new Actions(board, player);

    public void start() {

        new Funsies("jump", "Good for you");
        new Funsies("hello", "hi");
        new Funsies("help", "examine something");
        new Funsies("where", "idk figure it out");
        new Funsies("what", "idk figure it out");

//        Story.introduction();

//        gameDictionary.printNouns();
        System.out.println(gameDictionary.knownWords);
        while(true){
            String input = prompter.promptPlayer("What you wanna do?");
            Command command = parser.parse(input);
            actions.execute(command);
        }
    }

}
