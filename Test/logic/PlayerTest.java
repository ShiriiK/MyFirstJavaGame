package logic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída PlayerTest sloužící k otestování třídy Player.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

class PlayerTest {

    Weapon weapon = new Weapon("weapon", "",2, false,"",0,0,0,0);
    Player player = new Player("Haha", "male", weapon, 20, 20, null);

    /**
     * Test jestli má hráč správně nastavené staty.
     */
    @Test
    public void testPlayer() {
        assertEquals(
                "\nPohlaví: male\n" +
                        "Jméno: Haha\n" +
                        "Zbraň: weapon\n" +
                        "Životy: 20.0\n" +
                        "Síla: 40.0", player.getPlayer());
    }
}