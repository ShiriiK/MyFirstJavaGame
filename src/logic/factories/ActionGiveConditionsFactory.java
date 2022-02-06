package logic.factories;

import gui.util.Constants;
import logic.blueprints.Inventory;
import logic.blueprints.Item;
import logic.blueprints.Location;
import logic.blueprints.Npc;
import saving_tue.Main;

public class ActionGiveConditionsFactory {

    /**
     * Methond for checking if you can give item to npc
     * @param npcName name of npc
     * @param itemName name of item
     * @return message for player
     */
    public static String confirmFeasibility(String npcName, String itemName){
        Location currentLocation = Main.game.getGameState().getCurrentLocation();
        Npc npc = currentLocation.getNpc(npcName);
        Inventory inventory = Main.game.getGameState().getInventory();
        Item item = inventory.getItem(itemName);

        if (npcName.equals("gorm") && itemName.equals("shinning_rock")) {
            npc.insertItem(item);
            inventory.removeItem(itemName);
            return Constants.d1 + "You gave Gorm the shining rock.." + Constants.d2 +
                    "Gorm: That's just what I needed to complete the weapons, you can have one of the better ones now.";
        } else if (npcName.equals("general") && itemName.equals("bag")) {
            npc.insertItem(item);
            inventory.removeItem(itemName);
            currentLocation.getExit("gate").removeWatchingNpc(npc);
            currentLocation.getExit("ghetto").removeWatchingNpc(npc);
            currentLocation.getExit("entrance").removeWatchingNpc(npc);
            currentLocation.getExit("street").removeWatchingNpc(npc);
            return Constants.d1 + "You gave the General the bag full of coins." + Constants.d2 +
                    "General: That's what I call a deal. Now get out so I don't have to look at you anymore.";
        }
        if (npcName.equals("gate_guard") && itemName.equals("permit")) {
            npc.insertItem(item);
            inventory.removeItem(itemName);
            currentLocation.getExit("city").removeWatchingNpc(npc);
            return Constants.d1 + "You gave the guard a permit." + Constants.d2 + "Guard: Your permit looks fine. You may enter the city.";
        }
        if (npcName.equals("general") && itemName.equals("rock")) {
            Main.game.setTheEnd(true);
            return Constants.d1 + "You gave the General a rock." + Constants.d2 + "General: ...Get rid of them." + Main.game.epilog();
        }
        if (npcName.equals("guard") && itemName.equals("bag")) {
            inventory.removeItem(itemName);
            currentLocation.removeNpc("guard");
            return Constants.d1 + "You gave the guard a bag full of coins." + Constants.d2 +
                    "Guard: Woahhhhh. Thank you youngters, that's the kind of respect for elders I appreciate. " +
                    "Would you guys watch this for me for a minute?" +
                    " I'm just gonna jump on one and I'll be right back.";
        }
        if (npcName.equals("guard") && itemName.equals("bear")) {
            inventory.removeItem(itemName);
            currentLocation.removeNpc("guard");
            return Constants.d1 + "You gave the guard a beer." + Constants.d2 +
                    "Guard: Beer, oh yeah. Would you guys watch this place for a minute instead of me?" +
                    " I'm just gonna take a walk, and I'll be right back.";
        }
        if (npcName.equals("girl") && itemName.equals("pillow")) {
            npc.setTalked(3);
            inventory.removeItem(itemName);
            Item itemK = npc.getItemInNpc("stick");
            npc.removeItemInNpc("stick");
            inventory.addItem(itemK);
            return Constants.d1 + "You gave a little girl a pillow." + Constants.d2 + "Awwwwwwwwwwwwwwwwww. " +
                    "What a totally awesome furry pillow!!! Are you really just going to give it to me?" +
                    Constants.d1+ "The little girl gave you a stick in return." + Constants.d2;
        }
        if (npcName.equals("beggar") && itemName.equals("coin")) {
            npc.setTalked(3);
            Location entrance = Main.game.getGameState().getCurrentLocation().getExit("entrance").getTargetLocation();
            entrance.addItem(ItemsFactory.beer);
            inventory.removeItem(itemName);
            return Constants.d1 + "You gave the beggar a coin." + Constants.d2 +
                    "Thank you, thanks. Otherwise I'm not stupid, I know very well who you are and who you're looking for. " +
                    "So information for you. The entrance to the underground prison is guarded only by " +
                    "a grumpy old man who loves booze more than anything in the world. He's always hiding " +
                    "at least one jug of beer nearby, now that you know about it, I'm sure you'll find it.";
        }
        if (npcName.equals("beggar") && itemName.equals("bread")) {
            npc.setTalked(3);
            inventory.removeItem(itemName);
            return Constants.d1 + "You gave the beggar bread." + Constants.d2 +
                    "Well, still better than nothing... In exchange for it, I can tell you that the nearby mountains that are outside of town" +
                    " they say someone once hid a treasure.";
        }
        if (npcName.equals("tue") && itemName.equals("stick")) {
            Main.game.setTheEnd(true);
            inventory.removeItem(itemName);
            return Constants.d1 + "You gave Tue a stick." + Constants.d2 +
                    "Tue: Sttt--iii-ck? Are you really just gonna give me a stick???" +
                    "///Tue had just enough strength left to keep herself sane after a few days in this horrible place. " +
                    "Your offer of the stick gave her a shock that knocked her out of her senses. " +
                    "\nTue grabbed a stick and stabbed you with it with all her might." +
                    Main.game.epilog();

        }
        return Constants.d1 + "You can't give this character." + Constants.d2;
    }
}
