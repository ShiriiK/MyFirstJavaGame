package logic.actions;

import gui.util.Constants;
import logic.GameState;
import saving_tue.Main;

import java.util.Arrays;

/**
 * Class that implements the command to display the partner's stats.
 * @author Alena Kalivodová
 */

public class ActionPartner implements IAction {
    private final String[] names = {"partner"};

    /**
     * The method used to identify the validity of commands.
     * @return possible command names
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 1);
    }

    /**
     * Executes the partner command - displays the partner's stats.
     * @param parameters none
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = Main.game.getGameState();
        PhaseChecker.basicChecker();
        PhaseChecker.advancedChecker();

        if (parameters.length >= 1) {
            return Constants.d1 + "Just type partner." + Constants.d2;
        }

        return Constants.d1 + gameState.getPartner().getPartner() + Constants.d2;
    }
}
