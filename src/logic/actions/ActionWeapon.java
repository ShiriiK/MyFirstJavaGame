package logic.actions;

import gui.util.Constants;
import logic.*;
import logic.blueprints.Location;
import logic.blueprints.Npc;
import logic.blueprints.Weapon;
import saving_tue.Main;

import java.util.Arrays;

/**
 * Class implementing the weapon setup command.
 * @author Alena Kalivodová
 */

public class ActionWeapon implements IAction {
    private final String[] names = {"weapon", "take_weapon"};

    /**
     * The method used to identify the validity of commands.
     * @return possible command names
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 2);
    }

    /**
     * Executes the weapon command - sets the player's weapon.
     * @param parameters one parameter - the name of the weapon the player wants to take
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = Main.game.getGameState();
        PhaseChecker.basicChecker();

        if (parameters.length == 0) {
            return Constants.d1 + "And which weapon do you want?." + Constants.d2;
        }
        if (parameters.length > 1) {
            return Constants.d1 + "You can only have one weapon." + Constants.d2;
        }

        String weaponName = parameters[0];
        Location currentLocation = Main.game.getGameState().getCurrentLocation();

        if (currentLocation.getWeapon(weaponName) == null) {
            return Constants.d1 + "There is no such weapon." + Constants.d2;
        }
        if (Main.game.getGameState().getPhase() == 4) {
            return Constants.d1 + "You have to put down the weapon you're carrying before you can take another one." + Constants.d2;
        }

        Npc gorm = Main.game.getGameState().getCurrentLocation().getExit("forge").getTargetLocation().getNpc("gorm");
        Weapon weapon = currentLocation.getWeapon(weaponName);

        if (weapon.isLocked() && gorm.getItemInNpc("shinning_rock") == null) {
            return Constants.d1 + "You can't take this weapon." + Constants.d2;
        }

        currentLocation.removeWeapon(weaponName);
        gameState.getPlayer().setPlayerWeapon(weapon);
        gameState.setPhase(4);
        return Constants.d1 + "Weapon set to: " + weaponName + Constants.d2;
    }
}
