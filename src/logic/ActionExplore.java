package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro prozkoumání předmětu.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */


public class ActionExplore implements IAction {
    private Game game;
    private String[] names = {"prozkoumat", "prohledat"};

    /**
     * Konstuktor
     *
     * @param game hra ve které bude příkaz vykonán
     */
    public ActionExplore(Game game) {
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
     * Provádí příkaz explore - prohledá předmět, vypíše jeho popis a pokud se v něm nachází item,
     * tak ho přesune do aktuální lokace.
     *
     * @param parameters jeden parametr - jméno věci, která má být prozkoumána
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return "\nNemůžeš prozkoumávat věci, dokud si nenastavíš pohlaví.";
        }
        if (phase == 1) {
            return "\nnNemůžeš prozkoumávat věci, dokud si nenastavíš jméno.";
        }
        if (parameters.length < 1) {
            return "\nMusíš mi říct, co by si chtěl/a prozkoumat.";
        }
        if (parameters.length > 1) {
            return "\nNemůžeš prozkoumávat víc věcí najednou.";
        }

        String itemName = parameters[0];
        Location currentLocation = gameState.getCurrentLocation();

        if (currentLocation.getItem(itemName) == null) {
            return "\nNic takového tu není.";
        }

        Item item = currentLocation.getItem(itemName);
        String foundName = currentLocation.getItem(itemName).containedItem();
        Item found = currentLocation.getItem(itemName).getItemInItem(foundName);

        if ("velký_strom".equals(itemName) && "taška".equals(item.containedItem())) {
            item.removeItemInItem(foundName);
            currentLocation.addItem(found);
            return "\nNašel/a jsi: " + foundName;
        }

        Inventory inventory = gameState.getInventory();

        if (item.containedItem() != null) {
            if ("truhla".equals(itemName) && !inventory.containsItem("univerzální_klíč")) {
                return "\nTruhla je zamčená, ani brutální síla nepomáhá v jejím otevření.";
            }
            item.removeItemInItem(foundName);
            currentLocation.addItem(found);
            return "\nNašel/a jsi: " + foundName + "\n" +
                    item.getDescription();
        }

        return "\n" + item.getDescription();
    }
}
