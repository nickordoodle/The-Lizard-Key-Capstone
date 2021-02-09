package org.lizard;

class Command {
    private final Integer verb;
    private GameDictionary.Noun[] noun = null;
    private GameDictionary.Noun[] targetNoun = null;

    public Command(Integer verb, GameDictionary.Noun[] noun) {
        this.verb = verb;
        this.noun = noun;
    }

    public Command(Integer verb, GameDictionary.Noun[] noun, GameDictionary.Noun[] targetNoun) {
        this(verb, noun);
        this.targetNoun = targetNoun;
    }

    public Integer getVerb() {
        return verb;
    }

    public GameDictionary.Noun[] getNoun() {
        return noun;
    }

    public GameDictionary.Noun[] getTargetNoun() {

        return targetNoun;
    }

    public boolean isAmbiguous() {
        if (noun != null && noun.length > 1 || targetNoun != null && targetNoun.length > 1) {
            return true;
        }
        return false;
    }

    public void setNoun(GameDictionary.Noun[] noun) {
        this.noun = noun;
    }

    public void setTargetNoun(GameDictionary.Noun[] targetNoun) {
        this.targetNoun = targetNoun;
    }
}
