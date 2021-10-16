package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro nastavení zbraně .
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class ActionWeapon implements IAction {
    private Game game;
    private String[] names = {"vzít_zbraň", "zbraň"};

    /**
     * Konstuktor
     *
     * @param game hra ve které bude příkaz vykonán
     */
    public ActionWeapon(Game game) {
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
     * Provádí příkaz weapon - nastaví hráči zbraň.
     *
     * @param parameters jeden parametr - jméno zbraně, kterou si chce hráč vzít
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
        if (parameters.length == 0) {
            return "\nA kterou zbraň chceš?.";
        }
        if (parameters.length > 1) {
            return "\nMůžeš mít jenom jednu zbraň.";
        }

        String weaponName = parameters[0];
        Location currentLocation = game.getGameState().getCurrentLocation();

        if (currentLocation.getWeapon(weaponName) == null) {
            return "\nTaková zbraň tu není.";
        }
        if (phase == 3) {
            return "\nMusíš nejdřív položit zbraň, kterou máš u sebe, než si vezmeš jinou.";
        }

        Npc gorm = game.getGameState().getCurrentLocation().getExit("kovárna").getTargetLocation().getNpc("gorm");
        Weapon weapon = currentLocation.getWeapon(weaponName);

        if (weapon.isLocked() && gorm.getItemInNpc("svítící_kámen") == null) {
            return "\nTuhle zbraň si vzít nemůžeš";
        }

        Weapon partnerWeapon = game.getGameState().getPartner().getPartnerWeapon();
        String partnerName = game.getGameState().getPartner().getPartnerName();
        if (partnerWeapon == null) {
            if (partnerName.equals("Yrsa")) {
                gameState.getPartner().setPartnerWeapon
                        (currentLocation.getWeapon("nůž"));
            } else if (partnerName.equals("Torsten")) {
                gameState.getPartner().setPartnerWeapon
                        (currentLocation.getWeapon("sekera"));
            }
        }

        currentLocation.removeWeapon(weaponName);
        gameState.getPlayer().setPlayerWeapon(weapon);
        gameState.setPhase(3);
        return "\nGorm: Dobře, tady máš.\n" +
                "Zbraň nastavena na: " + weaponName;
    }
}
