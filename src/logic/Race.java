package logic;

public class Race {
    private final String name;
    private final String specialAttack;
    private final String charge;

    /**
     * Konstruktor
     * @param name jméno
     * @param specialAttack speciální útok
     * @param charge charge
     */
    public Race(String name, String specialAttack, String charge) {
        this.name = name;
        this.specialAttack = specialAttack;
        this.charge = charge;
    }

    /**
     * Metoda pro získání jména rasy
     * @return jméno rasy
     */
    public String getName() {
        return name;
    }

    /**
     * Metoda pro získání názvu speciálního útoku
     * @return název speciálního útoku
     */
    public String getSpecialAttack() {
        return specialAttack;
    }

    /**
     * Metoda pro získání názvu charge útoku
     * @return název charge útoku
     */
    public String getCharge(){
        return charge;
    }
}

