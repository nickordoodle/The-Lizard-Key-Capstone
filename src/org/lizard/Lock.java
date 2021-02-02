package org.lizard;

public class Lock {
    GameDictionary.Noun noun;
    String description;
    Command event;

    public Lock(GameDictionary.Noun noun, String description, Event event) {
        this.noun = noun;
        this.description = description;
        this.event= event;
    }

    public GameDictionary.Noun getNoun() {
        return noun;
    }

    public void printDescription() {
        System.out.println(description);
    }

    public Command getCommand() {
        return event;
    }
}
