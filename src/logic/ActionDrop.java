package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro zahození itemu z inventáře.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class ActionDrop implements IAction {
    private Game game;
    private String[] names = {"zahodit", "položit"};

    /**
     * Konstuktor
     *
     * @param game hra ve které bude příkaz vykonán
     */
    public ActionDrop(Game game) {
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
     * Provádí příkaz drop - pokud je to možné, tak zahodí item z inventáře a dá ho do aktuální lokace.
     *
     * @param parameters jeden parametr - jméno itemu
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return "\nNemůžeš zahazovat věci, dokud sis nevybral/a pohlaví.";
        }
        if (phase == 1) {
            return "\nNemůžeš zahazovat věci, dokud sis nevybral/a jméno.";
        }
        if (parameters.length < 1) {
            return "\nMusíš mi říct, co chceš zahodit.";
        }
        if (parameters.length > 1) {
            return "\nNemůžeš zahodit víc věci najednou.";
        }

        String itemName = parameters[0];
        Inventory inventory = gameState.getInventory();

        if (!inventory.containsItem(itemName)) {
            return "\nNemůžeš zahodit něco, co nemáš.";
        }

        Location currentLocation = gameState.getCurrentLocation();
        String locationName = currentLocation.getName();

        if (("žalář".equals(locationName) || "cela1".equals(locationName)
                || "cela2".equals(locationName)
                || "cela3".equals(locationName)) && "pochodeň".equals(itemName)) {
            return "\nNe, to tě opravdu nenechám udělat.";
        }

        Item item = inventory.getItem(itemName);
        inventory.removeItem(itemName);
        currentLocation.addItem(item);
        return "\nZahodil/a jsi " + itemName + ".";
    }
}