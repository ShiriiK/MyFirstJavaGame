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
    private String name;
    private double multiplicator;
    private boolean locked;

    // Konstruktor
    public Weapon(String name, double multiplicator, boolean locked) {
        this.name = name;
        this.multiplicator = multiplicator;
        this.locked = locked;
    }

    /**
     * Metoda pro získání názvu zbraně.
     *
     * @return název zbraně
     */
    public String getName() {
        return name;
    }

    /**
     * Metoda pro získání multiplikátoru na zbraně.
     *
     * @return multiplikátor
     */
    public double getMultiplicator() {
        return multiplicator;
    }

    /**
     * Metoda pro získání informace o dostupnosti zbraně.
     *
     * @return true pokud není dostupná jinak false
     */
    public boolean isLocked() {
        return locked;
    }
}
