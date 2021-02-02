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

    public String printDescription() {
        return description;
    }

    public Command getCommand() {
        return event;
    }
}
