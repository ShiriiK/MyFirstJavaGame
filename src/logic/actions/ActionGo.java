package logic.actions;

import gui.util.Constants;
import logic.*;
import logic.blueprints.*;
import saving_tue.Main;

import java.util.Arrays;

/**
 * A class implementing a command to move between locations.
 * @author Alena Kalivodová
 */

public class ActionGo implements IAction {
    private final String[] names = {"go", "gp_to"};


    /**
     * The method used to identify the validity of commands.
     * @return possible command names
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 2);
    }

    /**
     * Executes the go command - moves the player from one location to another.
     * @param parameters one parameter - destination location
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = Main.game.getGameState();
        PhaseChecker.basicChecker();

        if (parameters.length < 1) {
            return Constants.d1 + "And where's it going to be?" + Constants.d2;
        }
        if (parameters.length > 1) {
            return Constants.d1 + "You can't go to more than one place at a time." + Constants.d2;
        }

        String targetLocationName = parameters[0];
        Location currentLocation = gameState.getCurrentLocation();

        if (currentLocation.getExit(targetLocationName) == null) {
            return Constants.d1 + "You can't get there from here." + Constants.d2;
        }

        Exit targetLocationExit = currentLocation.getExit(targetLocationName);
        Location targetLocation = targetLocationExit.getTargetLocation();

        Inventory inventory = gameState.getInventory();

        if (targetLocationName.equals("dungeon") && !inventory.getContent().containsKey("torch")) {
            return Constants.d1 + "You're not going in there without a light source.." + Constants.d2;
        }
        if (targetLocationName.equals("right_cell")
                && !inventory.getContent().containsKey("key") && !inventory.getContent().containsKey("master_key")) {
            return Constants.d1 + "This cell is locked." + Constants.d2;
        }
        if (targetLocationName.equals("coutyard")) {
            return Constants.d1 + "You really don't believe, do you?" + Constants.d2;
        }

        if (Main.game.getGameState().getPhase() < targetLocation.getPhase()) {
            return Constants.d1 + "You really think Gorm's gonna let you leave the campground without a weapon? Take one." + Constants.d2;
        }

        if (gameState.isInCombat()) {
            return Constants.d1 + "Don't run away and finish the fight first.." + Constants.d2;
        }

        for (Npc npc : currentLocation.getNpcs()) {
            if (npc.equals(targetLocationExit.containsNpc(npc))) {
                return Constants.d1 + npc.getMessage() + Constants.d2;
            }
        }

        Player player = gameState.getPlayer();
        int playerHp = player.getHp();
        int dmg = targetLocationExit.getDamage();
        String description = targetLocation.longDescription();

        if (targetLocationName.equals("middle_cell")) {
            if (playerHp < dmg) {
                Main.game.setTheEnd(true);
                return Constants.d1 + "Someone attacked you after you entered the cell and killed you." + Main.game.epilog() + Constants.d2;
            } else {
                player.setHp(playerHp - dmg);
                gameState.setCurrentLocation(targetLocation);
                gameState.setAttackedNpc("brutal_guard");
                gameState.setInCombat(true);
                gameState.getPlayer().setRound(gameState.getPlayer().getRound() + 1);
                return Constants.d1+ description + "\nSomeone brutally attacked you from behind." + Constants.d2 +
                        targetLocationExit.getDamageMessage();
            }
        }
        if (targetLocationName.equals("mountain") && !targetLocation.getNpcs().isEmpty()) {
            if (playerHp < dmg) {
                Main.game.setTheEnd(true);
                return Constants.d1 + "A wild animal killed you." + Main.game.epilog() + Constants.d2;
            }
            player.setHp(playerHp - dmg);
            gameState.setCurrentLocation(targetLocation);
            gameState.setAttackedNpc("bear");
            gameState.setInCombat(true);
            gameState.getPlayer().setRound(gameState.getPlayer().getRound() + 1);
            return Constants.d1 + description + "\nYou've been attacked by wild animals." + Constants.d2 +
                    targetLocationExit.getDamageMessage();
        }
        if (targetLocationName.equals("forest")  && !targetLocation.getNpcs().isEmpty()) {
            if (playerHp < dmg) {
                Main.game.setTheEnd(true);
                return Constants.d1 + "A troll killed you." + Main.game.epilog() + Constants.d2;
            }
            player.setHp(playerHp - dmg);
            gameState.setCurrentLocation(targetLocation);
            gameState.setAttackedNpc("troll");
            gameState.setInCombat(true);
            gameState.getPlayer().setRound(gameState.getPlayer().getRound() + 1);
            return Constants.d1 + description + "\nYou've been attacked by a troll." + Constants.d2 +
                    targetLocationExit.getDamageMessage();
        }

        gameState.setCurrentLocation(targetLocation);
        return description;
    }
}

