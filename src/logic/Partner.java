package logic;

/**
 * Třída představující partnera hráče.
 * <p>
 * Toto rozhraní je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class Partner {
    private String partnerName;
    private Weapon partnerWeapon;
    private double hp;
    private double str;
    private double dex;


    // Konstruktor
    public Partner(String partnerName, Weapon partnerWeapon, double hp, double str, double dex) {
        this.partnerName = partnerName;
        this.partnerWeapon = partnerWeapon;
        this.hp = hp;
        this.str = str;
        this.dex = dex;
    }

    /**
     * Metoda pro získání statů partnera.
     *
     * @return informace o partnerovi
     */
    public String getPartner() {
        if (getPartnerWeapon() == null) {
            return null;
        }
        return "\nJméno: " + partnerName + "\n" +
                "Zbraň: " + getPartnerWeapon().getName() + "\n" +
                "Životy: " + hp + "\n" +
                "Síla: " + str * (getPartnerWeapon().getMultiplicator());
    }

    /**
     * Metoda pro získání jména partnera.
     *
     * @return jméno partnera
     */
    public String getPartnerName() {
        return partnerName;
    }

    /**
     * Metoda pro nastavení jména partnera.
     *
     * @param partnerName jméno partnera
     */
    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    /**
     * Metoda pro získání hp partnera.
     *
     * @return hp partnera
     */
    public double getHp() {
        return hp;
    }

    /**
     * Metoda pro nastavení hp partnera.
     *
     * @param hp partnera
     */
    public void setHp(double hp) {
        this.hp = hp;
    }

    /**
     * Metoda pro získání str partnera.
     *
     * @return str partnera
     */
    public double getStr() {
        return str * getPartnerWeapon().getMultiplicator();
    }

    /**
     * Metoda pro nastavení str partnera.
     *
     * @param str pastnera
     */
    public void setStr(double str) {
        this.str = str;
    }

    /**
     * Metoda pro získání dex partnera
     *
     * @return dex partnera
     */
    public double getDex() {
        return dex;
    }

    /**
     * Metoda pro nastavení dex partnera
     *
     * @param dex partnera
     */
    public void setDex(double dex) {
        this.dex = dex;
    }

    /**
     * Metoda pro získání zbraně partnera.
     *
     * @return zbraň partnera
     */
    public Weapon getPartnerWeapon() {
        return partnerWeapon;
    }

    /**
     * Metoda pro nastavení zbraně partnera.
     *
     * @param partnerWeapon partnera
     */
    public void setPartnerWeapon(Weapon partnerWeapon) {
        this.partnerWeapon = partnerWeapon;
    }
}
