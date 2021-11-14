package logic;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
        Set<Weapon> weapons = new HashSet<>();

        //Vytvoření zbraní
        Weapon axe = new Weapon("sekera", "Sekera",1.2, false, "trpaslík",0,0,0,0);
        Weapon executioner_axe = new Weapon("popravčí_sekera", "Popravčí sekera", 1.4,true,"trpaslík",10,0,10,0);
        Weapon two_axes = new Weapon("dvě_sekery", "Dvě sekery", 1.4,true, "trpaslík",0,20,0,0);
        Weapon rune_axe = new Weapon("runová_sekera", "Runová sekera", 1.4, true, "trpaslík",0,10,10,0);
        Weapon sword = new Weapon("meč", "Meč",1.2, false, "člověk",0,0,0,0);
        Weapon poisoned_sword = new Weapon("otrávený_meč", "Otrávený meč", 1.4, true, "člověk", 10,0,5,5);
        Weapon greatsword = new Weapon("greatsword", "Greatsword",1.4, true, "člověk",10,0,10,0);
        Weapon holysword = new Weapon("posvátný_meč","Posvátný meč", 1.4, true, "člověk",0,10,0,10);
        Weapon dagger = new Weapon("dýka", "Dýka",1.2, false, "temný_elf",0,0,0,0);
        Weapon poisoned_dagger = new Weapon ("otrávená_dýka", "Otrávená dýka", 1.4, true,"temný_elf",10,0,5,5);
        Weapon fire_dagger = new Weapon ("ohnivá_dýka", "Ohnivá dýka", 1.4,true, "temný_elf",0,0,10,10);
        Weapon curved_dagger = new Weapon("zahnutá_dýka", "Zahnutá dýka", 1.4,true, "temný_elf",0,20,5,54);
        Weapon bow = new Weapon("luk","Luk", 1.2, false, "elf",0,0,0,0);
        Weapon longbow = new Weapon("dlouhý_luk", "Dlouhý_luk", 1.4, true, "elf",10,10,0,0);
        Weapon mist_bow = new Weapon("mlhový_lul", "Mlhový luk", 1.4, true, "elf",0,0,10,10);
        Weapon elven_sword = new Weapon("elfí_meč","Elfí meč", 1.4, true, "elf",5,5,5,5);
        Weapon club = new Weapon("kyj", "Kyj",1.2, false, "barbar",0,0,0,0);
        Weapon mace = new Weapon("palcát", "Palcát", 1.4, true, "barbar",10,10,0,0);
        Weapon halberd = new Weapon("halberda", "Halberda",1.4, true, "barbar",0,20,0,10);
        Weapon spear = new Weapon("kopí", "Kopí",1.4, true, "barbar",0,0,30,0);
        Weapon staff = new Weapon ("magická_hůl","Magická hůl", 1.2, false, "mág",0,0,0,0);
        Weapon dragon_staff = new Weapon("dračí_hůl","Dračí hůl", 1.4, true, "mág",0,0,10,10);
        Weapon crystal_staff = new Weapon("krystalová_hůl", "Krystalová hůl", 1.4, true, "mág",10,10,0,0);
        Weapon ancient_staff = new Weapon("starodávná_hůl", "Starodávná hůl", 1.4, true, "mág",15,5,0,0);

        weapons.addAll(Arrays.asList(axe, executioner_axe, two_axes, rune_axe, sword, poisoned_sword, greatsword, holysword, dagger, poisoned_dagger, fire_dagger,
                curved_dagger, bow, longbow, mist_bow, elven_sword, club, mace, halberd, spear, staff, dragon_staff, crystal_staff, ancient_staff));


        for (Weapon weapon : weapons) {
            if (weapon.getRace().equals(gameState.getPlayer().getRace().getName())){
                armory.addWeapon(weapon);
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
