package org.lizard;

import java.util.HashMap;
import java.util.Map;

public class GameDictionary {
    private static final GameDictionary gameDictionary = new GameDictionary();

    private Map<String, Integer> verbs = new HashMap<>();
    private Map<String, Noun> nouns = new HashMap<>();

    private GameDictionary() {
        setVerbs();
        setDirections();
    }

    private void setVerbs() {
        verbs.put("grab", 1);
        verbs.put("take", 1);
        verbs.put("go", 2);
        verbs.put("move", 2);
    }

    public void addNoun(Noun noun) {
        nouns.put(noun.getName(), noun);
    }

    public void setDirections() {
//        addNoun(new Direction("north"));
//        addNoun(new Direction("south"));
//        addNoun(new Direction("east"));
//        addNoun(new Direction("west"));
    }

    public Integer checkVerb(String word) {
        return verbs.get(word);
    }
    public Noun checkNoun(String word) {
        return nouns.get(word);
    }

    public static class Noun {
        private boolean grabable = false;
        private boolean droppable = false;
        private boolean examinable = false;
        private boolean movable = false;
        private boolean openable = false;
        private boolean readable = false;
        private boolean puttable = false;
        private boolean eatable = false;
        private boolean chatable = false;
        private String name;

        public Noun(String name) {
            this.name = name;
            addToGameDictionary();
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

        private void addToGameDictionary() {
            gameDictionary.addNoun(this);

        }

        public String getName() {
            return name;
        }
    }

    public void printNouns() {
        gameDictionary.nouns.entrySet().forEach(set -> {
            System.out.println(set.getKey() + " is in dictionary");
        });
    }

    public static GameDictionary getGameDictionary() {
        return gameDictionary;
    }

//    class Direction extends Noun {
//
//        public Direction(String direction) {
//            super("north");
//        }
//
//        public String getDirection() {
//            return getName();
//        }
//    }
}
