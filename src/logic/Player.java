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
    private Race race;

    /**
     * Konstruktor
     * @param playerName jméno
     * @param playerGender pohlaví
     * @param playerWeapon zbraň
     * @param hp životy
     * @param str síla
     * @param race rasa
     */
    public Player(String playerName, String playerGender, Weapon playerWeapon, double hp, double str, Race race) {
        this.playerName = playerName;
        this.playerGender = playerGender;
        this.playerWeapon = playerWeapon;
        this.hp = hp;
        this.str = str;
        this.race = race;
    }

    /**
     * Metoda pro získání statů hráče.
     * @return informace o hráčovi
     */
    public String getPlayer() {
        if (getPlayerWeapon() == null) {
            return "\nPohlaví: " + playerGender + "\n" +
                    "Jméno: " + playerName + "\n" +
                    "Rasa: " + race.getName() + "\n" +
                    "Zbraň: \n" +
                    "Životy: " + hp + "\n" +
                    "Síla: " + str ;
        }
        return "\nPohlaví: " + playerGender + "\n" +
                "Jméno: " + playerName + "\n" +
                "Rasa: " + race.getName() + "\n" +
                "Zbraň: " + getPlayerWeapon().getName() + "\n" +
                "Životy: " + hp + "\n" +
                "Síla: " + str * (getPlayerWeapon().getMultiplicator());
    }

    /**
     * Metoda pro získání jména hráče.
     * @return jméno hráče
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Metoda pro nastavení jména hráče.
     * @param playerName jméno hráče
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Metoda pro získání pohlaví hráče.
     * @return male/female
     */
    public String getPlayerGender() {
        return playerGender;
    }

    /**
     * Metoda pro nastavení pohlaví hráče.
     * @param playerGender male/female
     */
    public void setPlayerGender(String playerGender) {
        this.playerGender = playerGender;
    }

    /**
     * Metoda pro získání zbraně hráče.
     * @return zbraň hráče
     */
    public Weapon getPlayerWeapon() {
        return playerWeapon;
    }

    /**
     * Metoda pro nastavení zbraně hráče.
     * @param playerWeapon hráče
     */
    public void setPlayerWeapon(Weapon playerWeapon) {
        this.playerWeapon = playerWeapon;
    }

    /**
     * Metoda pro získání hp hráče.
     * @return hp hráče
     */
    public double getHp() {
        return hp;
    }

    /**
     * Metoda pro nastavení hp hráče.
     * @param hp hp hráče
     */
    public void setHp(double hp) {
        this.hp = hp;
    }

    /**
     * Metoda pro získání str hráče.
     * @return str hráče
     */
    public double getStr() {
        return str * (getPlayerWeapon().getMultiplicator());
    }

    /**
     * Metoda pro nastavení str hráče.
     * @param str hráče
     */
    public void setStr(double str) {
        this.str = str;
    }

    public double getStrWithoutWeapon(){
        return str;
    }

    /**
     * Metoda pro získání profese hráče
     * @return profese hráče
     */
    public Race getRace() {
        return race;
    }

    /**
     * Metoda pro nastavení rasy hráče
     * @param race hráče
     */
    public void setRace(Race race) {
        this.race = race;
        switch(race.getName()){
            case("elf"):
                setStr(getStrWithoutWeapon()+5);
                break;
            case("temný_elf"):
                setStr(getStrWithoutWeapon()+5);
                break;
            case("barbar"):
                setHp(getHp()+5);
            case("trpaslík"):
                setHp(getHp()+10);
            case("člověk"):
                setHp(getHp()+3);
                setStr(getStrWithoutWeapon()+3);
            case("mág"):
                setStr(getStrWithoutWeapon()+12);
        }
    }

}
