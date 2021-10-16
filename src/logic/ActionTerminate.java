package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro ukončení hry.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class ActionTerminate implements IAction {
    private Game game;
    private String[] names = {"konec"};

    /**
     * Konstuktor
     *
     * @param game hra ve které bude příkaz vykonán
     */
    public ActionTerminate(Game game) {
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
     * Provádí příkaz end - ukončí hru.
     *
     * @param parameters žádný
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {
        if (parameters.length >= 1) {
            return "\nStačí napsat konec, nic víc.";
        }

        game.setTheEnd(true);
        return "\nHra skončila.";
    }
}
