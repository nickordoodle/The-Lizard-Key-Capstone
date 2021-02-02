package org.lizard;

import java.util.*;

public class TextParser {
    GameDictionary gameDictionary;

    public TextParser(GameDictionary gameDictionary) {
        this.gameDictionary = gameDictionary;
    }

    public Command parse(String userInput) {
        List<String> userInputWords = new ArrayList<>(Arrays.asList(userInput.split(" ")));
        Integer verbCandidate = null;
        GameDictionary.Noun[] nounCandidates = null;
        GameDictionary.Noun[] targetNounCandidates = null;
        Map<String, Set<GameDictionary.Noun>> knownWords = gameDictionary.getKnownWords();

        for (int i = 0; i < userInputWords.size(); i++) {
            Integer verbCategory = gameDictionary.getVerbCategory(userInputWords.get(i));
            if (verbCategory != null) {
                verbCandidate = verbCategory;
                userInputWords.remove(i);
                break;
            }
        }

        Set<GameDictionary.Noun> ids = null;

        for(int i = 0; i < userInputWords.size(); i++) {
            Set<GameDictionary.Noun> nounSet = knownWords.get(userInputWords.get(i));
            if(nounSet == null) {
                continue;
            }

            if(ids == null) {
                ids = nounSet;
            }

            ids.retainAll(nounSet);
            if(ids.size() == 1) {
                nounCandidates = new GameDictionary.Noun[ids.size()];
                nounCandidates = ids.toArray(nounCandidates);
                Arrays.stream(nounCandidates[0].getName().split(" ")).forEach(userInputWords::remove);
                break;
            }
            if(i == userInputWords.size() - 1 && ids.size() > 1) {
                nounCandidates = new GameDictionary.Noun[ids.size()];
                nounCandidates = ids.toArray(nounCandidates);
                if(userInputWords.get(i).equals("room")) continue;
                ids.forEach(word -> {
                    Arrays.stream(word.getName().split(" ")).forEach(userInputWords::remove);
                });
            }

        }
        ids = null;
        for(int i = 0; i < userInputWords.size(); i++) {
            Set<GameDictionary.Noun> nounSet = knownWords.get(userInputWords.get(i));
            if(nounSet == null) {
                continue;
            }
            if(ids == null) {
                ids = nounSet;
            }
            ids.retainAll(nounSet);
            if(ids.size() == 1) {
                targetNounCandidates = new GameDictionary.Noun[ids.size()];
                targetNounCandidates = ids.toArray(targetNounCandidates);
            }
            if(i == userInputWords.size() - 1 && ids.size() > 1) {
                targetNounCandidates = new GameDictionary.Noun[ids.size()];
                ids.toArray(targetNounCandidates);
            }

        }
        if(targetNounCandidates != null) {
            return new Command(verbCandidate, nounCandidates, targetNounCandidates);
        } else {
            return new Command(verbCandidate, nounCandidates);
        }

    }
}
