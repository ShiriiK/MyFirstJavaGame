package logic;

import util.Observer;
import util.SubjectOfChange;
import java.util.HashSet;
import java.util.Set;

/**
 * Instance této třídy představují jednotlivé lokace.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *;
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-01
 */

public class Location implements SubjectOfChange {
    private final String name;
    private final String displayName;
    private final String description;
    private final Set<Exit> exits;                     //  seznam sousedních lokací
    private final Set<Item> items;                    //  seznam věcí nacházejících se v lokaci
    private final Set<Npc> npcs;                     //  seznam postav nacházejících se v lokaci
    private final Set<Weapon> weapons;
    private final int phase;
    private boolean known;

    private static Set<Observer> observers = new HashSet<>();

    /**
     * Konstruktor
     * @param name název lokace
     * @param displayName zobrazovaný název lokace
     * @param phase fáze, ve které je možnost vstoupit do lokace
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

    public boolean isKnown() {
        return known;
    }

    public int getPhase() {
        return phase;
    }

    /**
     * Metoda pro získání názvu lokace.
     * @return název lokace
     */
    public String getName() {
        return name;
    }


    /**
     * Metoda, která vrací popis lokace.
     * Bere v potaz, zda ji již hráč navštívil.
     * @return infromace o tom, kde se hráč nachází a pokud je tamm poprvé, tak popis lokace
     */
    public String longDescription() {
        if (known) {
            return Game.makeItLookGood1() +"Aktuální lokace: " + name + Game.makeItLookGood2();
        } else {
            this.known = true;
            return Game.makeItLookGood1() + "Aktuální lokace: " + name + "\n" +
                     description + Game.makeItLookGood2();
        }
    }

    /**
     * @return metoada pro vrácení popisu lokace
     */
    public  String getDescription() {
        return  description;
    }

    /**
     * Metoda pro vložení npc do lokace.
     * @param npc npc, které má být do lokace vloženo
     */
    public void addNpc(Npc npc) {
        npcs.add(npc);
    }


    /**
     * Metoda pro získání odkazu na npc.
     * @param name název npc
     * @return odkaz na npc pokud je v lokaci, jinak null
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
     * Metoda pro získání jmen npc, které jsou v lokaci.
     * @return jména npcček pokud jsou v lokaci nějaká
     */
    public String npcDescription() {
        String text = "";
        if (!npcs.isEmpty()) {
            text = "\nPostavy:";
            for (Npc npc : npcs) {
                if (!text.equals("\nPostavy:")) {
                    text += ",";
                }
                text += " " + npc.getName();
            }
        }
        return text;
    }

    /**
     * Metoda pro odstranění npc z lokace.
     * @param name jméno npc, které má být odstraněno z lokace
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
     * Metoda pro zaútočení všech npc v lokaci na hráče.
     * @return damage, který dohromady způsobí
     */
    public double npcAttack() {
        double dmg = 0;
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
     * Metoda pro přidání východu z lokace.
     * @param exit lokace, do které bude vytvořen východ z aktuální lokace
     */
    public void addExit(Exit exit) {
        exits.add(exit);
    }

    /**
     * Metoda pro získání odkazu na exit.
     * @param exitName název targetLocation
     * @return odkaz na exit
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
     * Metoda pro získání názvů lokací do kterých se dá z aktuální lokace přemístit.
     * @return seznam exitů
     */
    public String exitsDescription() {
        String text = "";
        if (!exits.isEmpty()) {
            text = "\nVýchody:";
            for (Exit current : exits) {
                if (!text.equals("\nVýchody:")) {
                    text += ",";
                }
                text += " " + current.getTargetLocation().getName();
            }
        }
        return text;
    }

    /**
     * Slouží k přidání itemů do lokace.
     * @param added přidaný item
     */
    public void addItem(Item added) {
        items.add(added);
        notifyObservers();
    }

    /**
     * Metoda vrací odkaz na item.
     * @param name název itemu
     * @return odkaz na item nebo null
     */
    public Item getItem(String name) {
        Item item = null;
        for (Item current : items) {
            if (current.getName().equals(name)) {
                item = current;
                notifyObservers();
                break;
            }
        }
        return item;
    }

    /**
     * Metoda vrací seznam itemů.
     * @return názvy itemů
     */
    public String itemDescription() {
        String text = "";
        if (!items.isEmpty()) {
            text = "\nPředměty:";
            for (Item current : items) {
                if (!text.equals("\nPředměty:")) {
                    text += ",";
                }
                text += " " + current.getName();
            }
        }
        return text;
    }

    /**
     * Metoda odstraní item z lokace.
     * @param name název itemu
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
     * Metoda pro vložení zbraně do lokace.
     * @param added zbraň přidaná do lokace
     */
    public void addWeapon(Weapon added) {
        weapons.add(added);
    }


    /**
     * Vrátí odkaz na zbraň.
     * @param name název zbraně
     * @return odkaz na zbraň nebo null
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
     * Metoda vrací seznam zbraní.
     * @return názvy zbraní v lokaci
     */
    public String weaponDescription() {
        String text = "";
        if (!weapons.isEmpty()) {
            text = "\nZbraně:";
            for (Weapon current : weapons) {
                if (!text.equals("\nZbraně:")) {
                    text += ",";
                }
                text += " " + current.getName();
            }
        }
        return text;
    }

    /**
     * Metoda pro odebrání zbraně z lokace.
     * @param name název zbraně
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
     * Metoda vrací kolekci všech npc v lokaci.
     * @return kolekce všech npc v lokaci
     */
    public Set<Npc> getNpcs() {
        return new HashSet<>(npcs);
    }

    /**
     * Metoda vrací kolekci všech exitů.
     * @return kolekce sousedních lokací
     */
    public Set<Exit> getExits() {
        return new HashSet<>(exits);
    }

    /**
     * Metoda vrací kolekci všech sousedních lokací.
     * @return set sousedních lokací
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
     * Metoda vrací kolekci všech itemů v lokaci.
     * @return kolekce všech itemů v lokaci
     */
    public Set<Item> getItems() {
        return new HashSet<>(items);
    }

    /**
     * Metoda vrací kolekci všech zbraní v lokaci.
     * @return kolekce všech zbraní v lokaci
     */
    public Set<Weapon> getWeapons() {
        return new HashSet<>(weapons);
    }

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
        for (Observer o : observers) {
            o.update();
        }
    }
}
