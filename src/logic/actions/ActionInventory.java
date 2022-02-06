package logic.actions;

import gui.util.Constants;
import saving_tue.Main;

import java.util.Arrays;

/**
 * Class that implements a command to display the content of the inventory.
 * @author Alena Kalivodová
 */

public class ActionInventory implements IAction {
    private final String[] names = {"inventory"};

    /**
     * The method used to identify the validity of commands.
     * @return possible command names
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 1);
    }

    /**
     * Executes the inventory command - displays the contents of the inventory.
     * @param parameters none
     */
    @Override
    public String execute(String[] parameters) {

        PhaseChecker.basicChecker();

        if (parameters.length >= 1) {
            return Constants.d1 + "Just write inventory." + Constants.d2;
        }

        return Main.game.getGameState().getInventory().getInventory();
    }
}
