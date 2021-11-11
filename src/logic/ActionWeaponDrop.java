package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro zahození zbraně.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-16
 */

public class ActionWeaponDrop implements IAction {
    private final Game game;
    private final String[] names = {"odlož_zbraň"};

    //Konstuktor
    public ActionWeaponDrop(Game game) {
        this.game = game;
    }

    /**
     * Metoda použitá pro identifokivání platnosti příkazů.
     * @return možné názvy příkazů
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 1);
    }

    /**
     * Provádí příkaz weapon_drop - odloží hráčovu zbraň.
     * @param parameters žádný
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {
        String d1 = Game.makeItLookGood1();
        String d2 = Game.makeItLookGood2();

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return d1 + "Nejdřív si vyber pohlaví." + d2;
        }
        if (phase == 1) {
            return d1 + "Nejdřív si vyber jméno." + d2;
        }
        if (phase == 2) {
            return d1 + "Nejdřív si zbraň aspoň vezmi, než ji budeš odkládat." + d2;
        }
        if (parameters.length >= 1) {
            return d1 + "Stačí napsat odložitz." + d2;
        }

        Location currentLocation = gameState.getCurrentLocation();
        String locationName = currentLocation.getName();

        if (!(locationName.equals("zbrojírna"))) {
            return d1 + "Zbraň musíš odložit v místnosti za kovárnou." + d2;
        }

        Player player = gameState.getPlayer();
        Weapon playerWeapon = player.getPlayerWeapon();
        player.setPlayerWeapon(null);
        currentLocation.addWeapon(playerWeapon);
        gameState.setPhase(2);
        return d1 + "Odložil/a si svou zbraň." + d2;
    }
}
