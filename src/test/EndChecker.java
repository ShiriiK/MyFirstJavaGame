package test;

import logic.Game;

/**
 * Třída implementující kontrolu průběhu hry po vykonání příkazu.
 * <p>
 * Toto rozhraní je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-23
 */

public class EndChecker implements IChecker {

    @Override
    public CheckResult check(Step step, Game game, String actionResult) {

        String end = "Game in progress.";
        if (game.theEnd()) {
            end = game.theEpilog();
        }
        String message = "End: \n" + end + "\n" +
                "\nResult: ";
        if (step.getEnding() == null) {
            return new CheckResult(true, message + "Not checked\n__________________________________________");
        }

        if (game.theEpilog().equals(step.getEnding())) {
            return new CheckResult(true, message + "OK\n__________________________________________");
        }

        return new CheckResult(false, message + "Not OK. Expected: \n" + step.getEnding() + "\n__________________________________________");
    }
}
