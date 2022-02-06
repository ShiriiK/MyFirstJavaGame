package logic.actions;

import gui.util.Constants;
import logic.*;
import logic.blueprints.Inventory;
import logic.blueprints.Item;
import logic.blueprints.Location;
import logic.blueprints.Npc;
import logic.factories.ActionGiveConditionsFactory;
import saving_tue.Main;

import java.util.Arrays;

/**
 * Class that implements the command for giving item to npc.
 * @author Alena Kalivodová
 */

public class ActionGive implements IAction {
    private final String[] names = {"give", "offer"};

    /**
     * The method used to identify the validity of commands.
     * @return possible command names
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 2);
    }

    /**
     * Executes the give command - gives an item from the inventory to an npc.
     * @param parameters two parameters - the name of the npc and then the item to get
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = Main.game.getGameState();
        PhaseChecker.basicChecker();

        if (parameters.length < 2) {
            return Constants.d1 + "You also have to write down who you want to give something to and what that something is." + Constants.d2;
        }
        if (parameters.length > 2) {
            return Constants.d1 + "You can't give more than one thing at a time. Pick one." + Constants.d2;
        }

        String itemName = parameters[1];
        Inventory inventory = gameState.getInventory();

        if (!inventory.getContent().containsKey(itemName)) {
            return Constants.d1 + "You can't give someone something you don't have." + Constants.d2;
        }

        String npcName = parameters[0];


        if (Main.game.getGameState().getCurrentLocation().getNpc(npcName) == null) {
            return Constants.d1 + "You can't give things to someone who's not here." + Constants.d2;
        }

        return ActionGiveConditionsFactory.confirmFeasibility(npcName, itemName);
    }
}
