package org.lizard;

class Direction extends GameDictionary.Noun {

    public Direction(String direction) {
        super(direction);
    }

    public String getDirection() {
        return getName();
    }
}
