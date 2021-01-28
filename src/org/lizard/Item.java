package org.lizard;

public class Item extends GameDictionary.Noun {

    public Item(String name) {
        super(name);
        this.setExaminable(true);
        this.setDroppable(true);
        this.setGrabable(true);
        this.setMovable(true);
        this.setPuttable(true);
    }

    public String getName() {
        return super.getName();
    }

}
