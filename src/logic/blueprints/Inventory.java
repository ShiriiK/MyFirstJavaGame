package logic.blueprints;

import gui.util.Observer;
import gui.util.SubjectOfChange;
import logic.Game;
import logic.blueprints.Item;

import java.util.*;

/**
 * Třída reprezentující inventář hráče a implementující rozhraní SubjectOfChange
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-11-06
 */

public class Inventory implements SubjectOfChange{
    private static final int MAX_ITEMS = 6; // maximální počet věcí v inventáři
    private final Map<String, Item> content; // seznam věcí v inventáři

    private final Set<Observer> observers = new HashSet<>();


    // Konstruktor
    public Inventory() {
        content = new HashMap<>();
    }

    /**
     * Metoda vrací kolekci všech itemů v inventáři.
     * @return kolekce itemů v inventáři
     */
    public Map<String, Item> getContent() {
        return content;
    }

    /**
     * Metoda pro zjištění, zda je v batohu ještě místo.
     * @return true - pokud je, false - pokud je inventář plný
     */
    public boolean isSpace() {
        return content.size() < MAX_ITEMS;
    }

    /**
     * Metoda pro vložení itemu do inventáře.
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
     * Metoda na vrácení odkazu na item podle jeho názvu.
     * @param name název věci
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
     * Odstraní item z inventáře.
     * @param name název itemu
     */
    public void removeItem(String name) {
        if (content.containsKey(name)) {
            content.remove(name);
            notifyObservers();
        }
    }

    /**
     * Metoda pro vrácení názvů itemů v inventáři.
     * @return set názvů itemů v intentáři
     */
    public Set<String> itemsInInventory() { return content.keySet(); }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }


}
