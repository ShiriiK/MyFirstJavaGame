package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro záchranu Tua a vyhraní hry.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class ActionRescue implements IAction {
    private Game game;
    private String[] names = {"zachráňit_tua"};

    /**
     * Konstuktor
     *
     * @param game hra ve které bude příkaz vykonán
     */
    public ActionRescue(Game game) {
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
     * Provádí příkaz rescue - zachrání Tua a ukončí hru.
     *
     * @param parameters žádný
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {
        if (parameters.length >= 1) {
            return "\nStačí napsat jen zachránit_tua, nic víc";
        }

        Location currentLocation = game.getGameState().getCurrentLocation();

        if (!currentLocation.getName().equals("cela3")) {
            return "\nAby si mohl/a zachrnít Tua, musíš být tam, kde je vězněn";
        }

        game.setHappyEnd(true);
        game.setTheEnd(true);
        return "Hra skončila.";
    }
}
