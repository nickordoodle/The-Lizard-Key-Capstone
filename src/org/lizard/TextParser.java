package org.lizard;

import java.util.*;

public class TextParser {
    HashMap<String, Integer> nouns = new HashMap<>();
    HashMap<String, Integer> verbs = new HashMap<>();
    Player player;

    public TextParser(Player player) {
        nouns.put("key", 1);
        nouns.put("library", 2);
        verbs.put("grab", 1);
        verbs.put("go", 2);
        this.player = player;
    }

    public void parse(String userInput) {
        List<String> words = Arrays.asList(userInput.split(" "));
        Integer noun = null;
        Integer verb = null;

        for(int i = 0; i < words.size(); i++) {
            noun = nouns.get(words.get(i));
            if(noun != null) {
                words.remove(i);
                break;
            }
        }

        for(int i = 0; i < words.size(); i++) {
            verb = verbs.get(words.get(i));
            if(verbs != null) {
                words.remove(i);
                break;
            }
        }

        if(verb == null || noun == null) {
            System.out.println("I don't understand");
            return;
        } else if(verb == 1 && noun == 1) {
            player.addKeyToInventory();
        } else if(verb == 2) {
        }


    }
}
