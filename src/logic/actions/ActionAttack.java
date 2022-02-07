package logic.actions;

import gui.util.Constants;
import logic.*;
import logic.blueprints.Location;
import logic.blueprints.Npc;
import logic.blueprints.Player;
import saving_tue.Main;

import java.util.Arrays;

/**
 * A class that implements a command to attack an enemy.
 * @author Alena Kalivodová
 */

public class ActionAttack implements IAction {
    private final String[] names = {"attack"};

    /**
     * The method used to identify the validity of commands.
     * @return possible command names
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 1);
    }

    /**
     * Executes the attack command - attacks the npc (if possible), if the npc survives, it attacks back.
     * @param parameters one parameter - the name of the npc the player is attacking
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = Main.game.getGameState();

        PhaseChecker.basicChecker();
        PhaseChecker.advancedChecker();

        if (parameters.length < 1) {
            return Constants.d1 + "Who do you want to attack?" + Constants.d2;
        }
        if (parameters.length > 1) {
            return Constants.d1 + "You can't attack multiple enemies at once." + Constants.d2;
        }

        String npcName = parameters[0];
        Location currentLocation = gameState.getCurrentLocation();

        if (currentLocation.getNpc(npcName) == null) {
            return Constants.d1 + "You can't attack someone who's not here." + Constants.d2;
        }

        Npc attackedNpc = currentLocation.getNpc(npcName);

        if (attackedNpc.isFriendly()) {
            return Constants.d1 + "There's no reason to attack this npc." + Constants.d2;
        }

        Player player = gameState.getPlayer();
        int playerStr = player.getStr();
        int npcHp = attackedNpc.getHp();

        if (npcHp <= playerStr) {
            currentLocation.removeNpc(npcName);
            return Constants.d1 + "You killed: " + npcName + "." + Constants.d2;
        }

        int playerHp = player.getHp();
        int npcStr = attackedNpc.getStr();
        attackedNpc.setHp(npcHp - playerStr);
        player.setHp(playerHp - npcStr);

        if ((playerHp - npcStr) <= 0.0) {
            Main.game.setTheEnd(true);
            return Constants.d1 + "You died." + Constants.d2;
        }

        gameState.setInCombat(true);
        return  Constants.d1 + "You inflicted " + playerStr + " damage. Your opponent now has " + attackedNpc.getHp() + " hp.\n" +
                npcName + " attacked you back and caused you " + npcStr +
                " damafe. Now you have " + player.getHp() + " hp." + Constants.d2;
    }
}
