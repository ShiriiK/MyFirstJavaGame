package logic.actions;

import logic.*;
import logic.blueprints.Inventory;
import logic.blueprints.Item;
import logic.blueprints.Location;
import logic.blueprints.Npc;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro mluvení s npc ve hře.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-16
 */

public class ActionTalk implements IAction {
    private final Game game;
    private final String[] names = {"mluv_s", "promluv_si_s"};

    //Konstruktor
    public ActionTalk(Game game) {
        this.game = game;
    }

    /**
     * Metoda použitá pro identifikování platnosti příkazů.
     * @return možné názvy příkazů
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 2);
    }

    /**
     * Provádí příkaz talk - promluví si s npc.
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

        int talked = npc.getTalked();

        if (!npc.getTalks().isEmpty()) {
            if (talked == 2 && npcName.equals("generál")) {
                game.setTheEnd(true);
            }
            if (talked == 2 && npcName.equals("stráž")) {
                game.setTheEnd(true);
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
            return d1 + npc.getChat(npc) + d2;
        }

        return d1 + "Už jste si povídali až až." + d2;
    }
}
