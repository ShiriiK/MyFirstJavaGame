package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro zobrazení statů hráče.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class ActionPlayer implements IAction {
    private Game game;
    private String[] names = {"hráč"};

    /**
     * Konstuktor
     *
     * @param game hra ve které bude příkaz vykonán
     */
    public ActionPlayer(Game game) {
        this.game = game;
    }

    /**
     * Metoda použitá pro identifikování platnosti příkazů.
     *
     * @return možné názvy příkazů
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 1);
    }

    /**
     * Provádí příkaz player - nastaví jméno hráče.
     *
     * @param parameters žádný
     * @return staty hráče
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return "\nPředtím, než se budeš moct prohlížet své staty, si nastav pohlaví.";
        }
        if (phase == 1) {
            return "\nPředtím, než se budeš moct prohlížet své staty, si nastav jméno.";
        }
        if (phase == 2) {
            return "\nPředtím, než se budeš moct prohlížet své staty, si vyber zbraň.";
        }
        if (parameters.length >= 1) {
            return "\nStačí napsat hráč.";
        }

        return "\n" + gameState.getPlayer().getPlayer();
    }
}
