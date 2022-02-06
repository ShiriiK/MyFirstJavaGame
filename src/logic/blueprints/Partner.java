package logic.blueprints;

/**
 * A class representing a player's partner.
 *
 * @author Alena Kalivodová
 */

public class Partner {
    private String partnerName;
    private Weapon partnerWeapon;
    private double hp;
    private double str;

    /**
     * Constructor
     * @param partnerName name
     * @param partnerWeapon weapon
     * @param hp lives
     * @param str strength
     */
    public Partner(String partnerName, Weapon partnerWeapon, double hp, double str) {
        this.partnerName = partnerName;
        this.partnerWeapon = partnerWeapon;
        this.hp = hp;
        this.str = str;
    }

    /**
     * Method for obtaining partner's stats.
     * @return partner information
     */
    public String getPartner() {
        return "\nName: " + partnerName + "\n" +
               "Weapon: " + getPartnerWeapon().getDisplayName() + "\n" +
               "Hp: " + hp + "\n" +
               "Str: " + str  + "\n" +
               "Race: viking";
        }

    /**
     * Method to get the name of the partner.
     * @return partner name
     */
    public String getPartnerName() {
        return partnerName;
    }

    /**
     * Method for setting the partner name.
     * @param partnerName partner name
     */
    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    /**
     * Method for getting a hp partner.
     * @return hp partner
     */
    public double getHp() {
        return hp;
    }

    /**
     * Method for setting up hp partner.
     * @param hp partner
     */
    public void setHp(double hp) {
        this.hp = hp;
    }

    /**
     * Method for obtaining a str partner.
     * @return str partner
     */
    public double getStr() {
        return str * getPartnerWeapon().getMultiplicator();
    }

    /**
     * Method for setting the partner page.
     * @param str pastner
     */
    public void setStr(double str) {
        this.str = str;
    }


    /**
     * A method for obtaining a partner's weapon.
     * @return partner's weapon
     */
    public Weapon getPartnerWeapon() {
        return partnerWeapon;
    }

    /**
     * Method for setting the partner's weapon.
     * @param partnerWeapon partner
     */
    public void setPartnerWeapon(Weapon partnerWeapon) {
        this.partnerWeapon = partnerWeapon;
    }
}
