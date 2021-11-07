package logic;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Instance této třídy představují jednotlivé itemy.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class Item {
    private final String name;
    private final String displayName;
    private final boolean pickable;
    private final String description;
    private final Collection<Item> hiddenItems;

    /**
     * Konstruktor
     * @param name název itemu
     * @param displayName zobrazovaný název itemu
     * @param pickable informace o tom, zda ho lze dá do inventáře
     * @param description popis
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
     * Metoda pro získání jména itemu.
     * @return jméno itemu
     */
    public String getName() {
        return this.name;
    }

    /**
     * Metoda pro získání informace o tom, zda jde předmět vzít.
     * @return informace o přenositelnosti
     */
    public boolean isPickable() {
        return pickable;
    }

    /**
     * Metoda pro získání popisu itemu.
     * @return popis itemu
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Metoda pro vkládaní itemu do itemu.
     * @param inserted item v itemu
     */
    public void insertItem(Item inserted) {
        hiddenItems.add(inserted);
    }

    /**
     * Metoda pro získání itemu v itemu.
     * @param name název itemu, který je v jiném itemu
     * @return odkaz na item
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
     * Metoda pro odstranění itemu z itemu.
     * @param name název itemu, který má být z itemu vyndán
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
     * Metoda na zjištění zda je v itemu nějaký jiný item.
     * @return název itemu, který je v itemu, pokud tam nějaký je
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
