package logic.actions;

import gui.util.Constants;
import logic.GameState;
import saving_tue.Main;

import java.util.Arrays;

/**
 * Class implementing a command to display player stats.
 * @author Alena Kalivodová
 */

public class ActionPlayer implements IAction {
    private final String[] names = {"player"};

    /**
     * The method used to identify the validity of commands.
     * @return possible command names
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 1);
    }

    /**
     * Executes the player command - sets the player name.
     * @param parameters none
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = Main.game.getGameState();
        PhaseChecker.basicChecker();
        PhaseChecker.advancedChecker();

        if (parameters.length >= 1) {
            return Constants.d1 + "Just write the player." + Constants.d2;
        }

        return Constants.d1 + gameState.getPlayer().getPlayersStats() + Constants.d2;
    }
}
