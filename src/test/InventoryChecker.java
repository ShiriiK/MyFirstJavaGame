package test;

import logic.Game;
import logic.Item;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Třída implementující kontrolu inventáře po vykonání příkazu.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-23
 */

public class InventoryChecker implements IChecker {

    @Override
    public CheckResult check(Step step, Game game, String actionResult) {

        Set<String> inventory = new TreeSet<>();
        for (Item item : game.getGameState().getInventory().getContent()) {
            inventory.add(item.getName());
        }

        String message = "Inventory: " + String.join(", ", inventory)
                + "\nResult :";

        if (step.getInvenotry() == null) {
            return new CheckResult(true, message + "Not checked\n__________________________________________");
        }

        Set<String> expectedNames = new TreeSet<>(Arrays.asList(step.getInvenotry()));

        if (inventory.equals(expectedNames)) {
            return new CheckResult(true, message + "OK\n__________________________________________");
        }
        return new CheckResult(false, message + "Not OK. Expected:\n " + String.join(", ", expectedNames) + "\n__________________________________________");
    }
}