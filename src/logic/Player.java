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
    private int round;
    private double negetedDmg;
    private double bonusDmg;
    private boolean usedAttack3;
    private boolean usedCharge;

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

        round = 0;
        negetedDmg = 0.0;
        bonusDmg = 0.0;
        usedAttack3 = false;
        usedCharge = false;
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

    /**
     * Metoda pro získání informaace o tok, kolik kol souboje již proběhlo
     * @return počet kol
     */
    public int getRound() {
        return round;
    }

    /**
     * Metoda pro nastavení počtu kol souboje
     * @param round počet kol
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * Metoda pro vrácení negetedDmg.
     * @return negetedDmg
     */
    public double getNegetedDmg() {
        return negetedDmg;
    }

    /**
     * Metoda pro nastavení negetedDmg
     * @param negetedDmg dmg, který hráč vyblokuje
     */
    public void setNegetedDmg(double negetedDmg) {
        this.negetedDmg = negetedDmg;
    }

    /**
     * Metoda pro vrácení bonusDmg
     * @return bonusDmg
     */
    public double getBonusDmg() {
        return bonusDmg;
    }

    /**
     * Metoda pro nastavení bonusDmg
     * @param bonusDmg dmg, který dá vrác navíc
     */
    public void setBonusDmg(double bonusDmg) {
        this.bonusDmg = bonusDmg;
    }

    /**
     * Metoda pro získání informace, zda již hráč použil speciální útok
     * @return true - použil, false - nepoužil
     */
    public boolean isUsedAttack3() {
        return usedAttack3;
    }

    /**
     * Metoda pro nastavení stavu použití speciálního útoku
     * @param usedAttack3 stav použití speciálního útoku
     */
    public void setUsedAttack3(boolean usedAttack3) {
        this.usedAttack3 = usedAttack3;
    }

    /**
     * Metoda pro získání informace, zda již hráč použit charge
     * @return true - použil, false - nepoužil
     */
    public boolean isUsedCharge() {
        return usedCharge;
    }

    /**
     * Metoda pro nastavení stavu použití charge
     * @param usedCharge stav použití charg
     */
    public void setUsedCharge(boolean usedCharge) {
        this.usedCharge = usedCharge;
    }
}