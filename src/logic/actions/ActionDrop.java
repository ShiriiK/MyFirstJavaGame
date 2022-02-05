package logic.actions;

import logic.*;
import logic.blueprints.Inventory;
import logic.blueprints.Item;
import logic.blueprints.Location;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro zahození itemu z inventáře.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-16
 */

public class ActionDrop implements IAction {
    private final Game game;
    private final String[] names = {"zahoď", "polož"};

    //Konstruktor
    public ActionDrop(Game game) {
        this.game = game;
    }

    /**
     * Metoda použitá pro identifikování platnosti příkazů.
     * @return možné názvy příkazů
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 2);
    }

    /**
     * Provádí příkaz drop - pokud je to možné, tak zahodí item z inventáře a dá ho do aktuální lokace.
     * @param parameters jeden parametr - jméno itemu
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {
        String d1 = Game.makeItLookGood1();
        String d2 = Game.makeItLookGood2();

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return d1 + "Nemůžeš zahazovat věci, dokud sis nevybral/a pohlaví." + d2;
        }
        if (phase == 1) {
            return d1 + "Nemůžeš zahazovat věci, dokud sis nevybral/a jméno." + d2;
        }
        if (parameters.length < 1) {
            return d1 + "Musíš mi říct, co chceš zahodit." + d2;
        }
        if (parameters.length > 1) {
            return d1 + "Nemůžeš zahodit víc věci najednou." + d2;
        }

        String itemName = parameters[0];
        Inventory inventory = gameState.getInventory();


        if (!inventory.getContent().containsKey(itemName)) {
            return d1 + "Nemůžeš zahodit něco, co nemáš." + d2;
        }

        Location currentLocation = gameState.getCurrentLocation();
        String locationName = currentLocation.getName();

        if (("žalář".equals(locationName) || "cela_na_levo".equals(locationName)
                || "cela_uprostřed".equals(locationName)
                || "cela_na_pravo".equals(locationName)) && "pochodeň".equals(itemName)) {
            return d1 + "Ne, to opravdu nedělej." + d2;
        }

        Item item = inventory.getItem(itemName);
        inventory.removeItem(itemName);
        currentLocation.addItem(item);
        return d1 + "Zahodil/a jsi " + itemName + "." + d2;
    }
}
