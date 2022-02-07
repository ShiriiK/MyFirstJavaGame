package logic.blueprints;

/**
 * Instances of this class represent special attacks.
 * @author Alena Kalivodová
 */

public class SpecialAttack {

    private final String name;
    private final int dmg;
    private final int negetableDmg;
    private final Race race;


    public SpecialAttack(String name, int dmg, int negetableDmg, Race race){
        this.name = name;
        this.dmg = dmg;
        this.negetableDmg = negetableDmg;
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

}

