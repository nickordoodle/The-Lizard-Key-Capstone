package org.lizard;

import org.lizard.user.Prompter;

import java.util.Scanner;

public class Game {
    GameDictionary gameDictionary = GameDictionary.getGameDictionary();
    Player player = new Player("Edgar");
    TextParser parser = new TextParser(gameDictionary);
    Prompter prompter = new Prompter(new Scanner(System.in));
    Board board = new Board();
    Actions actions = new Actions(board, player);

    public void start() {
        new Direction("north");
        new Direction("south");
        new Direction("east");
        new Direction("west");
        new Funsies("jump", "Good for you");
        new Funsies("hello", "hi");
        new Funsies("help", "examine something");
        new Funsies("where", "idk figure it out");
        new Funsies("what", "idk figure it out");

        Story.introduction();

        while(true){
            String input = prompter.promptPlayer("What you wanna do?");
            Command command = parser.parse(input);
            actions.execute(command);
        }
    }

}
