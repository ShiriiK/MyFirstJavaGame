package logic.actions;

import gui.util.Constants;
import logic.*;
import logic.blueprints.Player;
import logic.factories.PartnerFactory;
import saving_tue.Main;

import java.util.Arrays;

/**
 * Class implementing the gender setting command.
 * @author Alena Kalivodová
 */

public class ActionGender implements IAction {
    private final String[] names = {"gender"};

    /**
     * The method used to identify the validity of commands.
     * @return possible command names
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 1);
    }

    /**
     * Executes the gender command - sets the player's gender and with it the base stats and partner.
     * @param parameters one parameter - male or female
     */
    @Override
    public String execute(String[] parameters) {

        if (parameters.length == 0) {
            return Constants.d1 + "Which gender do you want to choose? You have a choice between male and female." + Constants.d2;
        }
        if (parameters.length > 1) {
            return Constants.d1 + "You can only have one gender." + Constants.d2;
        }

        GameState gameState = Main.game.getGameState();
        Player player = gameState.getPlayer();
        String playerGender = player.getPlayerGender();

        if (playerGender != null) {
            return Constants.d1 + "Have you already chosen your gender." + Constants.d2;
        }

        String gender = parameters[0];

        if (!("male".equals(gender)) && !("female".equals(gender))) {
            return Constants.d1 + "I'm sorry to disappoint you, but the game only knows two genders this way." + Constants.d2;
        }

        player.setPlayerGender(gender);

        if ("male".equals(gender)) {
            PartnerFactory.setYrsa();
        }
        if ("female".equals(gender)) {
            PartnerFactory.setTorsten();
        }

        gameState.setPhase(1);
        return  Constants.d1 + "Gender set to: " + gender + Constants.d2 +
                Constants.d1 + "Now choose a race." + Constants.d2;

    }
}
