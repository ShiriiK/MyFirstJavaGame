package test;

import logic.Game;

/**
 * Třída implementující kontrolu hráče po vykonání příkazu.
 * <p>
 * Toto rozhraní je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-23
 */

public class PlayerChecker implements IChecker {

    @Override
    public CheckResult check(Step step, Game game, String actionResult) {
        String message = "Player: " + game.getGameState().getPlayer().getPlayer()
                + "\nResult: ";

        if (step.getPlayer() == null) {
            return new CheckResult(true, message + "Not checked\n__________________________________________");
        }

        if (game.getGameState().getPlayer().getPlayer().equals(step.getPlayer())) {
            return new CheckResult(true, message + "OK\n__________________________________________");
        }
        return new CheckResult(false, message + "Not OK. Expected: \n" + step.getPlayer() + "\n__________________________________________");
    }
}
