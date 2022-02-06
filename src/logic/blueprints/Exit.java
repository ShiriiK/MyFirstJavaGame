package logic.blueprints;

import java.util.HashMap;
import java.util.Map;

/**
 * Instances of this class represent exits from locations.
 * @author Alena Kalivodová
 */
public class Exit {
    private final Location targetLocation;
    private final Map<Npc, String> watchingNpcs;

    public Exit(Location targetLocation) {
        this.targetLocation = targetLocation;
        watchingNpcs = new HashMap<>();
    }

    /**
     * Method for adding npc to exit.
     * @param npc added npc
     */
    public void insertNpc(Npc npc) {
        watchingNpcs.put(npc, npc.getName());
    }

    /**
     * Method for removing npc from exit.
     * @param npc removed npc
     */
    public void removeWatchingNpc(Npc npc) {
        watchingNpcs.remove(npc);
    }

    /**
     * Method to get a reference to the location to which the exit is directed.
     * @return destination location
     */
    public Location getTargetLocation() {
        return targetLocation;
    }

    /**
     * Method to get information about whether exit contains npc.
     * @param npc npc that we want to find out if it is in exit
     * @return reference to npc if it is and null if it is not
     */
    public Npc containsNpc(Npc npc) {
        if (watchingNpcs.containsKey(npc)) {
            return npc;
        }
        return null;
    }

    /**
     * A method to display a report of the damage the player has taken when moving between locations.
     * @return damage reports
     */
    public String getDamageMessage() {
        return "You've lost " + getDamage() + " hp.";
    }

    /**
     * A method to get information about how much damage an npc deals in a location.
     * @return damage
     */
    public double getDamage() {
        return targetLocation.npcAttack();
    }
}
