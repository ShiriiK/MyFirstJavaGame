package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro zaútočení na nepřítele s partnerem.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class ActionAttackPartner implements IAction {
    private Game game;
    private String[] names = {"útokp", "zaútoč_s_parťákem"};

    /**
     * Konstuktor
     *
     * @param game hra ve které bude příkaz vykonán
     */
    public ActionAttackPartner(Game game) {
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
     * Provádí příkaz attackp - zaútočí na npc pomocí partnera (když je to možné), pokud npc přežije, tak útok oplatí.
     *
     * @param parameters jeden parametr - jméno npc, na které hráč útočí
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return "\nNech svého parťáka zatím odpočívat a vyber si pohlaví.";
        }
        if (phase == 1) {
            return "\nNech svého parťáka zatím odpočívat a vyber si jméno.";
        }
        if (phase == 2) {
            return "\nNech svého parťáka zatím odpočívat a jdi si pro zbraň.";
        }
        if (parameters.length < 1) {
            return "\nA a koho chceš zaútočit?";
        }

        Partner partner = gameState.getPartner();
        String partnerName = partner.getPartnerName();

        if (parameters.length > 1) {
            return "\nAni " + partnerName + " neumí útočit na více nepřátel najednou.";
        }

        String npcName = parameters[0];
        Location currentLocation = gameState.getCurrentLocation();

        if (currentLocation.getNpc(npcName) == null) {
            return "\nNemůžeš útočit na někoho, kdo tu není.";
        }

        Npc attackedNpc = currentLocation.getNpc(npcName);

        if (attackedNpc.getFriendly()) {
            return "\nNení důvod útočit na toto npc.";
        }

        double partnerStr = partner.getStr();
        double npcHp = attackedNpc.getHp();

        if (npcHp <= partnerStr) {
            currentLocation.removeNpc(npcName);
            return "\nZabil/a jsi: " + npcName + ".";
        }

        double partnerHp = partner.getHp();
        double npcStr = attackedNpc.getStr();
        attackedNpc.setHp(npcHp - partnerStr);
        partner.setHp(partnerHp - npcStr);

        if ((partnerHp - npcStr) <= 0) {
            game.setTheEnd(true);
            return "Tvůj partner umřel.";
        }

        return "\n" + partnerName + " dal/a " + partnerStr + " poškození. Tvůj oponent teď má " + attackedNpc.getHp() + " životů.\n" +
                npcName + " útok oplatil a způsobil " + npcStr +
                " poškození. " + partnerName + " teď má " + partner.getHp() + " životů.";
    }
}

