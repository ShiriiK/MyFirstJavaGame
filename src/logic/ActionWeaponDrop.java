package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro zahození zbraně.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class ActionWeaponDrop implements IAction {
    private Game game;
    private String[] names = {"odložitz", "odložit_zbraň"};

    /**
     * Konstuktor
     *
     * @param game hra ve které bude příkaz vykonán
     */
    public ActionWeaponDrop(Game game) {
        this.game = game;
    }

    /**
     * Metoda použitá pro identifokivání platnosti příkazů.
     *
     * @return možné názvy příkazů
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 2);
    }

    /**
     * Provádí příkaz weapon_drop - odloží hráčovu zbraň.
     *
     * @param parameters žádný
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return "\nNejdřív si vyber pohlaví.";
        }
        if (phase == 1) {
            return "\nNejdřív si vyber jméno.";
        }
        if (phase == 2) {
            return "\nNejdřív si zbraň aspoň vezmi, než ji budeš odkládat.";
        }
        if (parameters.length >= 1) {
            return "\nStačí napsat odložitz.";
        }

        Location currentLocation = gameState.getCurrentLocation();
        String locationName = currentLocation.getName();

        if (!(locationName.equals("místnost"))) {
            return "\nZbraň musíš odložit v místnosti za kovárnou.";
        }

        Player player = gameState.getPlayer();
        Weapon playerWeapon = player.getPlayerWeapon();
        player.setPlayerWeapon(null);
        currentLocation.addWeapon(playerWeapon);
        gameState.setPhase(2);
        return "\nOdložil/a si svou zbraň.";
    }
}
