package org.lizard;

class Command {
    private Integer verb;
    private GameDictionary.Noun noun;
    private GameDictionary.Noun targetNoun = null;

    public Command(Integer verb, GameDictionary.Noun noun) {
        this.verb = verb;
        this.noun = noun;
    }
    public Command(Integer verb, GameDictionary.Noun noun, GameDictionary.Noun noun2) {
        this(verb, noun);
        this.targetNoun = noun2;
    }

    public Integer getVerb() {
        return verb;
    }

    public GameDictionary.Noun getNoun() {
        return noun;
    }

    public  GameDictionary.Noun getTargetNoun() {
        return targetNoun;
    }
}
