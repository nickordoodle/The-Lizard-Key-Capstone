package org.lizard;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    Inventory inventory = new Inventory();

    public Player(String name) {
        this.name = name;
    }
    public Inventory getInventory() {
        return inventory;
    }

    class Inventory extends Item {
        List<Item> inventory = new ArrayList<>();

        public Inventory() {
            super("Inventory");
        }

        public void add(Item item) {
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

        public void printInventory() {
            inventory.forEach(System.out::println);
        }
    }

}


