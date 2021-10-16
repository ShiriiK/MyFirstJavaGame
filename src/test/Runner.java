package test;

import logic.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Třída obsahující logiku pro kontrolu testovacích scénářů.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Jan Říha
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-23
 */

public class Runner {

    private List<IChecker> checkers;

    /**
     * Konstruktor třídy, vytvoří nový spouštěč testů a množinu prováděných kontrol.
     */
    public Runner() {
        checkers = new ArrayList<>();
        checkers.add(new ActionResultChecker());
        checkers.add(new CurrentLocationChecker());
        checkers.add(new CurrentLocationExitsChecker());
        checkers.add(new PlayerChecker());
        checkers.add(new PartnerChecker());
        checkers.add(new InventoryChecker());
        checkers.add(new CurrentLocationWeaponsChecker());
        checkers.add(new CurrentLocationNpcsChecker());
        checkers.add(new CurrentLocationItemsChecker());
        checkers.add(new EndChecker());
    }

    private String run(Scenario scenario) {
        if (scenario.getSteps().isEmpty()) {
            return "\n\nScenarion:\n //////////////////////////////////////////////               " + scenario.getName() + "               //////////////////////////////////////////////"
                    + "\nno steps in this scenario\n";
        }

        int i = 1;
        boolean success = true;
        Game game = new Game();
        String result = "\n\nScenarion:\n//////////////////////////////////////////////               " + scenario.getName() + "               //////////////////////////////////////////////";

        for (Step step : scenario.getSteps()) {
            result += "\n\n____________________________________________________________________________________\n" + i++ + ". " + step.getAction();

            String actionResult = null;
            try {
                actionResult = game.processAction(step.getAction());

                for (IChecker checker : checkers) {
                    CheckResult checkResult = checker.check(step, game, actionResult);

                    success &= checkResult.isSuccess();

                    result += "\n" + checkResult.getMessage();
                }
            } catch (Exception e) {
                success = false;
                result += "\n" + e.getMessage();
                throw e;
            }

            if (!success) {
                break;
            }
        }
        result += "\n";

        if (success) {
            result += "\n//////////////////////////////////////////////               Success               //////////////////////////////////////////////\n\n\n";
        } else {
            result += "\n//////////////////////////////////////////////               Fail               //////////////////////////////////////////////\n\n\n";
        }
        result += "\n";

        return result;
    }

    /**
     * Metoda vrátí výpis testovacích scénářů definovaných ve třídě {@link Scenarios}.
     *
     * @return výpis testovacích scénářů
     */
    public String showAllScenarios() {
        String result = "";

        for (Scenario s : Scenarios.SCENARIOS) {
            result += s.toString();
        }
        return result;
    }

    /**
     * Metoda spustí testovací scénáře definované ve třídě {@link Scenarios} a vrátí
     * podrobný výpis výsledků.
     *
     * @return podrobný výpis výsledků provedených testovacích scénářů
     */
    public String runAllScenarios() {
        String result = "";

        for (Scenario s : Scenarios.SCENARIOS) {
            result += run(s);
        }
        return result;
    }

}

