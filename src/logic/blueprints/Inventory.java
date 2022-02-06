package logic.blueprints;

import gui.util.Constants;
import gui.util.Observer;
import gui.util.SubjectOfChange;
import logic.Game;

import java.util.*;

/**
 * Class representing the player inventory and implementing the SubjectOfChange interface
 *
 * @author Alena Kalivodová
 */

public class Inventory implements SubjectOfChange{
    private static final int MAX_ITEMS = 6; // maximum number of items in inventory
    private final Map<String, Item> content; // list of things in inventory
    private final Set<Observer> observers = new HashSet<>();

    public Inventory() {
        content = new HashMap<>();
    }

    /**
     * The method returns the collection of all items in the inventory.
     * @return collection of items in inventory
     */
    public Map<String, Item> getContent() {
        return content;
    }

    /**
     * A method to determine if there is still room in the inventory.
     * @return true - if there is, false - if the inventory is full
     */
    public boolean isSpace() {
        return content.size() < MAX_ITEMS;
    }

    /**
     * Method for inserting item into inventory.
     * @param item we want to insert
     * @return null if cannot insert, otherwise link to inserted item
     */
    public Item addItem(Item item) {
        if (isSpace() && item.isPickable()) {
            content.put(item.getName(), item);
            notifyObservers();
            return item;
        }
        return null;
    }

    /**
     * Method for listing inventory contents.
     * @return inventory contents
     */
    public String getInventory() {
        String text =  Constants.d1 + "Inventory:";
        for (String name : content.keySet()) {
            if (!text.equals("\nInventory:")) {
                text += ",";
            }
            text += " " + name;
        }
        return text + Constants.d2;
    }

    /**
     * Method to return a link to an item by its name.
     * @param name item name
     * @return reference to the item, if not found, null is returned
     */
    public Item getItem(String name) {
        Item item = null;
        if(content.containsKey(name)) {
            item = content.get(name);
        }
        return item;
    }

    /**
     * Removes an item from your inventory.
     * @param name item name
     */
    public void removeItem(String name) {
        if (content.containsKey(name)) {
            content.remove(name);
            notifyObservers();
        }
    }

    /**
     * Method for returning item names in inventory.
     * @return set of item names in inventory
     */
    public Set<String> itemsInInventory() { return content.keySet(); }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }


}
