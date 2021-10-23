package test;

import logic.Game;
import logic.Item;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Třída implementující kontrolu itemů v aktuální lokaci po vykonání příkazu.
 * <p>
 * Toto rozhraní je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-23
 */

public class CurrentLocationItemsChecker implements IChecker {

    @Override
    public CheckResult check(Step step, Game game, String actionResult) {

        Set<String> items = new TreeSet<>();
        for (Item item : game.getGameState().getCurrentLocation().getItems()) {
            items.add(item.getName());
        }

        String message = "Items: " + String.join(", ", items)
                + "\nResult :";
        if (step.getItems() == null) {
            return new CheckResult(true, message + "Not checked\n__________________________________________");
        }

        Set<String> expectedNames = new TreeSet<>(Arrays.asList(step.getItems()));

        if (items.equals(expectedNames)) {
            return new CheckResult(true, message + "OK\n__________________________________________");
        }
        return new CheckResult(false, message + "Not OK. Expected:\n " + String.join(", ", expectedNames) + "\n__________________________________________");
    }
}


