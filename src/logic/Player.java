package logic;

/**
 * Třída představující hráče.
 * <p>
 * Toto rozhraní je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class Player {
    private String playerName;
    private String playerGender;
    private Weapon playerWeapon;
    private double hp;
    private double str;

    // Konstruktor
    public Player(String playerName, String playerGender, Weapon playerWeapon, double hp, double str) {
        this.playerName = playerName;
        this.playerGender = playerGender;
        this.playerWeapon = playerWeapon;
        this.hp = hp;
        this.str = str;
    }

    /**
     * Metoda pro získání statů hráče.
     *
     * @return informace o hráčovi
     */
    public String getPlayer() {
        if (getPlayerWeapon() == null) {
            return null;
        }
        return "\nPohlaví: " + playerGender + "\n" +
                "Jméno: " + playerName + "\n" +
                "Zbraň: " + getPlayerWeapon().getName() + "\n" +
                "Životy: " + hp + "\n" +
                "Síla: " + str * (getPlayerWeapon().getMultiplicator());
    }

    /**
     * Metoda pro získání jména hráče.
     *
     * @return jméno hráče
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Metoda pro nastavení jména hráče.
     *
     * @param playerName jméno hráče
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Metoda pro získání pohlaví hráče.
     *
     * @return male/female
     */
    public String getPlayerGender() {
        return playerGender;
    }

    /**
     * Metoda pro nastavení pohlaví hráče.
     *
     * @param playerGender male/female
     */
    public void setPlayerGender(String playerGender) {
        this.playerGender = playerGender;
    }

    /**
     * Metoda pro získání zbraně hráče.
     *
     * @return zbraň hráče
     */
    public Weapon getPlayerWeapon() {
        return playerWeapon;
    }

    /**
     * Metoda pro nastavení zbraně hráče.
     *
     * @param playerWeapon hráče
     */
    public void setPlayerWeapon(Weapon playerWeapon) {
        this.playerWeapon = playerWeapon;
    }

    /**
     * Metoda pro získání hp hráče.
     *
     * @return hp hráče
     */
    public double getHp() {
        return hp;
    }

    /**
     * Metoda pro nastavení hp hráče.
     *
     * @param hp hp hráče
     */
    public void setHp(double hp) {
        this.hp = hp;
    }

    /**
     * Metoda pro získání str hráče.
     *
     * @return str hráče
     */
    public double getStr() {
        return str * (getPlayerWeapon().getMultiplicator());
    }

    /**
     * Metoda pro nastavení str hráče.
     *
     * @param str hráče
     */
    public void setStr(int str) {
        this.str = str;
    }
}
