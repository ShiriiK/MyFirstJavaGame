package logic;

import gui.util.Constants;
import logic.actions.*;

import java.util.*;

/**
 * Main class of application logic. Creates a GameState instance that initializes the
 * locations and items, weapons and npcs in them.
 * Prints the opening and closing text of the game.
 * Evaluates individual commands.
 * @author Jan Říha
 * @author Alena Kalivodová
 */

public class Game {
    private boolean theEnd;
    private final GameState gameState;
    private final Set<IAction> validActions;
    private boolean happyEnd;

    public Game() {
        theEnd = false;
        happyEnd = false;
        gameState = new GameState();
        validActions = new HashSet<>();
        validActions.add(new ActionGender());
        validActions.add(new ActionName());
        validActions.add(new ActionWeapon());
        validActions.add(new ActionHelp());
        validActions.add(new ActionTerminate());
        validActions.add(new ActionGo());
        validActions.add(new ActionLook());
        validActions.add(new ActionExplore());
        validActions.add(new ActionAttack());
        validActions.add(new ActionAttackPartner());
        validActions.add(new ActionPickUp());
        validActions.add(new ActionPlayer());
        validActions.add(new ActionPartner());
        validActions.add(new ActionDrop());
        validActions.add(new ActionInventory());
        validActions.add(new ActionWeaponDrop());
        validActions.add(new ActionGive());
        validActions.add(new ActionRescue());
        validActions.add(new ActionTalk());
        validActions.add(new ActionRace());
        validActions.add(new ActionEnhancedCombat());
    }

    /**
     * Returns an introductory message to the player.
     * @return welcome to the game
     */
    public String theBeginning() {
        return "\nWelcome to the game Saving Tue.\n" +
                "You will soon start playing and find out what your goal is, if you are not sure what to do, just type a hint " +
                "and you'll see the commands you can use. " + Constants.d1 +
                "First, choose whether you want to play as a man or a woman." +
                Constants.d2;
    }

    /**
     * Returns a final message to the player.
     * @return message on completion, either winning or losing
     */
    public String epilog() {
        if (happyEnd) {
            return "\nTue was rescued!!!!!!! \n" +
                    Constants.d1 + "Congratulations on the successful completion of the game!" + Constants.d2;
        } else {
            return Constants.d1 + "The game is over. You lost.." + Constants.d2;
        }
    }

    /**
     * Method used to set happyEnd to true when the player saves Tue.
     * @param happyEnd if true, the game ended well
     */
    public void setHappyEnd(boolean happyEnd) {
        this.happyEnd = happyEnd;
    }

    /**
     * As long as theEnd is false, the game runs.
     * @return false when the game is running, true when the game has been terminated
     */
    public boolean theEnd() {
        return theEnd;
    }

    /**
     * Used to set theEnd.
     * @param theEnd is set to true when the game ends
     */
    public void setTheEnd(boolean theEnd) {
        this.theEnd = theEnd;
    }

    /**
     * Used to get a link to gameState.
     * @return link to gameState
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * The method processes a text string as a parameter and splits it into a command and other parameters.
     * Tests if the command is in validAction.
     * If yes, it starts the execution of the command.
     *
     * @param line text specified by the user
     * @return the string to be printed to the console
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
        return Constants.d1 + "Invalid command." + Constants.d2;
    }


}
