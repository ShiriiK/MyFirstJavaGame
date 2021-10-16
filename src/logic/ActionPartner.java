package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro zobrazení statů partnera.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class ActionPartner implements IAction {
    private Game game;
    private String[] names = {"parťák", "parťačka"};

    /**
     * Konstuktor
     *
     * @param game hra ve které bude příkaz vykonán
     */
    public ActionPartner(Game game) {
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
     * Provádí příkaz partner - zobrazí staty partnera.
     *
     * @param parameters žádný
     * @return staty partnera
     */
    @Override
    public String execute(String[] parameters) {
        String d1 = Game.makeItLookGood1();
        String d2 = Game.makeItLookGood2();

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return d1 + "Předtím, než se budeš moct prohlížet staty svého parťáka, si nastav pohlaví." + d2;
        }
        if (phase == 1) {
            return d1 + "Předtím, než se budeš moct prohlížet staty svého parťáka, si nastav jméno." + d2;
        }
        if (phase == 2) {
            return d1 + "Předtím, než se budeš moct prohlížet staty svého parťáka, si vyber zbraň." + d2;
        }
        if (parameters.length >= 1) {
            return d1 + "Stačí napsat parťák." + d2;
        }

        return d1 + gameState.getPartner().getPartner() + d2;
    }
}
