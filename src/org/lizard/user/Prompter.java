package org.lizard.user;

import java.util.Scanner;

public class Prompter {
    private Scanner input;

    public Prompter(Scanner input) {
        this.input = input;
    }

    // Universal method that can print any input message, get a response from the player and send it back to the requester
    public String promptPlayer(String message) {
        System.out.print(message + "\n > ");
        String response = input.nextLine();
        return response;
    }

    // Give player option to see the game rules
    public void seeGameRules(String playerName) {
        System.out.print("\n" + playerName + ", would you like to know the game rules?" + "\n" +
                "Enter Y for yes - show me the rules or any other character for no - proceed without rules.");
        String response = input.next();

        if (response.toUpperCase().equals("Y")) {
            gameRules();
        }
    }

    // Present game rules **** Needs to be flushed out
    private void gameRules() {
        System.out.println("These are the rules to the game!");
    }
}
