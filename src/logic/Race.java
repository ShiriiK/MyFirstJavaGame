package logic;

public class Race {
    private String name;
    private String attack1;
    private String attack2;
    private String attack3;
    private String charge;

    // Konstruktor
    public Race(String name, String attack3, String charge) {
        this.name = name;
        this.attack1 = "útok_z_blízka";
        this.attack2 = "útok_z_dálky";
        this.attack3 = attack3;
        this.charge = charge;
    }

    public String getName() {
        return name;
    }

    public String getAttack1() {
        return attack1;
    }

    public String getAttack3() {
        return attack3;
    }

    public String getCharge(){
        return charge;
    }
}

