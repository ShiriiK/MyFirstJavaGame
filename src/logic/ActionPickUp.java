package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro odebrání itemů z lokace a jejich následnímu vložení do inventáře.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-16
 */

public class ActionPickUp implements IAction {
    private Game game;
    private String[] names = {"vezmi", "seber", "zdvihni"};

    /**
     * Konstuktor
     *
     * @param game hra ve které bude příkaz vykonán
     */
    public ActionPickUp(Game game) {
        this.game = game;
    }

    /**
     * Metoda použitá pro identifikování platnosti příkazů.
     *
     * @return možné názvy příkazů
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 3);
    }

    /**
     * Provádí příkaz pickup - sebere item.
     *
     * @param parameters jeden parametr - název itemu
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {
        String d1 = Game.makeItLookGood1();
        String d2 = Game.makeItLookGood2();

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return d1 + "Nejdřív si nastav pohlaví." + d2;
        }
        if (phase == 1) {
            return d1 + "Nastav si jméno a až pak si sbírej věci." + d2;
        }
        if (parameters.length < 1) {
            return d1 + "A co chceš sebrat?" + d2;
        }
        if (parameters.length > 1) {
            return d1 + "Nemůžeš sbírat víc věcí najednou, vyber si jednu." + d2;
        }

        String itemName = parameters[0];
        Location currentLocation = gameState.getCurrentLocation();

        if (currentLocation.getItem(itemName) == null) {
            return d1 + "Nic takového tu není." + d2;
        }

        Item item = currentLocation.getItem(itemName);

        if (!item.isPickable()) {
            return d1 + "Tohle si vzít nemůžeš." + d2;
        }
        if (gameState.getInventory().addItem(item) == null) {
            return d1 + "Nemáš už v batohu volné místo." + d2;
        }
        if (currentLocation.getNpc("stráž") != null && itemName.equals("pochodeň")) {
            return "\nStráž: Nesahej na to a vypadni!" + d2;
        }

        currentLocation.removeItem(itemName);
        return d1 + "Sebral/a jsi " + itemName + "." + d2;
    }
}
