package org.lizard;

class Directions {
    public final Direction north = new Direction("north");
    public final Direction south = new Direction("south");
    public final Direction east = new Direction("east");
    public final Direction west = new Direction("west");

    public Directions() { }

    public Direction getNorth() {
        return north;
    }

    public Direction getSouth() {
        return south;
    }

    public Direction getEast() {
        return east;
    }

    public Direction getWest() {
        return west;
    }

    static class Direction extends GameDictionary.Noun {

        String direction;
        private Direction(String direction) {
            super(direction);
            this.direction = direction;
        }
        public String getDirection() {
            return direction;
        }
    }
}
