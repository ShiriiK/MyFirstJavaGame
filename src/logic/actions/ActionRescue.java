package logic.actions;

import gui.util.Constants;
import logic.Game;
import logic.blueprints.Location;
import saving_tue.Main;

import java.util.Arrays;

/**
 * Class implementing the command to save Tu and win the game.
 * @author Alena Kalivodová
 */

public class ActionRescue implements IAction {
    private final String[] names = {"rescue_tue"};

    /**
     * The method used to identify the validity of commands.
     * @return possible command names
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 1);
    }

    /**
     * Executes the rescue command - rescues Tu and ends the game.
     * @param parameters none
     */
    @Override
    public String execute(String[] parameters) {

        if (parameters.length >= 1) {
            return Constants.d1 + "Just type rescue_tue, that's all" + Constants.d2;
        }

        Location currentLocation = Main.game.getGameState().getCurrentLocation();

        if (!currentLocation.getName().equals("right_cell")) {
            return Constants.d1 + "In order to save Tu, you have to be where she's imprisoned." + Constants.d2;
        }

        Main.game.setHappyEnd(true);
        Main.game.setTheEnd(true);
        return Main.game.epilog() + "Game ended.";
    }
}
