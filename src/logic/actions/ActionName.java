package logic.actions;

import gui.util.Constants;
import logic.*;
import logic.factories.GameAnswersFactory;
import logic.factories.WeaponFactory;
import saving_tue.Main;
import java.util.Arrays;

/**
 * Class implementing the player name setting command.
 * @author Alena Kalivodová
 */

public class ActionName implements IAction {
    private final String[] names = {"name"};

    /**
     * The method used to identify the validity of commands.
     * @return possible command names
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 1);
    }

    /**
     * Executes the name command - sets the player's name.
     * @param parameters one parameter - player name
     */
    @Override
    public String execute(String... parameters) {

        GameState gameState = Main.game.getGameState();
        PhaseChecker.firstChecker();
        PhaseChecker.secondChecker();

        if (parameters.length == 0) {
            return Constants.d1 + "What do you want to be called?" + Constants.d2;
        }
        if (parameters.length > 1) {
            return Constants.d1 + "If you want a multi-word name, use an underscore." + Constants.d2;
        }

        String playerName = Main.game.getGameState().getPlayer().getPlayerName();

        if (playerName != null) {
            return Constants.d1 + "You've already chosen a name." + Constants.d2;
        }

        String name = parameters[0];
        String partnerName = gameState.getPartner().getPartnerName();
        gameState.setPhase(3);
        gameState.getPlayer().setPlayerName(name);
        WeaponFactory.insertWeapons();

        return  Constants.d1 + "Name set to: " + name + Constants.d2 + "\n" + GameAnswersFactory.name(name,partnerName, gameState.getCurrentLocation());
    }
}
