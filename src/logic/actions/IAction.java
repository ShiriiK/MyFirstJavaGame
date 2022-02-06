package logic.actions;

/**
 * Classes implementing this interface process commands.
 * @author Jan Říha
 */

public interface IAction {

    /**
     * The method returns the name of the game command.
     */
    String[] getName();

    /**
     * A method of ensuring the execution of a game command.
     */
    String execute(String[] parameters);
}
