package logic.actions;

import gui.util.Constants;
import logic.Game;
import logic.GameState;
import logic.blueprints.Player;
import logic.blueprints.Race;
import logic.factories.RaceFactory;
import saving_tue.Main;

import java.util.Arrays;

/**
 * Class implementing the gender setting command.
 * This class is part of a simple text adventure game with a graphical interface.
 * @author Alena Kalivodová
 */

public class ActionRace implements IAction {
    private final String[] names = {"race"};

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

        PhaseChecker.firstChecker();

        if (parameters.length == 0) {
            return Constants.d1 + "Which race do you want to choose?" + Constants.d2;
        }
        if (parameters.length > 1) {
            return Constants.d1 + "You can only have one race." + Constants.d2;
        }

        GameState gameState = Main.game.getGameState();
        Player player = gameState.getPlayer();
        Race playerRace = player.getRace();

        if (playerRace != null) {
            return Constants.d1 + "You've already chosen your race." + Constants.d2;
        }

        String raceName = parameters[0];
        Race chosenRace = null;

        for (Race race : RaceFactory.races){
            if(raceName.equals(race.getName().toLowerCase())){
                chosenRace = race;
                break;
            }
        }

        if(chosenRace != null){
            player.setRace(chosenRace);

            gameState.setPhase(2);
            return Constants.d1 + "Race set to: " + raceName + Constants.d2 + "\n" +
                    "You slowly open your eyes and see the blurred silhouette of a giantess in front of you." +
                    "As you struggle to sit up, she turns to you and rushes to help you.\n" +
                    "???: Good morning. How are you feeling? Looks like Tue's been imprisoned. What on earth happened in that town?" +
                    " Can you hear me? Do you even remember your name?\n"
                    + Constants.d1 + "Choose your name." + Constants.d2;
        } else {
            return Constants.d1 + "I'm sorry to disappoint you, but we don't have that race here." + Constants.d2;
        }
    }
}

