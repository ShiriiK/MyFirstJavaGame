package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro zobrazení obsahu inventáře.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class ActionInventory implements IAction {
    private Game game;
    private String[] names = {"inventář", "batoh"};

    /**
     * Konstuktor
     *
     * @param game hra ve které bude příkaz vykonán
     */
    public ActionInventory(Game game) {
        this.game = game;
    }

    /**
     * Metoda použitá pro identifikování platnosti příkazů.
     *
     * @return možné názvy příkazů
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 2);
    }

    /**
     * Provádí příkaz inventory - zobrazí obsah inventáře.
     *
     * @param parameters žádný
     * @return obsah inventáře
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return "\nSvůj inventář si můžeš prohlédnout potom, co si nastavíš pohlaví.";
        }
        if (phase == 1) {
            return "\nSvůj inventář si můžeš prohlédnout, potom co si nastavíš jméno.";
        }
        if (parameters.length >= 1) {
            return "\nStačí napsat inventář.";
        }

        return "\n" + gameState.getInventory().getInventory();
    }
}
