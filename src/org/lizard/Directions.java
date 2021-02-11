package org.lizard;

class Directions {
    public final Direction north = new Direction("north");
    public final Direction south = new Direction("south");
    public final Direction east = new Direction("east");
    public final Direction west = new Direction("west");

    public Directions() {
    }

    public Direction getDirection(String direction) {
        switch (direction) {
            case "west":
                return west;
            case "north":
                return north;
            case "south":
                return south;
            case "east":
                return east;
        }
        return null;
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
