package logic.blueprints;

/**
 * A class representing a player's partner.
 * @author Alena Kalivodová
 */

public class Partner {
    private String partnerName;
    private Weapon partnerWeapon;
    private int hp;
    private int str;

    /**
     * Constructor
     * @param partnerName name
     * @param partnerWeapon weapon
     * @param hp lives
     * @param str strength
     */
    public Partner(String partnerName, Weapon partnerWeapon, int hp, int str) {
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
    public int getHp() {
        return hp;
    }

    /**
     * Method for setting up hp partner.
     * @param hp partner
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Method for obtaining a str partner.
     * @return str partner
     */
    public int getStr() {
        return str * getPartnerWeapon().getMultiplicator();
    }

    /**
     * Method for setting the partner page.
     * @param str pastner
     */
    public void setStr(int str) {
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
