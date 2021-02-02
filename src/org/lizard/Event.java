package org.lizard;

public class Event extends Command {

    public Event(Integer verb, GameDictionary.Noun noun) {
        super(verb, new GameDictionary.Noun[]{noun});
    }

    public Event(Integer verb, GameDictionary.Noun noun, GameDictionary.Noun targetNoun) {
        super(verb, new GameDictionary.Noun[]{noun}, new GameDictionary.Noun[]{targetNoun});
    }
}
