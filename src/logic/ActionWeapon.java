package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro nastavení zbraně .
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-16
 */

public class ActionWeapon implements IAction {
    private final Game game;
    private final String[] names = {"vzemi_si_zbraň", "zbraň"};

    //Konstuktor

    public ActionWeapon(Game game) {
        this.game = game;
    }

    /**
     * Metoda použitá pro identifokivání platnosti příkazů.
     * @return možné názvy příkazů
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 2);
    }

    /**
     * Provádí příkaz weapon - nastaví hráči zbraň.
     * @param parameters jeden parametr - jméno zbraně, kterou si chce hráč vzít
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
        if (parameters.length == 0) {
            return d1 + "A kterou zbraň chceš?." + d2;
        }
        if (parameters.length > 1) {
            return d1 + "Můžeš mít jenom jednu zbraň." + d2;
        }

        String weaponName = parameters[0];
        Location currentLocation = game.getGameState().getCurrentLocation();

        if (currentLocation.getWeapon(weaponName) == null) {
            return d1 + "Taková zbraň tu není." + d2;
        }
        if (phase == 3) {
            return d1 + "Musíš nejdřív položit zbraň, kterou máš u sebe, než si vezmeš jinou." + d2;
        }

        Npc gorm = game.getGameState().getCurrentLocation().getExit("kovárna").getTargetLocation().getNpc("gorm");
        Weapon weapon = currentLocation.getWeapon(weaponName);

        if (weapon.isLocked() && gorm.getItemInNpc("svítící_kámen") == null) {
            return d1 + "Tuhle zbraň si vzít nemůžeš" + d2;
        }

        Weapon partnerWeapon = game.getGameState().getPartner().getPartnerWeapon();
        String partnerName = game.getGameState().getPartner().getPartnerName();
        if (partnerWeapon == null) {
            if (partnerName.equals("Yrsa")) {
                gameState.getPartner().setPartnerWeapon
                        (currentLocation.getWeapon("meč"));
            } else if (partnerName.equals("Torsten")) {
                gameState.getPartner().setPartnerWeapon
                        (currentLocation.getWeapon("sekera"));
            }
        }

        currentLocation.removeWeapon(weaponName);
        gameState.getPlayer().setPlayerWeapon(weapon);
        gameState.setPhase(3);
        return d1 + "Zbraň nastavena na: " + weaponName + d2;
    }
}
