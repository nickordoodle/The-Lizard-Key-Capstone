package org.lizard;

import org.lizard.user.Prompter;

import java.util.Scanner;

public class Game {

    MyJFrame frame;

    public void start() {

        new Funsies("jump", "Good for you");
        new Funsies("hello", "hi");
        new Funsies("help", "examine something");
        new Funsies("where", "idk figure it out");
        new Funsies("what", "idk figure it out");
        new MyJFrame();

    }

}
