package org.lizard;

public class TextParser {
    GameDictionary gameDictionary;

    public TextParser(GameDictionary gameDictionary) {
        this.gameDictionary = gameDictionary;
    }

    public Command parse(String userInput) {
        String[] words = userInput.split(" ");
        Integer verb = null;
        GameDictionary.Noun noun = null;
        GameDictionary.Noun targetNoun = null;

        for (String word : words) {
            Integer possibleVerb = gameDictionary.checkVerb(word);
            if (possibleVerb != null) {
                verb = possibleVerb;
                break;
            }
        }

        for (int i = 0; i < words.length; i++ ) {
            GameDictionary.Noun possibleNoun = gameDictionary.checkNoun(words[i]);
            if (possibleNoun != null) {
                noun = possibleNoun;
                words[i] = "";
                break;
            }
        }

        for (String word : words) {
            GameDictionary.Noun possibleNoun = gameDictionary.checkNoun(word);
            if (possibleNoun != null) {
                targetNoun = possibleNoun;
                break;
            }
        }

        if(targetNoun != null) {
            return new Command(verb, noun, targetNoun);
        }
        return new Command(verb, noun);
    }
}
