package test;

import logic.Game;
import logic.Weapon;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Třída implementující kontrolu zbraní v aktuální lokaci po vykonání příkazu.
 * <p>
 * Toto rozhraní je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-23
 */

public class CurrentLocationWeaponsChecker implements IChecker {

    @Override
    public CheckResult check(Step step, Game game, String actionResult) {

        Set<String> weapons = new TreeSet<>();
        for (Weapon weapon : game.getGameState().getCurrentLocation().getWeapons()) {
            weapons.add(weapon.getName());
        }

        String message = "Weapons: " + String.join(", ", weapons)
                + "\nResult :";
        if (step.getWeapons() == null) {
            return new CheckResult(true, message + "Not checked\n__________________________________________");
        }

        Set<String> expectedNames = new TreeSet<>(Arrays.asList(step.getWeapons()));

        if (weapons.equals(expectedNames)) {
            return new CheckResult(true, message + "OK\n__________________________________________");
        }
        return new CheckResult(false, message + "Not OK. Expected:\n " + String.join(", ", expectedNames) + "\n__________________________________________");
    }
}


