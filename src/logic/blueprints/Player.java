package logic.blueprints;

/**
 * A class representing a player.
 * @author Alena Kalivodová
 */

public class Player {
    private String playerName;
    private String playerGender;
    private Weapon playerWeapon;
    private int hp;
    private int str;
    private Race race;
    private int round;
    private int negetedDmg;
    private int bonusDmg;
    private boolean usedAttack3;
    private boolean usedCharge;

    /**
     * Constructor
     * @param playerName name
     * @param playerGender gender
     * @param playerWeapon weapon
     * @param hp lives
     * @param str strength
     * @param race race
     */
    public Player(String playerName, String playerGender, Weapon playerWeapon, int hp, int str, Race race) {
        this.playerName = playerName;
        this.playerGender = playerGender;
        this.playerWeapon = playerWeapon;
        this.hp = hp;
        this.str = str;
        this.race = race;

        round = 0;
        negetedDmg = 0;
        bonusDmg = 0;
        usedAttack3 = false;
        usedCharge = false;
    }

    /**
     * Method to get player's stats.
     * @return player information
     */
    public String getPlayersStats() {
        if (getPlayerWeapon() == null) {
            return "\nGender: " + playerGender + "\n" +
                    "Name: " + playerName + "\n" +
                    "Race: " + race.getName() + "\n" +
                    "Weapon: \n" +
                    "Hp: " + hp + "\n" +
                    "Str: " + str ;
        }
        return "\nGender: " + playerGender + "\n" +
                "Name: " + playerName + "\n" +
                "Race: " + race.getName() + "\n" +
                "Weapon: " + getPlayerWeapon().getDisplayName() + "\n" +
                "Hp: " + hp + "\n" +
                "Str: " + str * (getPlayerWeapon().getMultiplicator());
    }

    /**
     * Method to get player's name.
     * @return player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Method for setting player name.
     * @param playerName player name
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Method to get the gender of the player.
     * @return male/female
     */
    public String getPlayerGender() {
        return playerGender;
    }

    /**
     * Method for setting the player's gender.
     * @param playerGender male/female
     */
    public void setPlayerGender(String playerGender) {
        this.playerGender = playerGender;
    }

    /**
     * Method for obtaining a player's weapon.
     * @return player's weapon
     */
    public Weapon getPlayerWeapon() {
        return playerWeapon;
    }

    /**
     * Method for setting the player's weapon.
     * @param playerWeapon
     */
    public void setPlayerWeapon(Weapon playerWeapon) {
        this.playerWeapon = playerWeapon;
    }

    /**
     * Method for getting player hp.
     * @return player hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * Method for setting player's hp.
     * @param hp player hp
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Method for getting player str.
     * @return player str
     */
    public int getStr() {
        return str * (getPlayerWeapon().getMultiplicator());
    }

    /**
     * Method for setting player str.
     * @param str player
     */
    public void setStr(int str) {
        this.str = str;
    }

    public int getStrWithoutWeapon(){
        return str;
    }

    /**
     * Method for obtaining the player profession
     * @return player profession
     */
    public Race getRace() {
        return race;
    }

    /**
     * Method for setting the player's race
     * @param race player race
     */
    public void setRace(Race race) {
        this.race = race;
        this.setStr(getStrWithoutWeapon() + race.getBonusStr());
        this.setHp(getHp() + race.getBonusHp());
    }

    /**
     * Method to get information about the number of rounds of a duel
     * @return number of rounds
     */
    public int getRound() {
        return round;
    }

    /**
     * Method for setting the number of rounds of combat
     * @param round number of rounds
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * Method to return negetedDmg.
     * @return negetedDmg
     */
    public int getNegetedDmg() {
        return negetedDmg;
    }

    /**
     * Method for setting negetedDmg
     * @param negetedDmg dmg that the player blocks
     */
    public void setNegetedDmg(int negetedDmg) {
        this.negetedDmg = negetedDmg;
    }

    /**
     * Method to return bonusDmg
     * @return bonusDmg
     */
    public int getBonusDmg() {
        return bonusDmg;
    }

    /**
     * Method for setting bonusDmg
     * @param bonusDmg dmg which will give back extra dmg
     */
    public void setBonusDmg(int bonusDmg) {
        this.bonusDmg = bonusDmg;
    }

    /**
     * Method to get information if a player has already used a special attack
     * @return true - has used, false - has not used
     */
    public boolean isUsedAttack3() {
        return usedAttack3;
    }

    /**
     * Method for setting the status of using a special attack
     * @param usedAttack3 special attack usage state
     */
    public void setUsedSpecialAttacks(boolean usedAttack3) {
        this.usedAttack3 = usedAttack3;
    }

    /**
     * Method to get information if the player has already used charge
     * @return true - used, false - did not use
     */
    public boolean isUsedCharge() {
        return usedCharge;
    }

    /**
     * Method for setting the charge usage status
     * @param usedCharge usage state charg
     */
    public void setUsedCharge(boolean usedCharge) {
        this.usedCharge = usedCharge;
    }
}