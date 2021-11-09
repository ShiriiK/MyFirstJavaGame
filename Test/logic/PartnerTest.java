package logic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída PartnerTest sloužící k otestování třídy Partner.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

class PartnerTest {

    Weapon weapon = new Weapon("weapon", "",2, false,"");
    Partner partner = new Partner("Yrsa", weapon, 20, 20);

    /**
     * Test jestli má partner správně nastavené staty.
     */
    @Test
    public void testPartner() {
        assertEquals(
                "\nJméno: Yrsa\n" +
                        "Zbraň: weapon\n" +
                        "Životy: 20.0\n" +
                        "Síla: 40.0", partner.getPartner());
    }
}