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
    public void testGivePlayerAllKeysBesidesLizardKeyNoLizardKeyFound() {
        Player.Inventory inv = player.getInventory();
        Actions.CheatCode.givePlayerAllKeysBesidesLizardKey(inv, board);
        List<GameDictionary.Noun> items = inv.getItems();
        items.forEach(item -> assertNotEquals("lizard key", item.getName()));
    }

    @Test
    public void testGivePlayerAllKeysBesidesLizardKeyPlayerInventoryIsNull() {
        Player.Inventory inv = null;
        String result = Actions.CheatCode.givePlayerAllKeysBesidesLizardKey(inv, board);
        assertEquals(result, "Oops! Cheat code get all keys besides lizard key did not work!");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void drop() {
    }
}