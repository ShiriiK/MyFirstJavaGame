package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro odebrání itemů z lokace a jejich následnímu vložení do inventáře.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class ActionPickUp implements IAction {
    private Game game;
    private String[] names = {"vzít", "sebrat"};

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
        return Arrays.copyOf(names, 2);
    }

    /**
     * Provádí příkaz pickup - sebere item.
     *
     * @param parameters jeden parametr - název itemu
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return "\nNejdřív si nastav pohlaví.";
        }
        if (phase == 1) {
            return "\nNastav si jméno a až pak si sbírej.";
        }
        if (parameters.length < 1) {
            return "\nA co chceš sebrat?";
        }
        if (parameters.length > 1) {
            return "\nNemůžeš sbírat víc věcí najednou, vyber si jednu.";
        }

        String itemName = parameters[0];
        Location currentLocation = gameState.getCurrentLocation();

        if (currentLocation.getItem(itemName) == null) {
            return "\nNic takového tu není.";
        }

        Item item = currentLocation.getItem(itemName);

        if (!item.isPickable()) {
            return "\nTohle si vzít nemůžeš.";
        }
        if (gameState.getInventory().addItem(item) == null) {
            return "\nNemáš už v batohu volné místo.";
        }
        if (currentLocation.getNpc("stráž") != null && itemName.equals("pochodeň")) {
            return "\nStráž: Nesahej na to a vypadni!";
        }

        currentLocation.removeItem(itemName);
        return "\nSebral/a jsi " + itemName + ".";
    }
}
