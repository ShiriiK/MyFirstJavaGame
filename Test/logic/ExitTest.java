package logic;

import logic.blueprints.Exit;
import logic.blueprints.Location;
import logic.blueprints.Npc;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída ExitTest sloužící k otestování třídy Exit.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */
class ExitTest {

    Location location1 = new Location("location1", "","ll", 2);

    Exit location1Exit = new Exit(location1);

    Npc npc1 = new Npc("npc1", "",true,2.5, 1, false, null, null);
    Npc npc2 = new Npc("npc2", "",true,2.5, 2.0, false, null, null);

    /**
     * Test správnosti fungování operací s watchingNpc.
     */
    @Test
    public void testWatchingNpcs(){
        location1Exit.insertNpc(npc1);
        assertEquals(npc1,location1Exit.containsNpc(npc1));
        location1Exit.removeWatchingNpc(npc1);
        assertNull(location1Exit.containsNpc(npc1));
    }

    /**
     * Test získávání informací o poškození.
     */
    @Test
    public void testDamage(){
        location1.addNpc(npc1);
        location1.addNpc(npc2);
        assertEquals(3, location1Exit.getDamage());
        assertEquals("Ztratil/a jsi 3.0 zdraví.", location1Exit.getDamageMessage());
    }
}