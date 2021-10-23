package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro zaútočení na nepřítele.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-16
 */

public class ActionAttack implements IAction {
    private Game game;
    private String[] names = {"zaútoč_na"};

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
        return Arrays.copyOf(names, 1);
    }

    /**
     * Provádí příkaz attack - zaútočí na npc (když je to možné), pokud to npc přežije, tak útok oplatí.
     *
     * @param parameters jeden parametr - jméno npc, na které hráč útočí
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {
        String d1 = Game.makeItLookGood1();
        String d2 = Game.makeItLookGood2();

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return d1 + "Před útočením si vyber pohlaví." + d2;
        }
        if (phase == 1) {
            return d1 + "Před útočením si vyber jméno." + d2;
        }
        if (phase == 2) {
            return d1 + "Je těžké útočit, když nemáš zbraň." + d2;
        }
        if (parameters.length < 1) {
            return d1 + "Na koho chceš zaútočit? To musíš napsat také." + d2;
        }
        if (parameters.length > 1) {
            return d1 + "Nemůžeš útočit na víc nepřátel najednou." + d2;
        }

        String npcName = parameters[0];
        Location currentLocation = gameState.getCurrentLocation();

        if (currentLocation.getNpc(npcName) == null) {
            return d1 + "Nemůžeš útočit na někoho, kdo tu není." + d2;
        }

        Npc attackedNpc = currentLocation.getNpc(npcName);

        if (attackedNpc.getFriendly()) {
            if ("gorm".equals(npcName)) {
                return d1 + "Proč útočit na Gorma????" + d2;
            }
            return d1 + "Není důvod útočit na toto npc." + d2;
        }

        Player player = gameState.getPlayer();
        double playerStr = player.getStr();
        double npcHp = attackedNpc.getHp();

        if (npcHp <= playerStr) {
            currentLocation.removeNpc(npcName);
            return d1 + "Zabil/a si: " + npcName + "." + d2;
        }

        double playerHp = player.getHp();
        double npcStr = attackedNpc.getStr();
        attackedNpc.setHp(npcHp - playerStr);
        player.setHp(playerHp - npcStr);

        if ((playerHp - npcStr) <= 0.0) {
            game.setTheEnd(true);
            return d1 + "Umřel/a jsi." + d2;
        }

        return  d1 + "Dal/a si " + playerStr + " poškození. Tvůj oponent teď má " + attackedNpc.getHp() + " životů.\n" +
                npcName + " ti útok oplatil a způsobil ti " + npcStr +
                " poškození. Teď máš " + player.getHp() + " životů." + d2;
    }
}
