package logic.blueprints;

import gui.util.Constants;
import gui.util.Observer;
import gui.util.SubjectOfChange;

import java.util.HashSet;
import java.util.Set;

/**
 * Instances of this class represent individual locations.
 * @author Alena Kalivodová
 */

public class Location implements SubjectOfChange {
    private final String name;
    private final String displayName;
    private final String description;
    private final Set<Exit> exits;                     //  list of neighbouring locations
    private final Set<Item> items;                    //  list of things in the location
    private final Set<Npc> npcs;                     //  list of characters in the location
    private final Set<Weapon> weapons;
    private final int phase;
    private boolean known;

    private static final Set<Observer> observers = new HashSet<>();

    /**
     * Constructor
     * @param name location name
     * @param displayName the displayed location name
     * @param phase phase in which the location can be entered
     */
    public Location(String name, String displayName, String description, int phase) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.known = false;
        this.phase = phase;
        exits = new HashSet<>();
        items = new HashSet<>();
        npcs = new HashSet<>();
        weapons = new HashSet<>();
    }

    public String getDisplayName(){
        return displayName;
    }

    public int getPhase() {
        return phase;
    }

    /**
     * Method to get the location name.
     * @return location name
     */
    public String getName() {
        return name;
    }


    /**
     * Method that returns a description of the location.
     * Takes into account whether the player has already visited it.
     * @return information about where the player is and, if it is the first time, a description of the location
     */
    public String longDescription() {
        if (known) {
            return Constants.d1 +"Current location: " + name + Constants.d2;
        } else {
            this.known = true;
            return Constants.d1 + "Current location: " + name + "\n" +
                     description + Constants.d2;
        }
    }

    /**
     * Method that returns location
     */
    public  String getDescription() {
        return  description;
    }

    /**
     * Method for inserting npc into location.
     * @param npc npc to be inserted into the location
     */
    public void addNpc(Npc npc) {
        npcs.add(npc);
    }


    /**
     * Method to get a link to npc.
     * @param name name of npc
     * @return reference to npc if it is in location, otherwise null
     */
    public Npc getNpc(String name) {
        Npc npc = null;
        for (Npc current : npcs) {
            if (current.getName().equals(name)) {
                npc = current;
                break;
            }
        }
        return npc;
    }

    /**
     * Method to get the npc names that are in the location.
     * @return npcs names if there are any in the location
     */
    public String npcDescription() {
        String text = "";
        if (!npcs.isEmpty()) {
            text = "\nNpcs: ";
            for (Npc npc : npcs) {
                if (!text.equals("\nNpcs: ")) {
                    text += ",";
                }
                text += " " + npc.getName();
            }
        }
        return text;
    }

    /**
     * Method to remove npc from location.
     * @param name name of the npc to be removed from the location
     */
    public void removeNpc(String name) {
        for (Npc current : npcs) {
            if (current.getName().equals(name)) {
                npcs.remove(current);
                notifyObservers();
                break;
            }
        }
    }

    /**
     * A method to attack all npcs in a location on a player.
     * @return damage that together will cause
     */
    public int npcAttack() {
        int dmg = 0;
        if (!npcs.isEmpty()) {
            for (Npc current : npcs) {
                if (!current.isFriendly()) {
                    dmg += current.getStr();
                }
            }
        }
        return dmg;
    }

    /**
     * Method for adding an exit from a location.
     * @param exit of the location to which the exit from the current location will be created
     */
    public void addExit(Exit exit) {
        exits.add(exit);
    }

    /**
     * Method to get the exit link.
     * @param exitName name targetLocation
     * @return exit reference
     */
    public Exit getExit(String exitName) {
        for (Exit exit : exits) {
            if (exit.getTargetLocation().getName().equals(exitName)) {
                return exit;
            }
        }
        return null;
    }

    /**
     * Method for getting the names of locations to which you can move from the current location.
     * @return list of exits
     */
    public String exitsDescription() {
        String text = "";
        if (!exits.isEmpty()) {
            text = "\nExits:";
            for (Exit current : exits) {
                if (!text.equals("\nExits:")) {
                    text += ",";
                }
                text += " " + current.getTargetLocation().getName();
            }
        }
        return text;
    }

    /**
     * Used to add items to a location.
     * @param added added item
     */
    public void addItem(Item added) {
        items.add(added);
        notifyObservers();
    }

    /**
     * The method returns a reference to the item.
     * @param name item name
     * @return reference to item or null
     */
    public Item getItem(String name) {
        Item item = null;
        for (Item current : items) {
            if (current.getName().equals(name)) {
                item = current;
                break;
            }
        }
        return item;
    }

    /**
     * Method returns a list of items.
     * @return item names
     */
    public String itemDescription() {
        String text = "";
        if (!items.isEmpty()) {
            text = "\nItems:";
            for (Item current : items) {
                if (!text.equals("\nItems:")) {
                    text += ",";
                }
                text += " " + current.getName();
            }
        }
        return text;
    }

    /**
     * The method removes the item from the location.
     * @param name item name
     */
    public void removeItem(String name) {
        for (Item current : items) {
            if (current.getName().equals(name) && current.isPickable()) {
                items.remove(current);
                notifyObservers();
                break;
            }
        }
    }

    /**
     * Method for inserting a weapon into a location.
     * @param added weapon added to location
     */
    public void addWeapon(Weapon added) {
        weapons.add(added);
    }


    /**
     * Returns a link to the weapon.
     * @param name weapon name
     * @return reference to weapon or null
     */
    public Weapon getWeapon(String name) {
        Weapon weapon = null;
        for (Weapon current : weapons) {
            if (current.getName().equals(name)) {
                weapon = current;
                notifyObservers();
                break;
            }
        }
        return weapon;
    }

    /**
     * The method returns a list of weapons.
     * @return names of weapons in the location
     */
    public String weaponDescription() {
        String text = "";
        if (!weapons.isEmpty()) {
            text = "\nWeapons:";
            for (Weapon current : weapons) {
                if (!text.equals("\nWeapons:")) {
                    text += ",";
                }
                text += " " + current.getName();
            }
        }
        return text;
    }

    /**
     * Method for removing a weapon from a location.
     * @param name weapon name
     */
    public void removeWeapon(String name) {
        for (Weapon current : weapons) {
            if (current.getName().equals(name)) {
                weapons.remove(current);
                notifyObservers();
                break;
            }
        }
    }

    /**
     * The method returns a collection of all npcs in the location.
     * @return collection of all npcs in the location
     */
    public Set<Npc> getNpcs() {
        return new HashSet<>(npcs);
    }

    /**
     * The method returns a collection of all exits.
     * @return collection of neighboring locations
     */
    public Set<Exit> getExits() {
        return new HashSet<>(exits);
    }

    /**
     * The method returns a collection of all neighboring locations.
     * @return set of neighboring locations
     */
    public Set<Location> getTargetLocations(){
        Set<Exit> exitsSet = getExits();
        Set<Location> locationsSet = new HashSet<>();
        for (Exit exit : exitsSet) {
            locationsSet.add(exit.getTargetLocation());
        }
        return locationsSet;
    }

    /**
     * The method returns a collection of all items in the location.
     * @return collection of all items in the location
     */
    public Set<Item> getItems() {
        return new HashSet<>(items);
    }

    /**
     * The method returns the collection of all weapons in the location.
     * @return collection of all weapons in the location
     */
    public Set<Weapon> getWeapons() {
        return new HashSet<>(weapons);
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }
}
