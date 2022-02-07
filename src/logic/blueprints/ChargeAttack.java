package logic.blueprints;

/**
 * Instances of this class represent charges.
 * @author Alena Kalivodová
 */

public class ChargeAttack {

    private final String name;
    private final int dmg;
    private final int negetableDmg;
    private final int bonusDmg;
    private final Race race;


    public ChargeAttack(String name, int dmg, int negetableDmg, int bonusDmg, Race race){
        this.name = name;
        this.dmg = dmg;
        this.negetableDmg = negetableDmg;
        this.bonusDmg = bonusDmg;
        this.race = race;
    }

    public String getName() {
        return name;
    }

    public Race getRace() {
        return race;
    }

    public int getDmg() {
        return dmg;
    }

    public int getNegetableDmg() {
        return negetableDmg;
    }

    public int getBonusDmg() {
        return bonusDmg;
    }

}
