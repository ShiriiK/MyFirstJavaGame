package logic.actions;

import gui.util.Constants;
import saving_tue.Main;

import java.util.Arrays;

/**
 * The class that implements the exit command.
 * @author Alena Kalivodová
 */

public class ActionTerminate implements IAction {
    private final String[] names = {"end"};

    /**
     * The method used to identify the validity of commands.
     * @return possible command names
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 1);
    }

    /**
     * Executes the end command - ends the game.
     * @param parameters none
     */
    @Override
    public String execute(String[] parameters) {

        if (parameters.length >= 1) {
            return Constants.d1 + "Just write end, that's all." + Constants.d2;
        }

        Main.game.setTheEnd(true);
        return Constants.d1 + "Game over." + Constants.d2;
    }
}
