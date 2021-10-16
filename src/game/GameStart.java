package game;

import test.Runner;
import logic.Game;
import ui.TextInterface;

/**
 * Spouštěcí třída aplikace.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Jan Říha
 * @author Alena Kalivodová
 * @version LS2021, 2021-05-26
 */

public class GameStart {

    /**
     * Spouštěcí metoda aplikace. Vyhodnotí parametry, se kterými byla aplikace
     * spuštěna, a na základě nich rozhodne, jakou operaci provede <i>(hra, výpis
     * testovacích scénářů, spuštění testovacích scénářů)</i>.
     * <p>
     * Pokud byla aplikace spuštěna s nepodporovanými parametry, vypíše nápovědu
     * a skončí.
     *
     * @param args parametry aplikace z příkazové řádky
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            Game game = new Game();
            TextInterface ui = new TextInterface(game);
            ui.play();
        } else if (args.length == 1 && args[0].equalsIgnoreCase("SHOW_SCENARIOS")) {
            Runner runner = new Runner();

            System.out.println(runner.showAllScenarios());
        } else if (args.length == 1 && args[0].equalsIgnoreCase("RUN_SCENARIOS")) {
            Runner runner = new Runner();

            System.out.println(runner.runAllScenarios());
        } else {
            System.out.println("Spuštění hry selhalo.");

            System.out.println("\nPro spuštění hry můžeš použít následující parametry:");
            System.out.println("  <žádné>              : Zapne hru.");
            System.out.println("  SHOW_SCENARIOS      : Vypíše kroky testovacího scénáře.");
            System.out.println("  RUN_SCENARIOS       : Otestuje krory testovacího scénáře.");
        }
    }
}
