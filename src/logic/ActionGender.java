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
    private final Game game;
    private final String[] names = {"pohlaví"};

    //Konstruktor
    public ActionGender(Game game) {
        this.game = game;
    }

    /**
     * Metoda použitá pro identifikování platnosti příkazů.
     * @return možné názvy příkazů
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 1);
    }

    /**
     * Provádí příkaz gender - nastaví pohlaví hráče a společně s tím základní staty a partner.
     * @param parameters jeden parametr - male nebo female
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {
        String d1 = Game.makeItLookGood1();
        String d2 = Game.makeItLookGood2();

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
            partner.setStr(20.0);
            partner.setHp(80.0);
            player.setStr(30.0);
            player.setHp(100.0);
        }
        if ("žena".equals(gender)) {
            partner.setPartnerName("Torsten");
            partner.setStr(30.0);
            partner.setHp(100.0);
            player.setStr(30.0);
            player.setHp(80.0);
        }

        gameState.setPhase(1);
        return  d1 + "Pohlaví nastaveno na: " + gender + d2 +
                d1 + "Nyní si vyber rasu." + d2;

    }
}
