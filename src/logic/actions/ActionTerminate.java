package logic.actions;

import logic.Game;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro ukončení hry.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-16
 */

public class ActionTerminate implements IAction {
    private final Game game;
    private final String[] names = {"konec"};

    //Konstruktor
    public ActionTerminate(Game game) {
        this.game = game;
    }

    /**
     * Metoda použitá pro identifikování platnosti příkazů.
     * @return možné názvy příkazů
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 1);
    }

    /**
     * Provádí příkaz end - ukončí hru.
     * @param parameters žádný
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {
        String d1 = Game.makeItLookGood1();
        String d2 = Game.makeItLookGood2();

        if (parameters.length >= 1) {
            return d1 + "Stačí napsat konec, nic víc." + d2;
        }

        game.setTheEnd(true);
        return d1 + "Hra skončila." + d2;
    }
}
