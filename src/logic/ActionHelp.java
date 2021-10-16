package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro zobrazení nápovědy.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
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

        if (parameters.length >= 1) {
            return "\nStačí napsat help.";
        }

        return "\nMůžeš použít následující příkazy:\n\n" +
                "pohlaví + muž/žena                       => nastaví pohlaví hráče a také nastaví partnera; lze použít jen jednou. \n_________________________________\n" +
                "jméno + jméno                            => nastaví jméno hráče dle jeho volby; lze použít jen jednou.\n_________________________________\n" +
                "vzít_zbraň/zbraň + zbraň                 => nastaví zbraň hráče, je možné použít jen, když nemá hráč zbraň.\n_________________________________\n" +
                "odložitz/odložit_zbraň                   => sundá zbraň, kterou hráč aktuálně má, aby to mohl udělat, musí být v místnosti za kovárnou.\n_________________________________\n" +
                "hráč                                     => zobrazí staty hráče.\n_________________________________\n" +
                "parťák/parťačka                          => zobrazí staty partnera.\n_________________________________\n" +
                "inventář/batoh                           => zobrazí inventář.\n_________________________________\n" +
                "mluvit/mluvit_s + postava                => slouží k povídání si s postavami ve hře.\n_________________________________\n" +
                "rozhlédnout_se/rozhlédnout               => zobrazí předměty, postavy a zbraně které jsou v lokaci a východy z ní.\n_________________________________\n" +
                "prozkoumat/prohledat + předmět           => zobrazí popisek předmětu a pokud je v něm schovaný jiný předmět, tak ho přesune do lokace.\n_________________________________\n" +
                "vzít/sebrat + předmět                    => pokud je to možné, vloží předmět do inventáře a odebere ho z lokace.\n_________________________________\n" +
                "zahodit/položit + předmět                => zahodí předmět z inventáře do aktuální lokace.\n_________________________________\n" +
                "dát/nabídnout + postava + předmět        => slouží k předávání předmětů postavám ve hře.\n_________________________________\n" +
                "jít/jít_do/jít_k + lokace                => slouží k pohybu mezi lokacemi, u některých lokací, než je možné použít tento příkaz, je potřeba nejdřív splnit nějaký úkol.\n_________________________________\n" +
                "útok/zaútoč_na + postava                 => slouží k zaútočení na postavu ve hře.\n_________________________________\n" +
                "útokp/zaútoč_s_parťákem + postava        => slouží k zaútočení na postavu ve hře pomocí partnera.\n_________________________________\n" +
                "zachránit_tua                            => finální příkaz pro zachránění Tua, slouží k dokončení hry.\n_________________________________\n" +
                "konec                                    => ukončí hru.\n_________________________________\n";
    }
}

