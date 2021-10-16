package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro nastavení jména hráče.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class ActionName implements IAction {
    private Game game;
    private String[] names = {"jméno"};

    /**
     * Konstuktor
     *
     * @param game hra ve které bude příkaz vykonán
     */
    public ActionName(Game game) {
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
     * Provádí příkaz name - nastaví jméno hráče.
     *
     * @param parameters jeden parametr - jméno hráče
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String... parameters) {
        String d1 = game.makeItLookGood1();
        String d2 = game.makeItLookGood2();

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
        return  d1 + "Jméno nastaveno na: " + name + d2 + "\n"+
                name + ": Ehhh? Já jsem " + name + ", že jo? Jsi to ty Gorme?\n" +
                "Gorm: Jo, to jsem já.\n" +
                name + ": Kde je " + partnerName + " a Tue?\n" +
                "Gorm: No, " + partnerName + " je táhle, ale jak jsem už říkal, Tua zavřeli.\n" +
                partnerName + ": Kdyby jenom to... Zatáhli ho do té nechvalně prosnulé mučírny a potom,\n" +
                "             co si tam s ním budou týden hrát, ho nechají pověsit\n" +
                "Gorm: Zpomal trochu. Oba jste na tom zatím celkem špatně. Pomalu se vzpamatuje,\n" +
                "           vemte si zbraně a pak mu běžte na pomoc. Já vám sice v boji k ničemu nebudu,\n" +
                "           ale v místosti za kovárnou si můžete vzít meč, sekeru nebo nůž, na zbytku zatím pracuju.\n" +
                partnerName + ": Za chvilku se k tobě přidám, jen si ještě chvilku odpočinu.\n" +
                gameState.getCurrentLocation().longDescription();
    }
}
