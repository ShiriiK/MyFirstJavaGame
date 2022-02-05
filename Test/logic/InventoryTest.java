package logic;

import logic.blueprints.Inventory;
import logic.blueprints.Item;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída InventoryTest sloužící k otestování třídy Invenotry.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */


class InventoryTest {

    Inventory inventory = new Inventory();
    Item item1 = new Item("item1", "",true, "");
    Item item2 = new Item("item2", "",true, "");
    Item item3 = new Item("item3", "",true, "");
    Item item4 = new Item("item4", "",true, "");
    Item item5 = new Item("item5", "",false, "");

    /**
     * Test jestli nejde přidat item na kapacitu inventáře.
     */
    @Test
    public void testAddItemWhenNoSpace() {
        assertTrue(inventory.isSpace());
        inventory.addItem(item1);
        assertTrue(inventory.isSpace());
        inventory.addItem(item2);
        assertTrue(inventory.isSpace());
        inventory.addItem(item3);
        inventory.addItem(item4);
        assertFalse(inventory.isSpace());
        assertNull(inventory.addItem(item4));
    }

    /**
     * Test vložnení itemu do inventáře
     */
    @Test
    public void testAddItem(){
        assertEquals(inventory.addItem(item1), item1);
        assertEquals(inventory.addItem(item2), item2);
        assertNull(inventory.addItem(item5)); // není pickable
        assertEquals(inventory.addItem(item3), item3);
        assertTrue(inventory.getContent().containsKey("item1"));
        assertTrue(inventory.getContent().containsKey("item2"));
        assertTrue(inventory.getContent().containsKey("item3"));
    }

    /**
     * Test odstranění itemu z inventáře
     */
    @Test
    public void testRemoveItem() {
        inventory.addItem(item1);
        inventory.addItem(item2);
        inventory.addItem(item3);
        assertEquals(item1, inventory.getItem("item1"));
        inventory.removeItem("item1");
        assertNull(inventory.getItem("item1"));
    }

    /**
     * Kontoluje jestli se vypisuje obsah inventáře správně.
     */
    @Test
    public void testDisplayInventory() {
        assertEquals("\nBatoh:", inventory.getInventory());
        inventory.addItem(item1);
        assertEquals("\nBatoh: item1", inventory.getInventory());
        inventory.addItem(item2);
        assertEquals("\nBatoh: item1, item2", inventory.getInventory());
        inventory.removeItem("item1");
        assertEquals("\nBatoh: item2", inventory.getInventory());
    }
}
