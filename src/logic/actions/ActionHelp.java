package logic.actions;

import gui.util.Constants;
import logic.Game;

import java.util.Arrays;

/**
 * Class implementing the command to display help.
 * @author Alena Kalivodová
 */
public class ActionHelp implements IAction {
    private final String[] names = {"help"};

    /**
     * The method used to identify the validity of commands.
     * @return possible command names
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 1);
    }

    /**
     * Executes the help command - prints help to the console.
     * @param parameters none
     */
    @Override
    public String execute(String[] parameters) {

        if (parameters.length >= 1) {
            return "\nJust type help.";
        }

        return  Constants.d1 + "You can use the following commands:\n\n" +
                "gender + male/female\n" +
                "name + name\n" +
                "race + race\n" +
                "take_weapon/weapon + weapon\n" +
                "drop_weapon\n" +
                "player\n" +
                "partner\n" +
                "inventory\n" +
                "talk/talk_to + npc\n" +
                "look\n" +
                "explore/search + item\n" +
                "take/pickup + item\n" +
                "drop/throw_away + item\n" +
                "give/give + npc + item\n" +
                "go/go_to + location\n" +
                "attack + npc\n" +
                "partner_attack + npc\n" +
                "rescue_tue\n" +
                "end" + Constants.d2;
    }
}

