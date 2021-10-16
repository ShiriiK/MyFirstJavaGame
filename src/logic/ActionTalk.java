package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro mluvení s npc ve hře.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class ActionTalk implements IAction {
    private Game game;
    private String[] names = {"mluvit", "mluvit_s"};

    /**
     * Konstuktor
     *
     * @param game hra ve které bude příkaz vykonán
     */
    public ActionTalk(Game game) {
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
     * Provádí příkaz talk - promluví si s npc.
     *
     * @param parameters jeden parametr - jméno npc se kterýmm chce hráč mluvit
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {
        String d1 = Game.makeItLookGood1();
        String d2 = Game.makeItLookGood2();

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return d1 + "S ostatními můžeš mluvit po výběru pohlaví." + d2;
        }
        if (phase == 1) {
            return d1 + "S ostatními můžeš mluvit po výběru jména." + d2;
        }
        if (parameters.length < 1) {
            return d1 + "A s kým chceš mluvit." + d2;
        }
        if (parameters.length > 1) {
            return d1 + "Můžeš mluvit jen s jedním člověkem najednou." + d2;
        }

        String npcName = parameters[0];
        Location currentLocation = gameState.getCurrentLocation();

        if (currentLocation.getItem(npcName) != null) {
            return d1 + "Ještě nejsi na tolik šílený/á, aby si mluvl/a s předmětem..." + d2;
        }
        if (currentLocation.getNpc(npcName) == null) {
            return d1 + "Nemůžeš mluvit s někým, kdo tu není." + d2;
        }

        Npc npc = currentLocation.getNpc(npcName);

        if (!npc.getTalk()) {
            return d1 + "Není důvod..." + d2;
        }

        Player player = gameState.getPlayer();
        int talked = npc.getTalked();

        if (!npc.getTalks().isEmpty()) {
            int i = npc.getTalked();
            if (talked == 2 && npcName.equals("generál")) {
                game.setTheEnd(true);
            }
            if (talked == 1 && npcName.equals("stráž")) {
                player.setHp(player.getHp() - npc.getStr());
                if (player.getHp() <= 0) {
                    game.setTheEnd(true);
                }
                return d1 + npc.getChat(npc) + "\n Dědek na tebe zaútočil a způsobil ti " + npc.getStr() + " poškození. " +
                        "Zbývá ti " + player.getHp() + " zdraví." + d2;
            }
            if (talked == 2 && npcName.equals("stráž")) {
                game.setTheEnd(true);
                return npc.getChat(npc);
            }

            Inventory inventory = game.getGameState().getInventory();

            if (talked == 1 && npcName.equals("stráž_průchodu")) {
                if (inventory.isSpace()) {
                    Item item = npc.getItemInNpc("univerzální_klíč");
                    npc.removeItemInNpc("univerzální_klíč");
                    inventory.addItem(item);
                    return "\n" + npc.getChat(npc) + d1 + "ARMIN ti dal univerzální_klíč" + d2;
                } else {
                    return "\nArmin: Něco ti dám, ale uvolni si předtím místo v batohu.";
                }
            }
            return "\n" + npc.getChat(npc);
        }

        return d1 + "Už jste si povídali až až." + d2;
    }
}
