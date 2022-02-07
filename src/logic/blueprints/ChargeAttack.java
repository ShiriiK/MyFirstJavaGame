package logic.blueprints;

public class ChargeAttack {

    private String name;
    private int dmg;
    private int negetableDmg;
    private int bonusDmg;
    private Race race;


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
