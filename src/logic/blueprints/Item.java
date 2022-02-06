package logic.blueprints;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Instances of this class represent individual items.
 * @author Alena Kalivodová
 */

public class Item {
    private final String name;
    private final String displayName;
    private final boolean pickable;
    private final String description;
    private final Collection<Item> hiddenItems;

    /**
     * Constructor
     * @param name item name
     * @param displayName display name of the item
     * @param pickable information about whether it can be put into inventory
     * @param description description
     */
    public Item(String name, String displayName, boolean pickable, String description) {
        this.name = name;
        this.displayName = displayName;
        this.pickable = pickable;
        this.description = description;
        this.hiddenItems = new ArrayList<>();
    }

    public String getDisplayName(){
        return displayName;
    }

    /**
     * Method to get the name of the item.
     * @return item name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method for obtaining information about whether an object can be taken.
     * @return portability information
     */
    public boolean isPickable() {
        return pickable;
    }

    /**
     * Method for obtaining the item description.
     * @return item description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Method for inserting item into item.
     * @param inserted item in item
     */
    public void insertItem(Item inserted) {
        hiddenItems.add(inserted);
    }

    /**
     * Method for obtaining item in item.
     * @param name name of the item that is in another item
     * @return reference to item
     */
    public Item getItemInItem(String name) {
        Item item = null;
        for (Item current : hiddenItems) {
            if (current.getName().equals(name)) {
                item = current;
                break;
            }
        }
        return item;
    }

    /**
     * Method to remove item from item.
     * @param name name of the item to be removed from the item
     */
    public void removeItemInItem(String name) {
        for (Item current : hiddenItems) {
            if (current.getName().equals(name)) {
                hiddenItems.remove(current);
                break;
            }
        }
    }

    /**
     * Method to find out if there is another item in the item.
     * @return the name of the item that is in the item, if there is one
     */
    public String containedItem() {
        String contains = null;
        if (!hiddenItems.isEmpty()) {
            for (Item current : hiddenItems) {
                contains = current.getName();
            }
        }
        return contains;
    }

}
