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

    public Item(String name, String description) {
        super(name, description);
        this.setExaminable(true);
        this.setDroppable(true);
        this.setGrabable(true);
        this.setMovable(true);
        this.setPuttable(true);
    }

    public Item(String name, Lock lock, String description) {
        super(name, lock);
        this.setDescription(description);
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
