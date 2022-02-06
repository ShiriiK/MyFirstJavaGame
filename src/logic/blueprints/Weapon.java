package logic.blueprints;

/**
 * Instances of this class represent individual weapons.
 *
 * @author Alena Kalivodová
 */

public class Weapon {
    private final String name;
    private final String displayName;
    private final double multiplicator;
    private final boolean locked;
    private final String race;
    private final double bonusDmg;
    private final double bonusBlock;
    private final double bonusSpecialAttack;
    private final double bonusCharge;

    /**
     * Constructor
     * @param name weapon name
     * @param displayName display name of the weapon
     * @param multiplicator multiplier
     * @param locked availability
     */
    public Weapon(String name, String displayName, double multiplicator, boolean locked, String race,
                  double bonusDmg, double bonusBlock, double bonusSpecialAttack, double bonusCharge) {
        this.name = name;
        this. displayName = displayName;
        this.multiplicator = multiplicator;
        this.locked = locked;
        this.race = race;
        this.bonusDmg = bonusDmg;
        this.bonusBlock = bonusBlock;
        this.bonusSpecialAttack = bonusSpecialAttack;
        this.bonusCharge = bonusCharge;
    }

    public String getDisplayName(){
        return displayName;
    }

    /**
     * Method to get the name of the weapon.
     * @return weapon name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to get weapon multiplier.
     * @return multiplier
     */
    public double getMultiplicator() {
        return multiplicator;
    }

    /**
     * Method for obtaining information about the availability of a weapon.
     * @return true if not available otherwise false
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Method for obtaining information about what race the weapon is intended for
     * @return race that can use the weapon
     */
    public String getRace() {
        return race;
    }

    /**
     * Method to get bonus damage to normal attack
     * @return bonus damage amount
     */
    public double getBonusDmg() {
        return bonusDmg;
    }

    /**
     * Method for getting a bonus block to an attack with an evasion
     * @return bonus block amount
     */
    public double getBonusBlock() {
        return bonusBlock;
    }

    /**
     * Method to get bonus damage for special attack
     * @return bonus damage amount
     */
    public double getBonusSpecialAttack() {
        return bonusSpecialAttack;
    }

    /**
     * Method to get bonus damage to charge attack
     * @return bonus damage amount
     */
    public double getBonusCharge() {
        return bonusCharge;
    }
}
