package test;

import logic.*;

/**
 * Třída implementující kontrolu odpovědi hry po vykonání příkazu.
 * <p>
 * Toto rozhraní je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Jan Říha
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-23
 */


public class ActionResultChecker implements IChecker {

    @Override
    public CheckResult check(Step step, Game game, String actionResult) {
        String message = "Answer: \n" + actionResult + "\n" +
                "\nResult: ";
        if (step.getMessage() == null) {
            return new CheckResult(true, message + "Not checked\n__________________________________________");
        }

        if (actionResult.toLowerCase().contains(step.getMessage().toLowerCase())) {
            return new CheckResult(true, message + "OK\n__________________________________________");
        }

        return new CheckResult(false, message + "Not OK. Expected: \n" + step.getMessage() + "\n__________________________________________");
    }
}
