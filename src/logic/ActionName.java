package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro nastavení jména hráče.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-16
 */

public class ActionName implements IAction {
    private final Game game;
    private final String[] names = {"jméno"};

    //Konstruktor
    public ActionName(Game game) {
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
     * Provádí příkaz name - nastaví jméno hráče.
     * @param parameters jeden parametr - jméno hráče
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String... parameters) {
        String d1 = Game.makeItLookGood1();
        String d2 = Game.makeItLookGood2();

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return d1 + "Vyber si nejdří své pohlaví." + d2;
        }
        if (parameters.length == 0) {
            return d1 + "A jak se chceš jmenovat?" + d2;
        }
        if (parameters.length > 1) {
            return d1 + "Pokud chceš mít víceslovné jméno, tak použij třeba podtržítko." + d2;
        }

        String playerName = game.getGameState().getPlayer().getPlayerName();

        if (playerName != null) {
            return d1 + "Už sis jméno vybral/a." + d2;
        }

        String name = parameters[0];
        String partnerName = gameState.getPartner().getPartnerName();
        gameState.setPhase(2);
        gameState.getPlayer().setPlayerName(name);

        Location armory = gameState.getCurrentLocation().getExit("kovárna").getTargetLocation().getExit("zbrojírna").getTargetLocation();

        for (Weapon weapon : armory.getWeapons()) {
            if (!weapon.getRace().equals(gameState.getPlayer().getRace().getName())){
                armory.removeWeapon(weapon.getName());
            }
        }
        return  d1 + "Jméno nastaveno na: " + name + d2 + "\n"+
                name + ": Ehhh? Já jsem " + name + ", že jo? Jsi to ty Gorm?\n" +
                "Gorm: Jo, jsem to já.\n" +
                name + ": Kde je " + partnerName + " a Tue?\n" +
                "Gorm: No, " + partnerName + " je táhle, ale jak jsem už říkala, Tue zavřeli.\n" +
                partnerName + ": Zatáhli ji do jakéhosi podzemního vězení, kde ji budou držet než ji popraví.\n" +
                "Tohle místo nám opravdu přináší jen samé neštěstí. Nejdřív Thorfinn a teď i Tue...\n" +
                "Kdyby neměla tak měkké srdce, tak by se jí nic nestalo.\n" +
                "Gorm: Zpomal trochu. Oba jste na tom zatím celkem špatně. Pomalu se vzpamatuje,\n" +
                " vemte si zbraně a pak jí běžte na pomoc. Já vám sice v boji k ničemu nebudu,\n" +
                " ale v místosti za kovárnou si můžete vzít zbraň, která vám bude vyhovovat, \n" +
                " kromě těch na kterých zatím pracuju.\n" +
                partnerName + ": Za chvilku se k tobě přidám, jen si ještě chvilku odpočinu. Tak si běž zařídit, \n" +
                "co jen potřebuješ. Přidám se k tobě hned jak opustíš louku." +
                gameState.getCurrentLocation().longDescription();
    }
}
