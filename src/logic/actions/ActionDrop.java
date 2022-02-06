package logic.actions;

import gui.util.Constants;
import logic.*;
import logic.blueprints.Inventory;
import logic.blueprints.Item;
import logic.blueprints.Location;
import saving_tue.Main;

import java.util.Arrays;

/**
 * Class implementing the command to discard an item from the inventory.
 * @author Alena Kalivodová
 */

public class ActionDrop implements IAction {
    private final String[] names = {"drop", "throw_away"};

    /**
     * The method used to identify the validity of commands.
     * @return possible command names
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 2);
    }

    /**
     * Executes the drop command - if possible, drops the item from the inventory and puts it in the current location.
     * @param parameters one parameter - item's name
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = Main.game.getGameState();
        PhaseChecker.basicChecker();

        if (parameters.length < 1) {
            return Constants.d1 + "What do you want to drop?" + Constants.d2;
        }
        if (parameters.length > 1) {
            return Constants.d1 + "You can't drop more than one thing at a time." + Constants.d2;
        }

        String itemName = parameters[0];
        Inventory inventory = gameState.getInventory();


        if (!inventory.getContent().containsKey(itemName)) {
            return Constants.d1 + "You can't drop something you don't have." + Constants.d2;
        }

        Location currentLocation = gameState.getCurrentLocation();
        String locationName = currentLocation.getName();

        if (("dungeon".equals(locationName) || "left_cell".equals(locationName)
                || "middle_cell".equals(locationName)
                || "right_cell".equals(locationName)) && "torch".equals(itemName)) {
            return Constants.d1 + "No, don't do that." + Constants.d2;
        }

        Item item = inventory.getItem(itemName);
        inventory.removeItem(itemName);
        currentLocation.addItem(item);
        return Constants.d1 + "You've dropped " + itemName + "." + Constants.d2;
    }
}
