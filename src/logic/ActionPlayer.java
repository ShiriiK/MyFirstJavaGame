package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro zobrazení statů hráče.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-16
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
        String d1 = Game.makeItLookGood1();
        String d2 = Game.makeItLookGood2();

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return d1 + "Předtím, než se budeš moct prohlížet své staty, si nastav pohlaví." + d2;
        }
        if (phase == 1) {
            return d1 + "Předtím, než se budeš moct prohlížet své staty, si nastav jméno." + d2;
        }
        if (phase == 2) {
            return d1 + "Předtím, než se budeš moct prohlížet své staty, si vyber zbraň." + d2;
        }
        if (parameters.length >= 1) {
            return d1 + "Stačí napsat hráč." + d2;
        }

        return d1 + gameState.getPlayer().getPlayer() + d2;
    }
}
