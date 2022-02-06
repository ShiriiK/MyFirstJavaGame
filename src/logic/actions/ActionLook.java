package logic.actions;

import gui.util.Constants;
import logic.blueprints.Location;
import saving_tue.Main;

import java.util.Arrays;

/**
 * Class implementing a command for listing items, npcs, weapons and exits from the current location.
 * @author Alena Kalivodová
 */

public class ActionLook implements IAction {
    private final String[] names = {"look"};

    /**
     * The method used to identify the validity of commands.
     * @return possible command names
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 1);
    }

    /**
     * Performs the look command - displays what the player sees in the environment.
     * @param parameters none
     */
    @Override
    public String execute(String[] parameters) {

        PhaseChecker.basicChecker();

        if (parameters.length >= 1) {
            return Constants.d1 + "Just type look." + Constants.d2;
        }

        Location currentLocation = Main.game.getGameState().getCurrentLocation();
        return  Constants.d1 + currentLocation.npcDescription() +
                currentLocation.itemDescription() +
                currentLocation.exitsDescription() +
                currentLocation.weaponDescription() + Constants.d2;
    }
}
