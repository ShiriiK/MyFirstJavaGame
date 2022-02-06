package logic.actions;

import gui.util.Constants;
import logic.Game;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro zobrazení nápovědy.
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
     * Provádí příkaz help - vypíše nápovědu do konzole.
     * @param parameters žádný
     * @return text s nápovědou
     */
    @Override
    public String execute(String[] parameters) {

        if (parameters.length >= 1) {
            return "\nStačí napsat nápověda.";
        }

        return  Constants.d1 + "Můžeš použít následující příkazy:\n\n" +
                "pohlaví + muž/žena\n" +
                "jméno + jméno\n" +
                "rasa + rasa\n" +
                "vzemi_si_zbraň/zbraň + zbraň\n" +
                "odlož_zbraň\n" +
                "hráč => zobrazí staty hráče.\n" +
                "parťák/parťačka => zobrazí staty partnera.\n" +
                "inventář/batoh\n" +
                "mluv_s/promluv_si_s + postava\n" +
                "rozhlédni_se\n" +
                "prozkoumej/prohledej + předmět\n" +
                "vezmi/seber + předmět\n" +
                "zahoď/polož + předmět\n" +
                "dej/nabídni + postava + předmět\n" +
                "jdi/jdi_do/běž/běž_do + lokace\n" +
                "zaútoč_na + postava\n" +
                "zaútoč_s_parťákem_na + postava\n" +
                "zachraň_tue\n" +
                "konec" + Constants.d2;
    }
}

