package logic;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Instance této třídy představují jednotlivé lokace.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-23
 */

public class Location {
    private String name;
    private String description;
    private boolean known;
    private Set<Exit> exits;                     //  seznam sousedních lokací
    private Set<Item> items;                    //  seznam věcí nacházejících se v lokaci
    private Set<Npc> npcs;                     //  seznam postav nacházejících se v lokaci
    private Set<Weapon> weapons;
    private int phase;

    /**
     * Konstruktor lokace
     *
     * @param name jméno lokace
     * @param description popis lokace
     */
    public Location(String name, String description, int phase) {
        this.name = name;
        this.description = description;
        this.known = false;
        this.phase = phase;
        exits = new HashSet<>();
        items = new HashSet<>();
        npcs = new HashSet<>();
        weapons = new HashSet<>();
    }

    public int getPhase() {
        return phase;
    }

    /**
     * Metoda pro získání názvu lokace.
     *
     * @return název lokace
     */
    public String getName() {
        return name;
    }


    /**
     * Metoda, která vrací popis lokace.
     * Bere v potaz, zda ji již hráč navštívil.
     *
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
     * Metoda pro vložení npc do lokace.
     *
     * @param npc npc, které má být do lokace vloženo
     */
    public void addNpc(Npc npc) {
        npcs.add(npc);
    }


    /**
     * Metoda pro získání odkazu na npc.
     *
     * @param name jméno npc
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
     *
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
     *
     * @param name jméno npc, které má být odstraněno z lokace
     */
    public void removeNpc(String name) {
        for (Npc current : npcs) {
            if (current.getName().equals(name)) {
                npcs.remove(current);
                break;
            }
        }
    }

    /**
     * Metoda pro zaútočení všech npc v lokaci na hráče.
     *
     * @return damage, který dohromady způsobí
     */
    public double npcAttack() {
        double dmg = 0;
        if (!npcs.isEmpty()) {
            for (Npc current : npcs) {
                if (!current.getFriendly()) {
                    dmg += current.getStr();
                }
            }
        }
        return dmg;
    }

    /**
     * Metoda pro přidání východu z lokace.
     *
     * @param exit lokace, do které bude vytvořen východ z aktuální lokace
     */
    public void addExit(Exit exit) {
        exits.add(exit);
    }

    /**
     * Metoda pro získání odkazu na exit.
     *
     * @param exitName jméno targetLocation
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
     *
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
     *
     * @param added přidaný item
     */
    public void addItem(Item added) {
        items.add(added);
    }


    /**
     * Metoda vrací odkaz na item.
     *
     * @param name jméno itemu
     * @return odkaz na item nebo null
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
     * Metoda vrací seznam itemů.
     *
     * @return jména itemů
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
     *
     * @param name jméno itemu
     */
    public void removeItem(String name) {
        for (Item current : items) {
            if (current.getName().equals(name) && current.isPickable()) {
                items.remove(current);
                break;
            }
        }
    }

    /**
     * Metoda pro vložení zbraně do lokace.
     *
     * @param added zbraň přidaná do lokace
     */
    public void addWeapon(Weapon added) {
        weapons.add(added);
    }


    /**
     * Vrátí odkaz na zbraň.
     *
     * @param name jméno zbraně
     * @return odkaz na zbraň nebo null
     */
    public Weapon getWeapon(String name) {
        Weapon weapon = null;
        for (Weapon current : weapons) {
            if (current.getName().equals(name)) {
                weapon = current;
                break;
            }
        }
        return weapon;
    }

    /**
     * Metoda vrací seznam zbraní.
     *
     * @return jména zbraní v lokaci
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
     *
     * @param name jméno zbraně
     */
    public void removeWeapon(String name) {
        for (Weapon current : weapons) {
            if (current.getName().equals(name) && !current.isLocked()) {// přidat podmínku pro lepší zbraně
                weapons.remove(current);
                break;
            }
        }
    }

    /**
     * Metoda vrací kolekci všech npc v lokaci.
     * Vyžívána testovací třídou CurrentLocationNpcsChecker.
     * Používá se i při přesuno do jiné lokace, kdy zjistí, jestli v průchodu mezi lokacemi jsou agresivní npc,
     * které na hráče zaútočí.
     *
     * @return kolekce všech npc v lokaci
     */
    public Collection<Npc> getNpcs() {
        return new HashSet<>(npcs);
    }

    /**
     * Metoda vrací kolekci všech sousedních lokací.
     * Vyžívána testovací třídou CurrentLocationExitsChecker a metodou getTargetLocations().
     *
     * @return kolekce sousedních lokací
     */
    public Set<Exit> getExits() {
        return new HashSet<>(exits);
    }

    /**
     * Metoda využitá gui třídou ExitPanel.
     *
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
     * Vyžívána testovací třídou CurrentLocationItemsChacker.
     *
     * @return kolekce všech itemů v lokaci
     */
    public Collection<Item> getItems() {
        return new HashSet<>(items);
    }

    /**
     * Metoda vrací kolekci všech zbraní v lokaci.
     * Vyžívána testovací třídou CurrentLocationWeaponsChecker.
     *
     * @return kolekce všech zbraní v lokaci
     */
    public Collection<Weapon> getWeapons() {
        return new HashSet<>(weapons);
    }
}
