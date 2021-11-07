package logic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída ItemTest sloužící k otestování třídy Item.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */


class ItemTest {

    Item item1 = new Item("item1", "",true, "");
    Item item2 = new Item("item2", "",true, "");

    /**
     * Test jestli funguje vkládání itemu do itemu
     */
    @Test
    public void testInsertItemIntoItem() {
        item1.insertItem(item2);
        assertEquals("item2", item1.containedItem());
    }


    /**
     * Test jestli fungeje odstranění itemu z itemu
     */
    @Test
    public void testRemoveItemFromItem() {
        item1.insertItem(item2);
        assertEquals(item2, item1.getItemInItem("item2"));
        item1.removeItemInItem(item2.getName());
        assertNull(item1.getItemInItem("item2"));
    }
}