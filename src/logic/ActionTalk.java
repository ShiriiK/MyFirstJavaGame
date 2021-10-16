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

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return "\nS ostatními můžeš mluvit po výběru pohlaví.";
        }
        if (phase == 1) {
            return "\nS ostatními můžeš mluvit po výběru jména.";
        }
        if (parameters.length < 1) {
            return "\nA s kým chceš mluvit.";
        }
        if (parameters.length > 1) {
            return "\nMůžeš mluvit jen s jedním člověkem najednou.";
        }

        String npcName = parameters[0];
        Location currentLocation = gameState.getCurrentLocation();

        if (currentLocation.getItem(npcName) != null) {
            return "\nJeště nejsi na tolik šílený/á, aby si mluvl/a s předmětem...";
        }
        if (currentLocation.getNpc(npcName) == null) {
            return "\nNemůžeš mluvit s někým, kdo tu není.";
        }

        Npc npc = currentLocation.getNpc(npcName);

        if (!npc.getTalk()) {
            return "\nNení důvod.";
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
                return "\n" + npc.getChat(npc) + "\n Dědek na tebe zaútočil a způsobil ti " + npc.getStr() + " poškození. " +
                        "Zbývá ti " + player.getHp() + " zdraví.";
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
                    return "\n" + npc.getChat(npc) + "\nARMIN ti dal univerzální_klíč";
                } else {
                    return "\nArmin: Něco ti dám, ale uvolni si předtím místo v batohu.";
                }
            }
            return "\n" + npc.getChat(npc);
        }

        return "\nUž jste si povídali až až.";
    }
}
