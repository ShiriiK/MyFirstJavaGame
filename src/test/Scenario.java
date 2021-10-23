package test;

import java.util.LinkedList;
import java.util.List;

/**
 * Třída reprezetující scénář simulující průběh hry.
 * <p>
 * Toto rozhraní je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Jan Říha
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-23
 */

public class Scenario {
    private String name;
    private List<Step> steps;

    /**
     * Konstruktor třídy, vytvoří testovací scénář se zadaným názvem.
     *
     * @param name název testovacího scénáře <i>(jednoznačný identifikátor, mělo by se jednat o text bez mezer)</i>
     */
    public Scenario(String name) {
        this.name = name;
        this.steps = new LinkedList<>();
    }

    /**
     * Metoda vrací název testovacího scénáře.
     *
     * @return název testovacího scénáře
     */
    public String getName() {
        return name;
    }

    /**
     * Metoda přidá další krok do testovacího scénáře a následně celý scénář vrátí.
     *
     * @param step krok, který bude přidán do scénáře
     * @return testovací scénář
     */
    public Scenario addStep(Step step) {
        steps.add(step);
        return this;
    }

    /**
     * Metoda vrátí seznam obsahující všechny kroky testovacího scénáře.
     *
     * @return seznam obsahující všechny kroky testovacího scénáře
     */
    public List<Step> getSteps() {
        return new LinkedList<>(steps);
    }

    /**
     * Metoda vrátí název a výpis kroků testovacího scénáře.
     *
     * @return název a výpis kroků testovacího scénáře
     */
    @Override
    public String toString() {
        String result = "\nScenario: " + name;

        if (steps.isEmpty()) {
            result += "No steps in this scenario.";
        } else {
            int i = 1;
            for (Step s : steps) {
                result += "\n\n" + i++ + ". " + s.toStirng();
            }
        }
        return result;
    }
}
