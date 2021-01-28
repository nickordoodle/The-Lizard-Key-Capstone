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

        for (String word : words) {
            Integer possibleVerb = gameDictionary.checkVerb(word);
            if (possibleVerb != null) {
                verb = possibleVerb;
                break;
            }
        }

        for (String word : words) {
            GameDictionary.Noun possibleNoun = gameDictionary.checkNoun(word);
            if (possibleNoun != null) {
                noun = possibleNoun;
                break;
            }
        }
        return new Command(verb, noun);
    }
}
