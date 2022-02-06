package logic.actions;

import gui.util.Constants;
import logic.*;
import logic.blueprints.Inventory;
import logic.blueprints.Item;
import logic.blueprints.Location;
import saving_tue.Main;

import java.util.Arrays;

/**
 * A class that implements a command to explore an object.
 * @author Alena Kalivodová
 */


public class ActionExplore implements IAction {
    private final String[] names = {"explore", "search"};

    /**
     * The method used to identify the validity of commands.
     * @return possible command names
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 2);
    }

    /**
     * Executes the explore command - searches the item, lists its description and if there is an item in it,
     * it moves it to the current location.
     * @param parameters one parameter - the name of the item to be explored
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = Main.game.getGameState();
        PhaseChecker.basicChecker();

        if (parameters.length < 1) {
            return Constants.d1 + "You have to tell me what you'd like to explore." + Constants.d2;
        }
        if (parameters.length > 1) {
            return Constants.d1 + "You can't explore more than one thing at a time." + Constants.d2;
        }

        String itemName = parameters[0];
        Inventory inventory = gameState.getInventory();
        Item itemInv = inventory.getItem(itemName);


        if (inventory.getItem(itemName) != null) {
            return Constants.d1 + itemInv.getDescription() + Constants.d2;
        }

        Location currentLocation = gameState.getCurrentLocation();
        Item item = currentLocation.getItem(itemName);

        if (currentLocation.getItem(itemName) == null) {
            return Constants.d1 + "There's no such thing here." + Constants.d2;
        }


        String foundName = currentLocation.getItem(itemName).containedItem();
        Item found = currentLocation.getItem(itemName).getItemInItem(foundName);

        if ("huge_tree".equals(itemName) && "bag".equals(item.containedItem())) {
            item.removeItemInItem(foundName);
            currentLocation.addItem(found);
            return Constants.d1 + "You've found: " + foundName + Constants.d2;
        }

        if (item.containedItem() != null) {
            if ("chest".equals(itemName) && !inventory.getContent().containsKey("master_key")) {
                return Constants.d1 + "The chest is locked, even brute force doesn't help in opening it." + Constants.d2;
            }
            item.removeItemInItem(foundName);
            currentLocation.addItem(found);
            return Constants.d1 + "You've found: " + foundName + "\n" +
                    item.getDescription() + Constants.d2;
        }

        return Constants.d1 + item.getDescription() + Constants.d2;
    }
}
