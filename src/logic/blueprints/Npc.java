package logic.blueprints;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Instances of this class represent individual npcs.
 *
 * @author Alena Kalivodová
 */
public class Npc {
    private final String name;
    private final String displayName;
    private final boolean friendly;
    private double hp;
    private final double str;
    private final Collection<Item> npcItems;
    private final List<String> talks;
    private int talked;
    private final boolean talk;
    private final String message;

    /**
     * Skeleton for npc
     * @param name name npc
     * @param displayName display name of npc
     * @param friendly specifies whether the npc can be fought (true - cannot, false - can)
     * @param hp npc lives
     * @param str npc strength
     * @param talk specifies whether the npc can be talked to (true - goes, false - doesn't)
     * @param talks all possible conversations with npc
     * @param message a message that is returned when the npc somehow prevents the transition between locations
     */
    public Npc(String name, String displayName, boolean friendly, double hp, double str, boolean talk, List talks, String message) {
        this.name = name;
        this.displayName = displayName;
        this.friendly = friendly;
        this.hp = hp;
        this.str = str;
        this.talk = talk;
        this.talks = talks;
        this.talked = 0;
        this.message = message;
        this.npcItems = new ArrayList<>();
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Method to get npc name.
     * @return npc name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to get npc attitude towards a player.
     * @return true if it is a friendly npc and false if it is hostile
     */
    public boolean isFriendly() {
        return friendly;
    }

    /**
     * Method for plugging hp npc.
     * @return hp npc
     */
    public double getHp() {
        return hp;
    }

    /**
     * Method for setting up hp npc.
     * @param hp npc
     */
    public void setHp(double hp) {
        this.hp = hp;
    }

    /**
     * Method for obtaining str npc.
     * @return str npc.
     */
    public double getStr() {
        return str;
    }

    /**
     * Method for inserting item into npc.
     * @param item to be added to npc
     */
    public void insertItem(Item item) {
        npcItems.add(item);
    }

    /**
     * Method for getting a reference to an item in npc.
     * @param name item name
     * @return reference to item and if it has none, null
     */
    public Item getItemInNpc(String name) {
        Item item = null;
        for (Item current : npcItems) {
            if (current.getName().equals(name)) {
                item = current;
                break;
            }
        }
        return item;
    }

    /**
     * Method to remove item from npc.
     * @param name name of the item that npc has
     */
    public void removeItemInNpc(String name) {
        for (Item current : npcItems) {
            if (current.getName().equals(name)) {
                npcItems.remove(current);
                break;
            }
        }
    }

    /**
     * The method returns information about whether the npc can be talked to.
     * @return true if you can and false if you can't
     */
    public boolean getTalk() {
        return talk;
    }

    /**
     * Method to get a list of all responses of a specific npc.
     * It is used to get information whether it is possible to talk to an npc.
     * @return a list of all replies from a given npc
     */
    public List<String> getTalks() {
        return talks;
    }

    /**
     * Method to get part of the conversation from npc.
     * @return returns the conversation based on how many times the player has already spoken to the npc
     */
    public String getChat(Npc npc) {
        int i = npc.getTalked();
        if (talked == talks.size()) {
            return "I've had enough of this chatter.";
        }
        talked ++;
        return talks.get(i);
    }

    /**
     * Method to get information about how many times a call to npc has already taken place.
     * @return number of times the npc has been spoken to
     */
    public int getTalked() {
        return talked;
    }

    /**
     * Method for setting the value of how many times npc has spoken.
     * @param talked how many times npc talked
     */
    public void setTalked(int talked) {
        this.talked = talked;
    }

    /**
     * Method for getting a message if the entry to (or exit from) a location is somehow restricted.
     * @return message to player
     */
    public String getMessage() {
        return message;
    }
}
