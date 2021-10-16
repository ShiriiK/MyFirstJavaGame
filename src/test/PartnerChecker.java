package test;

import logic.Game;

/**
 * Třída implementující kontrolu partnera po vykonání příkazu.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-23
 */

public class PartnerChecker implements IChecker {

    @Override
    public CheckResult check(Step step, Game game, String actionResult) {
        String message = "Partner: " + game.getGameState().getPartner().getPartner()
                + "\nResult: ";

        if (step.getPartner() == null) {
            return new CheckResult(true, message + "Not checked\n__________________________________________");
        }

        if (game.getGameState().getPartner().getPartner().equals(step.getPartner())) {
            return new CheckResult(true, message + "OK\n__________________________________________");
        }
        return new CheckResult(false, message + "Not OK. Expected: \n" + step.getPartner() + "\n__________________________________________");
    }
}