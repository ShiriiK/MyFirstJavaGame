package test;

import logic.Exit;
import logic.Game;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Třída implementující kontrolu exitů z lokace po vykonání příkazu.
 * <p>
 * Toto rozhraní je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Jan Říha
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-23
 */

public class CurrentLocationExitsChecker implements IChecker {

    @Override
    public CheckResult check(Step step, Game game, String actionResult) {

        Set<String> exitNames = new TreeSet<>();
        for (Exit exit : game.getGameState().getCurrentLocation().getExits()) {
            exitNames.add(exit.getTargetLocation().getName());
        }

        String message = "Exits: " + String.join(", ", exitNames)
                + "\nResult :";
        if (step.getExits() == null) {
            return new CheckResult(true, message + "Not checked\n__________________________________________");
        }

        Set<String> expectedNames = new TreeSet<>(Arrays.asList(step.getExits()));

        if (exitNames.equals(expectedNames)) {
            return new CheckResult(true, message + "OK\n__________________________________________");
        }
        return new CheckResult(false, message + "Not OK. Expected:\n " + String.join(", ", expectedNames) + "\n__________________________________________");
    }
}
