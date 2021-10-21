package logic;

import util.Observer;
import util.SubjectOfChange;

import java.util.*;

/**
 * Třída reprezentující inventář hráče.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class Inventory implements SubjectOfChange{
    private static final int MAX_ITEMS = 4; // maximální počet věcí v inventáři
    private Map<String, Item> content; // seznam věcí v inventáři

    private Set<Observer> observerSet = new HashSet<>();


    // Konstruktor
    public Inventory() {
        content = new HashMap<String, Item>();
    }

    /**
     * Metoda vrací kolekci všech itemů v inventáři.
     *
     * @return kolekce itemů v inventáři
     */
    public Map<String, Item> getContent() {
        return content;
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
            content.put(item.getName(), item);
            notifyObservers();
            return item;
        }
        return null;
    }

    /**
     * Metoda pro vypsání obsahu inventáře.
     *
     * @return obsah inventáře
     */
    public String getInventory() {
        String text =  Game.makeItLookGood1() + "Batoh:";
        for (String name : content.keySet()) {
            if (!text.equals("\nBatoh:")) {
                text += ",";
            }
            text += " " + name;
        }
        return text + Game.makeItLookGood2();
    }

    /**
     * Metoda najde věc, kterou chceme.
     *
     * @param name název věci, kterou chceme
     * @return odkaz na věc, pokud nebyla nalezena, tak se vrátí null
     */
    public Item getItem(String name) {
        Item item = null;
        if(content.containsKey(name)) {
            item = content.get(name);
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
        Item item = null;
        if (content.containsKey(name)) {
            item = content.get(name);
            content.remove(name);
            notifyObservers();
        }
    }

    @Override
    public void registerObserver(Observer observer) {
        observerSet.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        observerSet.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observerSet) {
            observer.update();
        }
    }

    public Set<String> itemsInInventory() { return content.keySet(); }
}
