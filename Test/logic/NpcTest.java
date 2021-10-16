package logic;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída NpcTest sloužící k otestování třídy Npc.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

class NpcTest {

    Npc npc1 = new Npc("npc1", 2.5, 1);
    /**Npc npc2 = new Npc("npc2", 2.5, 2, Arrays.asList("gfhf", "fjf", "3","j"));*/

    Item item1 = new Item("item1", true, "");

    /**
     * Test jestli funguje vkládání itemu do npc.
     */
    @Test
    public void testInsertItemIntoNpc(){
        npc1.insertItem(item1);
        assertEquals(item1, npc1.getItemInNpc(item1.getName()));
        assertNull(npc1.getItemInNpc("item2"));
    }

    /**
     * Test jestli fungeje odstranění itemu z npc
     */
    @Test
    public void testRemoveItemInNpc() {
        npc1.insertItem(item1);
        assertEquals(item1, npc1.getItemInNpc("item1"));
        npc1.removeItemInNpc("item1");
        assertNull(npc1.getItemInNpc("item1"));
    }

    /**
     * Test fungování rozhovorů.

    @Test
    public void testChat() {
        assertEquals("1", npc2.getChat(npc2));
        assertEquals("2", npc2.getChat(npc2));
        assertEquals("3", npc2.getChat(npc2));
    }*/
}