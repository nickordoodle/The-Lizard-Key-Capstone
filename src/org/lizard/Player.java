package org.lizard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    Inventory inventory = new Inventory();
    private String name;
    boolean hasWinningKey = false;
    boolean hasMagicCape = false;


    int playerHP = 250;

    public Player(String name) {
        this.name = name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getName() {
        return name;
    }

    class Inventory extends Item {
        List<GameDictionary.Noun> inventory = new ArrayList<>();


        public Inventory() {
            super("inventory");
        }

        public String add(GameDictionary.Noun item) {

            if (item.getName().equalsIgnoreCase("lizard key")) {
                hasWinningKey = true;
            }
            inventory.add(item);
            return "Added to inventory";
        }

        public List<GameDictionary.Noun> getItems() {
            return inventory;
        }

        // Returns a list of all item names that the player has in inventory
        public List<String> getItemNames() {
            return inventory
                    .stream()
                    .map(GameDictionary.Noun::getName)
                    .collect(Collectors.toList());
        }


        public GameDictionary.Noun drop(GameDictionary.Noun item) {
            int index = inventory.indexOf(item);
            if (index != -1) {
                return inventory.remove(index);
            }
            System.out.println("That is not in your bag");
            return null;
        }

        public boolean has(GameDictionary.Noun item) {
            return inventory.contains(item);
        }

        public String getDescription() {
            StringBuilder inventoryDescription = new StringBuilder();
            inventory.forEach(item -> inventoryDescription.append(item.getName()).append("\n"));
            return inventoryDescription.toString().equalsIgnoreCase("") ? "You have nothing in your inventory" : inventoryDescription.toString();
        }

        public void consumeItem(GameDictionary.Noun item) {
            inventory.remove(item);
        }

    }

    public boolean isHasMagicCape() {
        return hasMagicCape;
    }

    public void setHasMagicCape(boolean hasMagicCape) {
        this.hasMagicCape = hasMagicCape;
    }

    public int getPlayerHP() {
        return playerHP;
    }

}


