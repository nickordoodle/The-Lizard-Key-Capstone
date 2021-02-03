package org.lizard;

import org.lizard.user.Prompter;

import java.util.Scanner;

public class Game {

//    Player player = new Player("Edgar");
//    Prompter prompter = new Prompter(new Scanner(System.in));
//    Board board = new Board();
//    Actions actions = new Actions(board, player);
    MyJFrame frame;

    public void start() {

        new Funsies("jump", "Good for you");
        new Funsies("hello", "hi");
        new Funsies("help", "examine something");
        new Funsies("where", "idk figure it out");
        new Funsies("what", "idk figure it out");
        new MyJFrame();


//        Story.introduction();

//        gameDictionary.printNouns();


//        gameDictionary.printNouns();

//        while(true){
////            String input = prompter.promptPlayer("What you wanna do?");
////
//            Command command = parser.parse(input);
//            actions.execute(command);
//        }
    }

}
