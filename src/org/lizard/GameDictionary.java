package org.lizard;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GameDictionary {
    private static final GameDictionary gameDictionary = new GameDictionary();

    private final Map<String, Integer> verbs = new HashMap<>();
//    private final Map<Integer, Noun> nouns = new HashMap<>();
    public final Map<String, Set<Noun>> knownWords = new HashMap<>();

    private GameDictionary() {
        setVerbs();
    }

    private void setVerbs() {
        verbs.put("grab", 1);
        verbs.put("take", 1);
        verbs.put("go", 2);
        verbs.put("move", 2);
        verbs.put("examine",3);
        verbs.put("look", 3);
        verbs.put("use", 4);
        verbs.put("drop", 5);
    }

//    public void addNoun(Noun noun) {
//        nouns.put(noun.getId(), noun);
//    }

    public Map<String, Set<Noun>> getKnownWords() {
        return knownWords;
    }

    public Integer getVerbCategory(String word) {
        return verbs.get(word);
    }
//    public Noun checkNoun(Integer id) {
//        return nouns.get(id);
//    }

    public static class Noun {
        private static final AtomicInteger count = new AtomicInteger(0);
        private final int id;
        private boolean grabable = false;
        private boolean droppable = false;
        private boolean examinable = false;
        private boolean movable = false;
        private boolean openable = false;
        private boolean readable = false;
        private boolean puttable = false;
        private boolean eatable = false;
        private boolean chatable = false;

        public boolean isUseable() {
            return useable;
        }

        public void setUseable(Lock lock) {
            this.lock = lock;
            useable = true;
        }

        private boolean useable = false;

        private Lock lock = null;

        private String name;

        private String description = "hi, i am a noun";

        public Noun(String name) {
            this.name = name;
            id = count.incrementAndGet();
            Arrays.stream(name.split(" ")).forEach(word -> {
                addKnownWord(this, word);
            });
        }

        public Noun(String name, Lock lock) {
            this(name);
            setUseable(lock);
        }
        public Noun(String name, String description) {
            this(name);
            this.description = description;

        }

        public boolean isGrabable() {
            return grabable;
        }

        public void setGrabable(boolean grabable) {
            this.grabable = grabable;
        }

        public boolean isDroppable() {
            return droppable;
        }

        public void setDroppable(boolean droppable) {
            this.droppable = droppable;
        }

        public boolean isExaminable() {
            return examinable;
        }

        public void setExaminable(boolean examinable) {
            this.examinable = examinable;
        }

        public boolean isMovable() {
            return movable;
        }

        public void setMovable(boolean movable) {
            this.movable = movable;
        }

        public boolean isOpenable() {
            return openable;
        }

        public void setOpenable(boolean openable) {
            this.openable = openable;
        }

        public boolean isReadable() {
            return readable;
        }

        public void setReadable(boolean readable) {
            this.readable = readable;
        }

        public boolean isPuttable() {
            return puttable;
        }

        public void setPuttable(boolean puttable) {
            this.puttable = puttable;
        }

        public boolean isEatable() {
            return eatable;
        }

        public void setEatable(boolean eatable) {
            this.eatable = eatable;
        }

        public boolean isChatable() {
            return chatable;
        }

        public void setChatable(boolean chatable) {
            this.chatable = chatable;
        }

//        private void addToGameDictionary() {
//            gameDictionary.addNoun(this);
//
//        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }


        public String getName() {
            return name;
        }

        public Lock getLock() {
            return lock;
        }
        public void deleteLock() {
            lock = null;
        }
        public int getId() {
            return id;
        }

        public void addKnownWord(Noun noun, String word) {
            Set<Noun> knownWord = gameDictionary.knownWords.get(word);
            if(knownWord == null) {
                gameDictionary.knownWords.put(word, new HashSet<>());
                knownWord = gameDictionary.knownWords.get(word);

            }
            knownWord.add(noun);
        }
    }


//    public void printNouns() {
//        gameDictionary.nouns.entrySet().forEach(set -> {
//            System.out.println(set.getValue().getName() + " id is " + set.getKey());
//        });
//    }

    public static GameDictionary getGameDictionary() {
        return gameDictionary;
    }


}
