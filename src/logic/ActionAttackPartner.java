package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro zaútočení na nepřítele s partnerem.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-01
 */

public class ActionAttackPartner implements IAction {
    private Game game;
    private String[] names = {"zaútoč_s_parťákem_na"};

    //Konstruktor
    public ActionAttackPartner(Game game) {
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
     * Provádí příkaz attackp - zaútočí na npc pomocí partnera (když je to možné), pokud npc přežije, tak útok oplatí.
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
            return d1 + "Nech svého parťáka zatím odpočívat a vyber si pohlaví." + d2;
        }
        if (phase == 1) {
            return d1 + "Nech svého parťáka zatím odpočívat a vyber si jméno." + d2;
        }
        if (phase == 2) {
            return d1 + "Dojdi si pro zbraň a pak můžete vyrazit do boje." + d2;
        }
        if (parameters.length < 1) {
            return d1 + "A a koho chceš zaútočit?" + d2;
        }

        Partner partner = gameState.getPartner();
        String partnerName = partner.getPartnerName();

        if (parameters.length > 1) {
            return d1 + "Ani " + partnerName + " neumí útočit na více nepřátel najednou." + d2;
        }

        String npcName = parameters[0];
        Location currentLocation = gameState.getCurrentLocation();

        if (currentLocation.getNpc(npcName) == null) {
            return d1 + "Nemůžeš útočit na někoho, kdo tu není." + d2;
        }

        Npc attackedNpc = currentLocation.getNpc(npcName);

        if (attackedNpc.isFriendly()) {
            return d1 + "Není důvod útočit na toto npc." + d2;
        }

        double partnerStr = partner.getStr();
        double npcHp = attackedNpc.getHp();

        if (npcHp <= partnerStr) {
            currentLocation.removeNpc(npcName);
            return d1 + "Zabili jste: " + npcName + "." + d2;
        }

        double partnerHp = partner.getHp();
        double npcStr = attackedNpc.getStr();
        attackedNpc.setHp(npcHp - partnerStr);
        partner.setHp(partnerHp - npcStr);

        if ((partnerHp - npcStr) <= 0) {
            game.setTheEnd(true);
            return d1 + "Tvůj partner umřel." + d2;
        }

        gameState.setInCombat(true);
        gameState.setRound(gameState.getRound() + 1);
        return  d1 + partnerName + " dal/a " + partnerStr + " poškození. Tvůj oponent teď má " + attackedNpc.getHp() + " životů.\n" +
                npcName + " útok oplatil a způsobil " + npcStr +
                " poškození. " + partnerName + " teď má " + partner.getHp() + " životů." + d2;
    }
}

