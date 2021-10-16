package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro vypsaná itemů, npc, zbraní a exitů z aktuální lokace.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class ActionLook implements IAction {
    private Game game;
    private String[] names = {"rozhlédnout_se", "rozhlédnout"};

    /**
     * Konstuktor
     *
     * @param game hra ve které bude příkaz vykonán
     */
    public ActionLook(Game game) {
        this.game = game;
    }

    /**
     * Metoda použitá pro identifikování platnosti příkazů.
     *
     * @return možné názvy příkazů
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 2);
    }

    /**
     * Provádí příkaz look - zobrazí, co hráč v okolí zahlédne.
     *
     * @param parameters žádný
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return "\nNastav si pohlaví a pak až pak se rozhlížej.";
        }
        if (phase == 1) {
            return "\nNastav si jméno a pak až pak se rozhlížej.";
        }
        if (parameters.length >= 1) {
            return "\nStačí napsat rozhlédnout_se";
        }

        Location currentLocation = gameState.getCurrentLocation();
        return currentLocation.npcDescription() +
                currentLocation.itemDescription() +
                currentLocation.exitsDescription() +
                currentLocation.weaponDescription() + "\n";
    }
}
