package logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Třída reprezentující inventář hráče.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class Inventory {
    private static final int MAX_ITEMS = 4; // maximální počet věcí v inventáři
    private List<Item> content; // seznam věcí v inventáři

    // Konstruktor
    public Inventory() {
        content = new ArrayList<>();
    }

    /**
     * Metoda vrací kolekci všech itemů v inventáři.
     *
     * @return kolekce itemů v inventáři
     */
    public Collection<Item> getContent() {
        return new ArrayList<>(content);
    }

    /**
     * Metoda pro zjištění, zda je v batohu ještě místo.
     *
     * @return true - pokud je, false - pokud je inventář plný
     */
    public boolean isSpace() {
        return content.size() < MAX_ITEMS;
    }

    /**
     * Metoda pro vložení itemu do inventáře.
     *
     * @param item který chceme vložit
     * @return null pokud nelze vložit, jinak odkaz na vložený item
     */
    public Item addItem(Item item) {
        if (isSpace() && item.isPickable()) {
            content.add(item);
            return item;
        }
        return null;
    }

    /**
     * Kontroluje, zda je daná věc v inventáři.
     *
     * @param item který chceme zkontrolovat
     * @return true pokud má item hráč v inventáři
     */
    public boolean containsItem(String item) {
        for (Item current : content) {
            if (current.getName().equals(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metoda pro vypsání obsahu inventáře.
     *
     * @return obsah inventáře
     */
    public String getInventory() {
        String text = "\nBatoh:";
        for (Item current : content) {
            if (!text.equals("\nBatoh:")) {
                text += ",";
            }
            text += " " + current.getName();
        }
        return text;
    }

    /**
     * Metoda najde věc, kterou chceme.
     *
     * @param name název věci, kterou chceme
     * @return odkaz na věc, pokud nebyla nalezena, tak se vrátí null
     */
    public Item getItem(String name) {
        Item item = null;
        for (Item current : content) {
            if (current.getName().equals(name)) {
                item = current;
                break;
            }
        }
        return item;
    }

    /**
     * Odstraní item z inventáře, ale nevloží ho aktulní lokace.
     * Item po odstranění může být předán nějakému npc nebo položen do aktuální lokace.
     *
     * @param name název itemu, který chceme odstranit
     */
    public void removeItem(String name) {
        for (Item current : content) {
            if (current.getName().equals(name)) {
                content.remove(current);
                break;
            }
        }
    }
}
