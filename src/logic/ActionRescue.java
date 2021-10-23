package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro záchranu Tua a vyhraní hry.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-16
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
        String d1 = Game.makeItLookGood1();
        String d2 = Game.makeItLookGood2();
        String d3 = Game.makeItLookGood3();

        if (parameters.length >= 1) {
            return d1 + "Stačí napsat jen zachránit_tua, nic víc" + d2;
        }

        Location currentLocation = game.getGameState().getCurrentLocation();

        if (!currentLocation.getName().equals("cela3")) {
            return d1 + "Aby si mohl/a zachrnít Tua, musíš být tam, kde je vězněn" + d2;
        }

        game.setHappyEnd(true);
        game.setTheEnd(true);
        return d3 + "Hra skončila." + d3;
    }
}
