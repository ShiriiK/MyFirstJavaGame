package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro prozkoumání předmětu.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-16
 */


public class ActionExplore implements IAction {
    private final Game game;
    private final String[] names = {"prozkoumej", "prohledej"};

    //Konstruktor
    public ActionExplore(Game game) {
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
     * Provádí příkaz explore - prohledá předmět, vypíše jeho popis a pokud se v něm nachází item,
     * tak ho přesune do aktuální lokace.
     * @param parameters jeden parametr - jméno věci, která má být prozkoumána
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {
        String d1 = Game.makeItLookGood1();
        String d2 = Game.makeItLookGood2();

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return d1 + "Nemůžeš prozkoumávat věci, dokud si nenastavíš pohlaví." + d2;
        }
        if (phase == 1) {
            return d1 + "Nemůžeš prozkoumávat věci, dokud si nenastavíš jméno." + d2;
        }
        if (parameters.length < 1) {
            return d1 + "Musíš mi říct, co by si chtěl/a prozkoumat." + d2;
        }
        if (parameters.length > 1) {
            return d1 + "Nemůžeš prozkoumávat víc věcí najednou." + d2;
        }

        String itemName = parameters[0];
        Inventory inventory = gameState.getInventory();
        Item itemInv = inventory.getItem(itemName);


        if (inventory.getItem(itemName) != null) {
            return d1 + itemInv.getDescription() + d2;
        }

        Location currentLocation = gameState.getCurrentLocation();
        Item item = currentLocation.getItem(itemName);

        if (currentLocation.getItem(itemName) == null) {
            return d1 + "Nic takového tu není a ani nemáš nic takového u sebe." + d2;
        }


        String foundName = currentLocation.getItem(itemName).containedItem();
        Item found = currentLocation.getItem(itemName).getItemInItem(foundName);

        if ("velký_strom".equals(itemName) && "taška".equals(item.containedItem())) {
            item.removeItemInItem(foundName);
            currentLocation.addItem(found);
            return d1 + "Našel/a jsi: " + foundName + d2;
        }

        if (item.containedItem() != null) {
            if ("truhla".equals(itemName) && !inventory.getContent().containsKey("univerzální_klíč")) {
                return d1 + "Truhla je zamčená, ani brutální síla nepomáhá v jejím otevření." + d2;
            }
            item.removeItemInItem(foundName);
            currentLocation.addItem(found);
            return d1 + "Našel/a jsi: " + foundName + "\n" +
                    item.getDescription() + d2;
        }

        return d1 + item.getDescription() + d2;
    }
}
