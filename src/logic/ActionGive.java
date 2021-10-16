package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro předání itemu npc.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class ActionGive implements IAction {
    private Game game;
    private String[] names = {"dát", "nabídnout"};

    /**
     * Konstuktor
     *
     * @param game hra ve které bude příkaz vykonán
     */
    public ActionGive(Game game) {
        this.game = game;
    }

    /**
     * Metoda použitá pro identifikování platnosti příkazů.
     *
     * @return možné názvy příkazů
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 2);
    }

    /**
     * Provádí příkaz give - dá z inventáře item nějakému npc.
     *
     * @param parameters dva parametry - jméno npc a pak item, který má dostat
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return "\nNemůžeš dávat věci, dokud si nenastavíš pohlaví.";
        }
        if (phase == 1) {
            return "\nNemůžeš dávat věci, dokud si nenastavíš jméno.";
        }
        if (parameters.length < 2) {
            return "\nMusíš napsat taky komu chceš něco dát a co to něco má být.";
        }
        if (parameters.length > 2) {
            return "\nNemůžeš dávat víc věcí najednou. Vyber si jednu.";
        }

        String itemName = parameters[1];
        Inventory inventory = gameState.getInventory();

        if (!inventory.containsItem(itemName)) {
            return "\nNemůžeš někomu dát něco, co nemáš.";
        }

        String npcName = parameters[0];
        Location currentLocation = gameState.getCurrentLocation();
        Npc npc = currentLocation.getNpc(npcName);

        if (npc == null) {
            return "\nNemůžeš dávat věci někomu, kdo tu není.";
        }

        Item item = inventory.getItem(itemName);

        if (npcName.equals("gorm") && itemName.equals("svítící_kámen")) {
            npc.insertItem(item);
            inventory.removeItem(itemName);
            return "\nDal/a jsi Gormovi svítící_kámen.\n" +
                    "Gorm:To je přesně to, co jsem potřeboval k dokončení zbraní, už si můžeš vzít nějakou z těch lepších.";
        }
        if (npcName.equals("generál") && itemName.equals("taška")) {
            npc.insertItem(item);
            inventory.removeItem(itemName);
            currentLocation.getExit("brána").removeWatchingNpc(npc);
            currentLocation.getExit("ghetto").removeWatchingNpc(npc);
            currentLocation.getExit("vchod").removeWatchingNpc(npc);
            currentLocation.getExit("průchod").removeWatchingNpc(npc);
            return "\nGenerál: Tomu říkám domluva. A teď vypadněte ať už se na vás nemusím koukat.";
        }
        if (npcName.equals("stráž_brány") && itemName.equals("propustka")) {
            npc.insertItem(item);
            inventory.removeItem(itemName);
            currentLocation.getExit("město").removeWatchingNpc(npc);
            return "\nStráž: Vaše propustka vypadá v pořádku. Můžete do města.";
        }
        if (npcName.equals("generál") && itemName.equals("kámen")) {
            game.setTheEnd(true);
            return "\nGenerál: ...Zbavte se jich.";
        }
        if (npcName.equals("stráž") && itemName.equals("taška")) {
            inventory.removeItem(itemName);
            currentLocation.removeNpc("stráž");
            return "\nStráž: Woahhhh. Díky mlaďoši takováhle úcta k starším se cení. Pohlídali byste to tu chvilku místo mě?\n" +
                    " Jen si skočim na jedno a hned jsem zpátky";
        }
        if (npcName.equals("stráž") && itemName.equals("pivo")) {
            inventory.removeItem(itemName);
            currentLocation.removeNpc("stráž");
            return "\nStráž: Pivčo no jooooo. Pohlídali byste to tu chvilku místo mě?\n" +
                    " Jen si skočim a hned jsem zpátky.";
        }
        if (npcName.equals("malá_holčička") && itemName.equals("polštář")) {
            npc.setTalked(3);
            inventory.removeItem(itemName);
            Item itemK = npc.getItemInNpc("klacek");
            npc.removeItemInNpc("klacek");
            inventory.addItem(itemK);
            return "\nAwwwwwwwwwwwwwwwwww. To je ale úplně úžasně chlupatý polštářek!!! Opravdu mi ho jen tak dáte?\n" +
                    "Malá_holčička ti dala na oplátku klacek.";
        }
        if (npcName.equals("žebrák") && itemName.equals("peníz")) {
            npc.setTalked(3);
            Location entrance = gameState.getCurrentLocation().getExit("vchod").getTargetLocation();
            Item bear = new Item("pivo", true, "Prostě obyčejný pivo.");
            entrance.addItem(bear);
            inventory.removeItem(itemName);
            return "\nDíky díky. Jinak nejsem hlupák, moc dobře vím, kdo jste a koho tu hledáte. Takže infromace přímo pro vás.\n" +
                    "Vchod do podzemního vězení hlídá jen starý nevrlý dědek, který miluje chlast nadevšechno na světě.\n" +
                    "Vždycky si schovává aspoň jeden džbán s pivem poblíž, teď když o něm víte, tak to určitě najdete.";
        }
        if (npcName.equals("žebrák") && itemName.equals("chleba")) {
            npc.setTalked(3);
            inventory.removeItem(itemName);
            return "\nNo, pořád lepší než nic... Výměnou za něj vám můžu říct, že poblíž hory, které je za městem,\n" +
                    " si prý kdysi někdo schoval poklad.";
        }
        if (npcName.equals("tue") && itemName.equals("klacek")) {
            return "Tue: Kla--kla-cek opravdu mi prostě dáš klacek??? Jsem vám opravud až tak pro smích???\n" +
                    "///Tuovi, kterému po několika dnech strávených na tomoto příšerném místě, zbývala už jen trocha síla na to, aby se udržel při smyslech, \n" +
                    "tvoje nabídka klacku způsobila šok, který ho připravil o zbytek rozumu.  Popadl klacek a vší silou, tě s ním začal bodat a zabil tě.";
        }
        return "\nTohle téhle postavě dát nemůžeš.";
    }
}
