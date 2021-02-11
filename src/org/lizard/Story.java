package org.lizard;

// Static / singleton pattern type of class
// Contains hard coded text required for
// the story of the game
class Story {
    static String introduction() {
        return "Like a dream, your thoughts carry you to an unknown universe. Four bare walls keep you as a prisoner to an overly bright room with no windows. " +
                "Shelves with millions of keys in varying sizes, shapes, and colors line the floors like passageways to doors around you.\n" +
                "What lies behind each door is unknown to you, but your options are limited, for this is no dream, and you are not imagining what you are seeing. " +
                "The keys you touch are as real as the keys to your home. The floor you stand on is as hard as the surface beneath your bed.\n" +
                "Like many others before you, you have become a pet to a man named Copernicus Rex Verwirrtheit Theodore, locksmith, creator of worlds, and master of confusion. " +
                "With no two worlds being the same, Mister Theodore has created each maze uniquely odd and fantastically tumultuous for every mouse searching for its cheese.\n" +
                "It is up to you to find the way out of this prison, for if you do not, you will forever be in an endless loop of rooms that lead to other rooms," +
                "the final and single exit a mystery never to be given away freely.";

    }

    static String howToPlay() {
        return "\n" + "\n" + "\n" + "\n" + "\n" + "\n" +
                "How to Play: \n" +
                "\n" +
                "Look for clues, and don't be afraid to explore!\n" +
                "To perform an action, type your command in the text box and hit enter.\n" +
                "\n" +
                "Examples of possible commands are: \n" +
                "\n" +
                "'rules' - to see instructions.\n" +
                "\n" +
                "'go east' - to travel east.\n" +
                "\n" +
                "'go north' - to travel north.\n" +
                "\n" +
                "'examine room' - to see items and doors in current room.\n" +
                "\n" +
                "'examine locked chest' - to examine the locked chest (locked chest can be substituted for any item in your current room).\n" +
                "\n" +
                "'inventory' - to see items in your inventory.\n" +
                "\n" +
                "'grab knife' - to grab a knife (substitute knife for item you wish to pick up).\n" +
                "\n" +
                "'drop knife' - to drop a knife (substitute knife for item you wish to drop from your inventory).\n" +
                "\n" +
                "'use skeleton key on east' - some doors or items may be locked. Using the proper key/item on the proper item or direction will ";

    }

    static String howToPlayInGame() {
        return "How to Play: \n" +
                "\n" +
                "Look for clues, and don't be afraid to explore!\n" +
                "To perform an action, type your command in the text box and hit enter.\n" +
                "\n" +
                "Examples of possible commands are: \n" +
                "\n" +
                "'rules' - to see instructions.\n" +
                "\n" +
                "'go east' - to travel east.\n" +
                "\n" +
                "'go north' - to travel north.\n" +
                "\n" +
                "'examine room' - to see items and doors in current room.\n" +
                "\n" +
                "'examine locked chest' - to examine the locked chest (locked chest can be substituted for any item in your current room).\n" +
                "\n" +
                "'inventory' - to see items in your inventory.\n" +
                "\n" +
                "'grab knife' - to grab a knife (substitute knife for item you wish to pick up).\n" +
                "\n" +
                "'drop knife' - to drop a knife (substitute knife for item you wish to drop from your inventory).\n" +
                "\n" +
                "'use skeleton key on east' - some doors or items may be locked. Using the proper key/item on the proper item or direction will ";

    }
}
