package logic.blueprints;

/**
 * Instances of this class represent races in game.
 * @author Alena Kalivodová
 */

public class Race {
    private final String name;
    private final int bonusStr;
    private final int bonusHp;

    /**
     * Constructor
     * @param name name
     */
    public Race(String name, int bonusHp, int bonusStr) {
        this.name = name;
        this.bonusHp = bonusHp;
        this.bonusStr = bonusStr;
    }

    /**
     * Method for obtaining a race name
     * @return race name
     */
    public String getName() {
        return name;
    }

    public int getBonusStr() {
        return bonusStr;
    }

    public int getBonusHp() {
        return bonusHp;
    }
}

