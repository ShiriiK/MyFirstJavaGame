package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro nastavení pohlaví.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-16
 */

public class ActionGender implements IAction {
    private Game game;
    private String[] names = {"pohlaví"};

    /**
     * Konstuktor
     *
     * @param game hra ve které bude příkaz vykonán
     */
    public ActionGender(Game game) {
        this.game = game;
    }

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
     * Provádí příkaz gender - nastaví pohlaví hráče a společně s tím základní staty a partner.
     *
     * @param parameters jeden parametr - male nebo female
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {
        String d1 = game.makeItLookGood1();
        String d2 = game.makeItLookGood2();

        if (parameters.length == 0) {
            return d1 + "Které pohlaví si chceš vybrat? Máš na výběr mezi mužem a ženou." + d2;
        }
        if (parameters.length > 1) {
            return d1 + "Můžeš mít pouze jednou pohlaví." + d2;
        }

        GameState gameState = game.getGameState();
        Player player = gameState.getPlayer();
        String playerGender = player.getPlayerGender();

        if (playerGender != null) {
            return d1 + "Už sis pohlavní vybral/a." + d2;
        }

        String gender = parameters[0];

        if (!("muž".equals(gender)) && !("žena".equals(gender))) {
            return d1 + "Je mi líto, že tě zklamu, ale takhle hra zná jen dvě pohlaví." + d2;
        }

        player.setPlayerGender(gender);
        Partner partner = gameState.getPartner();

        if ("muž".equals(gender)) {
            partner.setPartnerName("Yrsa");
            partner.setStr(5.0);
            partner.setHp(50.0);
            player.setStr(10.0);
            player.setHp(70.0);
        }
        if ("žena".equals(gender)) {
            partner.setPartnerName("Torsten");
            partner.setStr(10.0);
            partner.setHp(70.0);
            player.setStr(5.0);
            player.setHp(50.0);
        }

        String partnerName = partner.getPartnerName();
        String name;
        if (partnerName.equals("Yrsa")) {
            name = "Yrsy";
        } else {
            name = "Torstena";
        }

        gameState.setPhase(1);
        return  d1 + "Pohlaví nastaveno na: " + gender + d2 + "\n" +
                "Pomalu otevíráš oči a vidíš před sebou rozmazanou siluetu obrovského muže.\n" +
                "Mezitím, co se snažíš opartně posadit, se k tobě muž otočí a přispěchá ti na pomoc.\n\n" +
                "???: Dobré ráno. Jak se cítíš? Slyšel jsem od " + name + ", co stalo. Ti vojáci opravdu nezají hranic...\n " +
                "Že se nestydí chtít popravit malou holčičku. Vypadá to, že Tua uvěznili za to, že si jí zastal.\n " +
                "Musíte se ho vydat brzy zachránit. Ale v tomhle stavu si nejsem jistný, jestli to zvládnete...\n " +
                "Vnímáš mě? Pamatuješ vůbec svoje jméno?\n " + d1 +
                "Zkus si vzpomenout, jak se jmenuješ. Napiš: jméno tvé_jméno" + d2;
    }
}
