package logic;

import java.util.HashMap;
import java.util.Map;

/**
 * Instance této třídy představují exity z lokací.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */
public class Exit {
    private Location targetLocation;
    private Map<Npc, String> watchingNpcs;

    /**
     * Konstruktor
     *
     * @param targetLocation cílová lokace
     */
    public Exit(Location targetLocation) {
        this.targetLocation = targetLocation;
        watchingNpcs = new HashMap<>();
    }

    /**
     * Metoda pro přidání npc do průchodu.
     *
     * @param npc přidané npc
     */
    public void insertNpc(Npc npc) {
        watchingNpcs.put(npc, npc.getName());
    }

    /**
     * Metoda pro odstanění npc z průchodu.
     *
     * @param npc odstraněné npc.
     */
    public void removeWatchingNpc(Npc npc) {
        watchingNpcs.remove(npc);
    }

    /**
     * Metoda pro získání odkazu na lokaci, do které průchod směřuje.
     *
     * @return cílová lokace
     */
    public Location getTargetLocation() {
        return targetLocation;
    }

    /**
     * Metoda pro získání informace o tom, zda průchod obsahuje npc.
     *
     * @param npc npc, které chceme zjistit, jestli je v průchodu
     * @return odkaz na npc, pokud tak je a null pokud není
     */
    public Npc containsNpc(Npc npc) {
        if (watchingNpcs.containsKey(npc)) {
            return npc;
        }
        return null;
    }

    /**
     * Metoda pro zobrazení zprávy o poškození, která hráč utrpěl při přechodu mezi lokacemi.
     *
     * @return zprávy o poškození
     */
    public String getDamageMessage() {
        return "Ztratil/a jsi " + getDamage() + " zdraví.";
    }

    /**
     * Metoda pro získání infromace o tom, kolik v lokaci npc uštědří poškození.
     *
     * @return poškození
     */
    public double getDamage() {
        return targetLocation.npcAttack();
    }
}
