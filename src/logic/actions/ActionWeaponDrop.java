package logic.actions;

import gui.util.Constants;
import logic.*;
import logic.blueprints.Location;
import logic.blueprints.Player;
import logic.blueprints.Weapon;
import saving_tue.Main;

import java.util.Arrays;

/**
 * A class implementing the weapon discard command.
 * @author Alena Kalivodová
 */

public class ActionWeaponDrop implements IAction {
    private final String[] names = {"drop_weapon"};

    /**
     * The method used to identify the validity of commands.
     * @return possible command names
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 1);
    }

    /**
     * Executes the weapon_drop command - drops the player's weapon.
     * @param parameters none
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = Main.game.getGameState();
        PhaseChecker.basicChecker();
        PhaseChecker.advancedChecker();

        if (parameters.length >= 1) {
            return Constants.d1 + "Just type drop_weapon." + Constants.d2;
        }

        Location currentLocation = gameState.getCurrentLocation();
        String locationName = currentLocation.getName();

        if (!(locationName.equals("armory"))) {
            return Constants.d1 + "You have to put your weapon down in the room behind the forge." + Constants.d2;
        }

        Player player = gameState.getPlayer();
        Weapon playerWeapon = player.getPlayerWeapon();
        player.setPlayerWeapon(null);
        currentLocation.addWeapon(playerWeapon);
        gameState.setPhase(3);
        return Constants.d1 + "You' ve put your weapon down." + Constants.d2;
    }
}
