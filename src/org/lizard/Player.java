package org.lizard;

public class Player {
    private String name;
    private boolean hasKey = false;
    private String currentLocation = "Library";

    public Player(String name) {
        this.name = name;
    }

    public void addKeyToInventory() {
        hasKey = true;
    }
}
