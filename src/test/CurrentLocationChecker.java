package test;

import logic.*;

/**
 * Třída implementující kontrolu aktuální lokace po vykonání příkazu.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Jan Říha
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-23
 */

public class CurrentLocationChecker implements IChecker {

    @Override
    public CheckResult check(Step step, Game game, String actionResult) {
        String message = "Location: " + game.getGameState().getCurrentLocation().getName()
                + "\nResult: ";

        if (step.getLocation() == null) {
            return new CheckResult(true, message + "Not checked\n__________________________________________");
        }

        if (game.getGameState().getCurrentLocation().getName().equals(step.getLocation())) {
            return new CheckResult(true, message + "OK\n__________________________________________");
        }
        return new CheckResult(false, message + "Not OK. Expected: \n" + step.getLocation() + "\n__________________________________________");
    }
}
