package logic;

import java.util.*;

/**
 * Třída představující logiku adventury.
 * <p>
 * Hlavní třída logiky aplikace. Vytváři instanci GameState, která inicializuje
 * lokace a itemy, zbraně a npc v nich.
 * Vypisuje úvodní a závěrečný text hry.
 * Vyhodnocuje jednotlivé příkazy.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Jan Říha
 * @author Alena Kalivodová
 * @version LS-2021, 2021-10-23
 */

public class Game {
    private boolean theEnd;
    private GameState gameState;
    private Set<IAction> validActions;
    private boolean happyEnd;

    /**
     * Vytvoří hru.
     */
    public Game() {
        theEnd = false;
        happyEnd = false;
        gameState = new GameState();
        validActions = new HashSet<>();
        validActions.add(new ActionGender(this));
        validActions.add(new ActionName(this));
        validActions.add(new ActionWeapon(this));
        validActions.add(new ActionHelp());
        validActions.add(new ActionTerminate(this));
        validActions.add(new ActionGo(this));
        validActions.add(new ActionLook(this));
        validActions.add(new ActionExplore(this));
        validActions.add(new ActionAttack(this));
        validActions.add(new ActionAttackPartner(this));
        validActions.add(new ActionPickUp(this));
        validActions.add(new ActionPlayer(this));
        validActions.add(new ActionPartner(this));
        validActions.add(new ActionDrop(this));
        validActions.add(new ActionInventory(this));
        validActions.add(new ActionWeaponDrop(this));
        validActions.add(new ActionGive(this));
        validActions.add(new ActionRescue(this));
        validActions.add(new ActionTalk(this));
        validActions.add(new ActionRace(this));
        validActions.add(new ActionEnhancedCombat(this));
    }

    /**
     * Vrátí úvodní zprávu pro hráče.
     * @return přivítání do hry
     */
    public String theBeginning() {
        return "\nVítej ve hře Záchrana Tue.\n" +
                "Brzy začneš hrát a zjistíš, co je tvým cílem, pokud si nejsi jistý/á, co máš dělat, stačí napsat nápověda" +
                "a zobrazí se příkazy, které můžeš použít." + makeItLookGood1() +
                "Nejprve vyber, zda chceš hrát za muže nebo ženu." +
                makeItLookGood2();
    }

    /**
     * Vrátí závěrečnou zprávu pro hráče.
     * @return zpráva o ukočení, buďto vítězném nebo o prohře
     */
    public String epilog() {
        if (happyEnd) {
            return "\nTue byla zachráněna!!!!!!! \n" +
                    makeItLookGood1() + "Gratuluji k úspěšnému dokončení hry!" + makeItLookGood2();
        } else {
            return makeItLookGood1() + "Hra skončila. Prohrál/a jsi." + makeItLookGood2();
        }
    }

    /**
     * Metoda sloužící k nastavení happyEnd na true ve chvíli, kdy hráč zachrání Tue.
     * @param happyEnd pokud je true, tak hra skončila dobře
     */
    public void setHappyEnd(boolean happyEnd) {
        this.happyEnd = happyEnd;
    }

    /**
     * Dokud je theEnd false, tak hra běží.
     * @return false když hra běží, true, když byla ukončena
     */
    public boolean theEnd() {
        return theEnd;
    }

    /**
     * Slouží k nastavení theEnd.
     * @param theEnd nastaví se na true při ukončení hry
     */
    public void setTheEnd(boolean theEnd) {
        this.theEnd = theEnd;
    }

    /**
     * Slouží k získání odkazu na gameState.
     * @return odkaz na gameState
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Metoda zpracuje textový řetězec jako parametr a rozdělí ho na příkaz a další parametry.
     * Otestuje zda je příkaz ve validAction.
     * Pokud ano, tak spustí provedení příkazu.
     *
     * @param line text zadaný uživatelem
     * @return řetězec, který se vypíše do konzole
     */
    public String processAction(String line) {
        String[] text = line.split("[ \t]+");
        String actionName = text[0];
        String[] parameters = new String[text.length - 1];

        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = text[i + 1];
        }
        for (IAction action : validActions) {
            for (String x : action.getName()) {
                if (x.equals(actionName)) {
                    return action.execute(parameters);
                }
            }
        }
        return makeItLookGood1() + "Nesprávný příkaz." + makeItLookGood2();
    }

    //Metody pro zkrášlení textů vypisovaných do konozole
    public static String makeItLookGood1() {
        return "\n_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n";
    }

    public static String makeItLookGood2() {
        return "\n‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾-‾\n";
    }
}
