package org.lizard;

import java.util.Map;
import java.util.Scanner;

public class Game {
    Player player = new Player("Edgar");
    TextParser parser = new TextParser(player);
    Scanner playerInput = new Scanner(System.in);

    public void start() {

        while(true){

            System.out.println("what do you wanna do?");
            String input = playerInput.nextLine();
            parser.parse(input);

        }
    }
}
