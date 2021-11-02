package logic;

import java.util.Arrays;
/**
 * Třída implementující příkaz pro nastavení pohlaví.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-16
 */

public class ActionProfession implements IAction {
    private Game game;
    private String[] names = {"profese"};

    /**
     * Konstuktor
     *
     * @param game hra ve které bude příkaz vykonán
     */
    public ActionProfession(Game game) {
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
            return d1 + "Kterou profesi si chceš vybrat?" + d2;
        }
        if (parameters.length > 1) {
            return d1 + "Můžeš mít pouze jednu profesi." + d2;
        }

        GameState gameState = game.getGameState();
        Player player = gameState.getPlayer();
        String playerProfession = player.getProfession();

        if (playerProfession != "nic") {
            return d1 + "Už sis profesi vybral/a." + d2;
        }

        String prof = parameters[0];


        for (Profession profession : Profession.values()) {
            if (profession.toString().equalsIgnoreCase(prof)) {

                player.setProfession(prof);

                gameState.setPhase(1);
                return d1 + "Profese nastavena na: " + prof + d2 + "\n" +
                        "Pomalu otevíráš oči a vidíš před sebou rozmazanou siluetu obrovského muže.\n" +
                        "Mezitím, co se snažíš opartně posadit, se k tobě muž otočí a přispěchá ti na pomoc.\n\n" +
                        "???: Dobré ráno. Jak se cítíš? Slyšel jsem, co se stalo. Ti vojáci opravdu nezají hranic...\n " +
                        "Že se nestydí chtít popravit malou holčičku. Vypadá to, že Tua uvěznili za to, že si jí zastal.\n " +
                        "Musíte se ho vydat brzy zachránit. Ale v tomhle stavu si nejsem jistný, jestli to zvládnete...\n " +
                        "Vnímáš mě? Pamatuješ vůbec svoje jméno?\n " + d1 +
                        "Zkus si vzpomenout, jak se jmenuješ. Napiš: jméno tvé_jméno" + d2;
            } else {
                return d1 + "Je mi líto, že tě zklamu, ale tuhle profei tu nemáme." + d2;
            }
        }
        return "Někde se stala chyba.";
    }
}

