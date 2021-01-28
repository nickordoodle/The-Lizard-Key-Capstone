package org.lizard;

class Command {
    private Integer verb;
    private GameDictionary.Noun noun;

    public Command(Integer verb, GameDictionary.Noun noun) {
        this.verb = verb;
        this.noun = noun;
    }

    public Integer getVerb() {
        return verb;
    }

    public GameDictionary.Noun getNoun() {
        return noun;
    }
}
