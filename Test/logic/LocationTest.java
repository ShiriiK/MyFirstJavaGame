package logic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída LocationTest sloužící k otestování třídy Location.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

class LocationTest {

    Location location1 = new Location("location1", "","ll",1);
    Location location2 = new Location("location2", "","ll",1);
    Location location3 = new Location("location3", "","ll",1);
    Location location4 = new Location("location4", "","ll",1);

    Exit location1Exit = new Exit(location1);
    Exit location2Exit = new Exit(location2);
    Exit location3Exit = new Exit(location3);
    Exit location4Exit = new Exit(location4);

    Npc npc1 = new Npc("npc1", "",true,2.5, 1, false, null, null);
    Npc npc2 = new Npc("npc2", "",true,2.5, 2.0, false, null, null);

    Item item1 = new Item("item1", "",true, "ii");
    Item item2 = new Item("item2", "",false, "ii");

    Weapon weapon1 = new Weapon("weapon1", "",2, false,"");
    Weapon weapon2 = new Weapon("weapon2", "",2, false,"");

    /**
     * Kontroluje jestli vrací infromace o tom, kde se hráč nachází a pokud je tamm poprvé, tak i popis lokace.
     */
    @Test
    public void testDescription() {
        assertEquals("\nAktuální lokace: " + location1.getName() + "\n" +
                "Popis lokace: " + "ll", location1.longDescription());
        assertEquals("Aktuální lokace: " + "location1", location1.longDescription());
    }

    /**
     * Test týkající se npc.
     */
    @Test
    public void testNpc() {
        assertNull(location1.getNpc("npc1"));
        assertEquals(0, location1.npcAttack());

        location1.addNpc(npc1);
        assertEquals(1.0, location1.npcAttack());
        assertEquals(npc1, location1.getNpc("npc1"));
        assertNull(location1.getNpc("npc2"));

        location1.addNpc(npc2);
        assertEquals(3.0, location1.npcAttack());

        location1.removeNpc("npc2");
        assertNull(location1.getNpc("npc2"));
    }



    /**
     * Test týkající se exitů.
     */
    @Test
    public void testExit() {
        assertNull(location1.getExit("location2"));

        location1.addExit(location2Exit);
        assertEquals(location2Exit, location1.getExit("location2"));

        location1.addExit(location3Exit);
        location1.addExit(location4Exit);
        assertEquals(location3Exit, location1.getExit("location3"));
        assertEquals(location4Exit, location1.getExit("location4"));

        location2.addExit(location1Exit);
        location2.addExit(location3Exit);
        assertEquals(location1Exit, location2.getExit("location1"));
        assertEquals(location3Exit, location2.getExit("location3"));
    }

    /**
     * Test týkající se itemů.
     */
    @Test
    public void testItem() {
        assertNull(location1.getItem("item1"));

        location1.addItem(item1);
        assertEquals(item1, location1.getItem("item1"));
        location1.addItem(item2);
        assertEquals(item2, location1.getItem("item2"));

        location1.removeItem(item1.getName());
        assertNull(location1.getItem(item1.getName()));
    }


    /**
     * Test týkající se zbraní.
     */
    @Test
    public void testWeapon() {
        assertNull(location1.getWeapon("weapon1"));

        location1.addWeapon(weapon1);
        assertEquals(weapon1, location1.getWeapon("weapon1"));

        location1.addWeapon(weapon2);
        assertEquals(weapon2, location1.getWeapon("weapon2"));

        location1.removeWeapon("weapon1");
        assertNull(location1.getWeapon("weapon1"));
    }
}