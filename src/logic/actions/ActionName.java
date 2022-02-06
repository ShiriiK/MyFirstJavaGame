package logic.actions;

import gui.util.Constants;
import logic.*;
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

        return  Constants.d1 + "Name set to: " + name + Constants.d2 + "\n"+
                name + ": Ehhh? I'm " + name + ", right? Is that you, Gorm?\n" +
                "Gorm: Yeah, it's me..\n" +
                name + ": Where is " + partnerName + " a Tue?\n" +
                partnerName + ": They've dragged her into some kind of underground prison where they'll keep her until they execute her." +
                "This place really brings us nothing but misery. First Thorfinn and now Tue..." +
                "If she wasn't so soft-hearted, nothing would have happened to her.\n" +
                "Gorm: Slow down a little. You're both doing pretty badly so far. Recover slowly, " +
                "get your weapons and then go help her. I won't be of any use to you in a fight, " +
                "but in the room behind the forge, you can get a weapon that suits you, " +
                "except for the ones I'm working on so far..\n" +
                partnerName + ": I'll join you in a minute, I'm just gonna rest for a while. Then go do your chores. " +
                "I'll join you as soon as you leave the field.\n" +
                gameState.getCurrentLocation().longDescription();
    }
}
