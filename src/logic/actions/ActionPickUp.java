package logic.actions;

import gui.util.Constants;
import logic.*;
import logic.blueprints.Item;
import logic.blueprints.Location;
import saving_tue.Main;

import java.util.Arrays;

/**
 * Class implementing a command for removing items from a location and putting them into the inventory.
 * @author Alena Kalivodová
 */

public class ActionPickUp implements IAction {
    private final String[] names = {"take", "pickup"};

    /**
     * The method used to identify the validity of commands.
     * @return possible command names
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 2);
    }

    /**
     * Executes the pickup command - picks up the item.
     * @param parameters one parameter - item name
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = Main.game.getGameState();
        PhaseChecker.basicChecker();

        if (parameters.length < 1) {
            return Constants.d1 + "What do you want to take?" + Constants.d2;
        }
        if (parameters.length > 1) {
            return Constants.d1 + "You can't collect more than one thing at a time, pick one." + Constants.d2;
        }

        String itemName = parameters[0];
        Location currentLocation = gameState.getCurrentLocation();

        if (currentLocation.getItem(itemName) == null) {
            return Constants.d1 + "There's no such thing." + Constants.d2;
        }

        Item item = currentLocation.getItem(itemName);

        if (!item.isPickable()) {
            return Constants.d1 + "You can't pickup this." + Constants.d2;
        }
        if (gameState.getInventory().addItem(item) == null) {
            return Constants.d1 + "There's no more room in your inventory." + Constants.d2;
        }
        if (currentLocation.getNpc("guard") != null && itemName.equals("torch")) {
            return "\nGuard: Don't touch it and get out!" + Constants.d2;
        }

        currentLocation.removeItem(itemName);
        return Constants.d1 + "You picked up " + itemName + "." + Constants.d2;
    }
}
