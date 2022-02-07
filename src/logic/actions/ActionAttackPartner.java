package logic.actions;

import gui.util.Constants;
import logic.*;
import logic.blueprints.Location;
import logic.blueprints.Npc;
import logic.blueprints.Partner;
import saving_tue.Main;

import java.util.Arrays;

/**
 * A class implementing a command to attack an enemy with a partner.
 * @author Alena Kalivodová
 */

public class ActionAttackPartner implements IAction {
    private final String[] names = {"partner_attack"};

    /**
     * The method used to identify the validity of commands.
     * @return possible command names
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 1);
    }

    /**
     * Executes the partnerAttack command - attacks the npc with a partner (if possible), if the npc survives, it attacks back.
     * @param parameters one parameter - the name of the npc the player is attacking
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = Main.game.getGameState();
        PhaseChecker.basicChecker();
        PhaseChecker.advancedChecker();

        if (parameters.length < 1) {
            return Constants.d1 + "And who do you want to attack?" + Constants.d2;
        }

        Partner partner = gameState.getPartner();
        String partnerName = partner.getPartnerName();

        if (parameters.length > 1) {
            return Constants.d1 + "Not even" + partnerName + " can attack multiple enemies at once." + Constants.d2;
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

        int partnerStr = partner.getStr();
        int npcHp = attackedNpc.getHp();

        if (npcHp <= partnerStr) {
            currentLocation.removeNpc(npcName);
            return Constants.d1 + "You killed: " + npcName + "." + Constants.d2;
        }

        int partnerHp = partner.getHp();
        int npcStr = attackedNpc.getStr();
        attackedNpc.setHp(npcHp - partnerStr);
        partner.setHp(partnerHp - npcStr);

        if ((partnerHp - npcStr) <= 0) {
            Main.game.setTheEnd(true);
            return Constants.d1 + "Your partner died." + Constants.d2;
        }

        gameState.setInCombat(true);
        gameState.getPlayer().setRound(gameState.getPlayer().getRound() + 1);
        return  Constants.d1 + partnerName + " inflicted " + partnerStr + " damage. Your opponent has now " + attackedNpc.getHp() + " hp.\n" +
                npcName + " attacked back and caused " + npcStr +
                " damage. " + partnerName + " now has " + partner.getHp() + " hp." + Constants.d2;
    }
}

