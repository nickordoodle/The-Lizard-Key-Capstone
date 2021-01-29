package org.lizard;

public class Lock {
    GameDictionary.Noun noun;
    String description;
    Command command;

    public Lock(GameDictionary.Noun noun, String description, Command command) {
        this.noun = noun;
        this.description = description;
        this.command = command;
    }

    public GameDictionary.Noun getNoun() {
        return noun;
    }

    public void printDescription() {
        System.out.println(description);
    }

    public Command getCommand() {
        return command;
    }
}
