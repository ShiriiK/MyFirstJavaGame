package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro zobrazení obsahu inventáře.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-16
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
        String d1 = Game.makeItLookGood1();
        String d2 = Game.makeItLookGood2();

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return d1 + "Svůj inventář si můžeš prohlédnout potom, co si nastavíš pohlaví." + d2;
        }
        if (phase == 1) {
            return d1 + "Svůj inventář si můžeš prohlédnout, potom co si nastavíš jméno." + d2;
        }
        if (parameters.length >= 1) {
            return d1 + "Stačí napsat inventář." + d2;
        }

        return gameState.getInventory().getInventory();
    }
}
