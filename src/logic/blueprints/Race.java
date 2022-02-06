package logic.blueprints;

public class Race {
    private final String name;
    private final String specialAttack;
    private final String charge;

    /**
     * Constructor
     * @param name name
     * @param specialAttack special attack
     * @param charge charge
     */
    public Race(String name, String specialAttack, String charge) {
        this.name = name;
        this.specialAttack = specialAttack;
        this.charge = charge;
    }

    /**
     * Method for obtaining a race name
     * @return race name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to get the name of the special attack
     * @return special attack name
     */
    public String getSpecialAttack() {
        return specialAttack;
    }

    /**
     * Method to get the name of the charge attack
     * @return charge attack name
     */
    public String getCharge(){
        return charge;
    }
}

