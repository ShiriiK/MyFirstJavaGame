package test;

import logic.Game;
import logic.blueprints.Npc;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Třída implementující kontrolu npc v aktuální lokaci po vykonání příkazu.
 * <p>
 * Toto rozhraní je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-23
 */

public class CurrentLocationNpcsChecker implements IChecker {

    @Override
    public CheckResult check(Step step, Game game, String actionResult) {

        Set<String> npcs = new TreeSet<>();
        for (Npc npc : game.getGameState().getCurrentLocation().getNpcs()) {
            npcs.add(npc.getName());
        }

        String message = "Npcs: " + String.join(", ", npcs)
                + "\nResult :";

        if (step.getNpcs() == null) {
            return new CheckResult(true, message + "Not checked\n__________________________________________");
        }

        Set<String> expectedNames = new TreeSet<>(Arrays.asList(step.getNpcs()));

        if (npcs.equals(expectedNames)) {
            return new CheckResult(true, message + "OK\n__________________________________________");
        }
        return new CheckResult(false, message + "Not OK. Expected:\n " + String.join(", ", expectedNames) + "\n__________________________________________");
    }
}

