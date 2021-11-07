package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro zobrazení nápovědy.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-16
 */
public class ActionHelp implements IAction {
    private String[] names = {"nápověda"};

    /**
     * Metoda použitá pro identifikování platnosti příkazů.
     * @return možné názvy příkazů
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
        String d1 = Game.makeItLookGood1();
        String d2 = Game.makeItLookGood2();


        if (parameters.length >= 1) {
            return "\nStačí napsat nápověda.";
        }

        return  d1 + "Můžeš použít následující příkazy:\n\n" +
                "pohlaví + muž/žena\n" +
                "jméno + jméno\n" +
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
                "dej/nabídn + postava + předmět\n" +
                "jdi/jdi_do/běž/běž_do + lokace\n" +
                "zaútoč_na + postava\n" +
                "zaútoč_s_parťákem_na + postava\n" +
                "zachraň_tua\n" +
                "konec" + d2;
    }
}

