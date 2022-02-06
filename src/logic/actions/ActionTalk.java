package logic.actions;

import gui.util.Constants;
import logic.*;
import logic.blueprints.Inventory;
import logic.blueprints.Item;
import logic.blueprints.Location;
import logic.blueprints.Npc;
import saving_tue.Main;

import java.util.Arrays;

/**
 * Class implementing the command for talking to npc in game.
 * @author Alena Kalivodová
 */

public class ActionTalk implements IAction {
    private final String[] names = {"talk", "talk_to"};

    /**
     * The method used to identify the validity of commands.
     * @return possible command names
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 2);
    }

    /**
     * Executes talk command - talks to npc.
     * @param parameters one parameter - the name of the npc the player wants to talk to
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = Main.game.getGameState();
        PhaseChecker.basicChecker();

        if (parameters.length < 1) {
            return Constants.d1 + "And who do you want to talk to?" + Constants.d2;
        }
        if (parameters.length > 1) {
            return Constants.d1 + "You can only talk to one person at a time." + Constants.d2;
        }

        String npcName = parameters[0];
        Location currentLocation = gameState.getCurrentLocation();

        if (currentLocation.getItem(npcName) != null) {
            return Constants.d1 + "You're not crazy enough to talk to the object..." + Constants.d2;
        }
        if (currentLocation.getNpc(npcName) == null) {
            return Constants.d1 + "You can't talk to someone who isn't here." + Constants.d2;
        }

        Npc npc = currentLocation.getNpc(npcName);

        if (!npc.getTalk()) {
            return Constants.d1 + "There's no reason to..." + Constants.d2;
        }

        int talked = npc.getTalked();

        if (!npc.getTalks().isEmpty()) {
            if (talked == 2 && npcName.equals("general")) {
                Main.game.setTheEnd(true);
            }
            if (talked == 2 && npcName.equals("guard")) {
                Main.game.setTheEnd(true);
            }
            if (talked == 2 && npcName.equals("guard")) {
                Main.game.setTheEnd(true);
                return npc.getChat(npc);
            }

            Inventory inventory = Main.game.getGameState().getInventory();

            if (talked == 1 && npcName.equals("passage_guard")) {
                if (inventory.isSpace()) {
                    Item item = npc.getItemInNpc("master_key");
                    npc.removeItemInNpc("master_key");
                    inventory.addItem(item);
                    return "\n" + npc.getChat(npc) + Constants.d1 + "Armin gave you the master_key" + Constants.d2;
                } else {
                    return "\nArmin: I'll give you something, but make room in your backpack.";
                }
            }
            return Constants.d1 + npc.getChat(npc) + Constants.d2;
        }

        return Constants.d1 + "You've talked enough." + Constants.d2;
    }
}
