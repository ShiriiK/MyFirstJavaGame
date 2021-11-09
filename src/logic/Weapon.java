package logic;

/**
 * Instance této třídy představují jednotlivé zbraně.
 * <p>
 * Toto rozhraní je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version 2021-05-26
 */

public class Weapon {
    private final String name;
    private final String displayName;
    private final double multiplicator;
    private final boolean locked;
    private final String race;

    /**
     * Konstruktor
     * @param name název zbraně
     * @param displayName zobrazovaný název zbraně
     * @param multiplicator mlutiplikátor
     * @param locked dostupnost
     */
    public Weapon(String name, String displayName, double multiplicator, boolean locked, String race) {
        this.name = name;
        this. displayName = displayName;
        this.multiplicator = multiplicator;
        this.locked = locked;
        this.race = race;
    }

    public String getDisplayName(){
        return displayName;
    }

    /**
     * Metoda pro získání názvu zbraně.
     * @return název zbraně
     */
    public String getName() {
        return name;
    }

    /**
     * Metoda pro získání multiplikátoru na zbraně.
     * @return multiplikátor
     */
    public double getMultiplicator() {
        return multiplicator;
    }

    /**
     * Metoda pro získání informace o dostupnosti zbraně.
     * @return true pokud není dostupná jinak false
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Metoda pro získání informace o tom, pro jakou rasu, je zbraň určena
     * @return
     */
    public String getRace() {
        return race;
    }
}
