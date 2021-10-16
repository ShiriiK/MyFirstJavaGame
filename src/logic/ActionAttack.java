package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro zaútočení na nepřítele.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class ActionAttack implements IAction {
    private Game game;
    private String[] names = {"útok", "zaútoč_na"};

    /**
     * Konstuktor
     *
     * @param game hra ve které bude příkaz vykonán
     */
    public ActionAttack(Game game) {
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
     * Provádí příkaz attack - zaútočí na npc (když je to možné), pokud to npc přežije, tak útok oplatí.
     *
     * @param parameters jeden parametr - jméno npc, na které hráč útočí
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return "\nPřed útočením si vyber pohlaví.";
        }
        if (phase == 1) {
            return "\nPřed útočením si vyber jméno.";
        }
        if (phase == 2) {
            return "\nJe těžké útočit, když nemáš zbraň.";
        }
        if (parameters.length < 1) {
            return "\nNa koho chceš zaútočit? To musíš napsat také.";
        }
        if (parameters.length > 1) {
            return "\nNemůžeš útočit na víc nepřátel najednou.";
        }

        String npcName = parameters[0];
        Location currentLocation = gameState.getCurrentLocation();

        if (currentLocation.getNpc(npcName) == null) {
            return "\nNemůžeš útočit na někoho, kdo tu není.";
        }

        Npc attackedNpc = currentLocation.getNpc(npcName);

        if (attackedNpc.getFriendly()) {
            if ("gorm".equals(npcName)) {
                return "\nProč útočit na Gorma????";
            }
            return "\nNení důvod útočit na toto npc.";
        }

        Player player = gameState.getPlayer();
        double playerStr = player.getStr();
        double npcHp = attackedNpc.getHp();

        if (npcHp <= playerStr) {
            currentLocation.removeNpc(npcName);
            return "\nZabil/a si: " + npcName + ".";
        }

        double playerHp = player.getHp();
        double npcStr = attackedNpc.getStr();
        attackedNpc.setHp(npcHp - playerStr);
        player.setHp(playerHp - npcStr);

        if ((playerHp - npcStr) <= 0.0) {
            game.setTheEnd(true);
            return "You died.";
        }

        return "\nDal/a si " + playerStr + " poškození. Tvůj oponent teď má " + attackedNpc.getHp() + " životů.\n" +
                npcName + " ti útok oplatil a způsobil ti " + npcStr +
                " poškození. Teď máš " + player.getHp() + " životů.";
    }
}
