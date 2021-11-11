package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro zobrazení statů partnera.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-16
 */

public class ActionPartner implements IAction {
    private final Game game;
    private final String[] names = {"parťák", "parťačka"};

    //Konstruktor
    public ActionPartner(Game game) {
        this.game = game;
    }

    /**
     * Metoda použitá pro identifikování platnosti příkazů.
     * @return možné názvy příkazů
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 2);
    }

    /**
     * Provádí příkaz partner - zobrazí staty partnera.
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
