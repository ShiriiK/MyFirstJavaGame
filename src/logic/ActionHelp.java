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
    private String[] names = {"help"};

    /**
     * Metoda použitá pro identifikování platnosti příkazů.
     *
     * @return možné názvy příkazů
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 1);
    }

    /**
     * Provádí příkaz help - vypíše nápovědu do konzole.
     *
     * @param parameters žádný
     * @return text s nápovědou
     */
    @Override
    public String execute(String[] parameters) {
        String d1 = Game.makeItLookGood1();
        String d2 = Game.makeItLookGood2();


        if (parameters.length >= 1) {
            return "\nStačí napsat help.";
        }

        return  d1 + "Můžeš použít následující příkazy:\n\n" +
                "pohlaví + muž/žena\n" +
                "jméno + jméno\n" +
                "vzít_zbraň/zbraň + zbraň\n" +
                "odložitz/odložit_zbraň\n" +
                "hráč => zobrazí staty hráče.\n" +
                "parťák/parťačka => zobrazí staty partnera.\n" +
                "inventář/batoh\n" +
                "mluvit/mluvit_s + postava\n" +
                "rozhlédnout_se/rozhlédnout\n" +
                "prozkoumat/prohledat + předmět\n" +
                "vzít/sebrat + předmět\n" +
                "zahodit/položit + předmět\n" +
                "dát/nabídnout + postava + předmět\n" +
                "jít/jít_do/jít_k + lokace\n" +
                "útok/zaútoč_na + postava\n" +
                "útokp/zaútoč_s_parťákem + postava\n" +
                "zachránit_tua\n" +
                "konec" + d2;
    }
}

