package org.lizard.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.*;

public class PrompterTest {

    Prompter prompter;

    @org.junit.Before
    public void setUp() throws Exception {
    }

    // Checks to see if returned input matches what was expected from entry
    @org.junit.Test
    public void promptPlayer() throws FileNotFoundException {
        prompter = new Prompter(new Scanner(new File("responses/action.txt")));
        String choice = prompter.promptPlayer("What would you like to do?");
        assertEquals("go east", choice);
    }
    
}