package logic.actions;

import logic.Game;
import logic.GameState;
import logic.blueprints.Location;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro vypsaná itemů, npc, zbraní a exitů z aktuální lokace.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-16
 */

public class ActionLook implements IAction {
    private final Game game;
    private final String[] names = {"rozhlédni_se"};

    //Konstruktor
    public ActionLook(Game game) {
        this.game = game;
    }

    /**
     * Metoda použitá pro identifikování platnosti příkazů.
     * @return možné názvy příkazů
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 1);
    }

    /**
     * Provádí příkaz look - zobrazí, co hráč v okolí zahlédne.
     * @param parameters žádný
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {
        String d1 = Game.makeItLookGood1();
        String d2 = Game.makeItLookGood2();

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return d1 + "Nastav si pohlaví a pak až pak se rozhlížej." + d2;
        }
        if (phase == 1) {
            return d1 + "Nastav si jméno a pak až pak se rozhlížej." + d2;
        }
        if (parameters.length >= 1) {
            return d1 + "Stačí napsat rozhlédnout_se" + d2;
        }

        Location currentLocation = gameState.getCurrentLocation();
        return  d1 + currentLocation.npcDescription() +
                currentLocation.itemDescription() +
                currentLocation.exitsDescription() +
                currentLocation.weaponDescription() + d2;
    }
}
