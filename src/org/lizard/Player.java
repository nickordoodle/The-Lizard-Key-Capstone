package org.lizard;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private boolean hasKey = false;
    Inventory inventory = new Inventory();

    public Player(String name) {
        this.name = name;
    }
    public Inventory getInventory() {
        return inventory;
    }

    public boolean getHasKey() {
        return hasKey;
    }
    public String getName() {
        return name;
    }

    class Inventory extends Item {
        List<Item> inventory = new ArrayList<>();

        public Inventory() {
            super("inventory");
        }

        public void add(Item item) {
            if(item.getName().equals("key")) {
                hasKey = true;
            }
            inventory.add(item);
        }

        public Item drop(Item item) {
            int index = inventory.indexOf(item);
            if(index != -1) {
                return inventory.remove(index);
            }
            System.out.println("That is not in your bag");
            return null;
        }
        public boolean has(Item item) {
            return inventory.contains(item);
        }

        public String getDescription() {
            StringBuilder inventoryDescription = new StringBuilder();
            inventory.forEach(item -> inventoryDescription.append(item.getName() + "\n"));
            return inventoryDescription.toString().equals("") ? "You have nothing in your inventory": inventoryDescription.toString();
        }

    }

}


