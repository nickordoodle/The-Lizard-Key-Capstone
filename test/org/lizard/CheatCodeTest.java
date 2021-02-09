package org.lizard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class CheatCodeTest {

    private Board board;
    private Player player;

    @Before
    public void setUp() throws Exception {
        player = new Player("bob");
        board = new Board();
    }

    @Test
    public void testGivePlayerAllKeysBesidesLizardKey() {
        Player.Inventory inv = player.getInventory();
        Actions.CheatCode.givePlayerAllKeysBesidesLizardKey(inv, board);
        List<GameDictionary.Noun> items = inv.getItems();
        items.forEach(item -> assertFalse(item.getName().equals("lizard key")));
        List<GameDictionary.Noun> keys =  items.stream()
                .filter(item -> item.getName().contains("key"))
                .collect(Collectors.toList());
        assertTrue(keys.stream().anyMatch(key -> key.getName().equalsIgnoreCase("skeleton key")));
        assertTrue(keys.stream().anyMatch(key -> key.getName().equalsIgnoreCase("library key")));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void drop() {
    }
}