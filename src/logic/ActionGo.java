package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro pohyb mezi lokacemi.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-01
 */

public class ActionGo implements IAction {
    private Game game;
    private String[] names = {"jdi", "jdi_do", "běž", "běž_do"};

    //Konstruktor
    public ActionGo(Game game) {
        this.game = game;
    }

    /**
     * Metoda použitá pro identifikování platnosti příkazů.
     * @return možné názvy příkazů
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 4);
    }

    /**
     * Provádí příkaz go - přesune hráče z jedné lokace do druhé.
     * @param parameters jeden parametr - cílová lokace
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {
        String d1 = Game.makeItLookGood1();
        String d2 = Game.makeItLookGood2();

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return d1 + "Vyber si pohlaví, než někam půjdeš." + d2;
        }
        if (phase == 1) {
            return d1 + "Vyber si jméno, než někam půjdeš." + d2;
        }
        if (parameters.length < 1) {
            return d1 + "A kam to bude?" + d2;
        }
        if (parameters.length > 1) {
            return d1 + "Nemůžeš jít na víc míst najednou." + d2;
        }

        String targetLocationName = parameters[0];
        Location currentLocation = gameState.getCurrentLocation();

        if (currentLocation.getExit(targetLocationName) == null) {
            return d1 + "Odtud se tam nedostaneš." + d2;
        }

        Exit targetLocationExit = currentLocation.getExit(targetLocationName);
        Location targetLocation = targetLocationExit.getTargetLocation();

        Inventory inventory = gameState.getInventory();

        if (targetLocationName.equals("žalář") && !inventory.getContent().containsKey("pochodeň")) {
            return d1 + "Bez zdroje světla tam nejdeš." + d2;
        }
        if (targetLocationName.equals("cela_na_pravo")
                && !inventory.getContent().containsKey("klíč") && !inventory.getContent().containsKey("univerzální_klíč")) {
            return d1 + "Tahle cela je zamčená." + d2;
        }
        if (targetLocationName.equals("nádvoří")) {
            return d1 + "Ty mi opravdu nevěříš co?" + d2;
        }

        if (phase < targetLocation.getPhase()) {
            return d1 + "Opravdu si myslíš, že tě Gorm nechá opustit oblast kempu bez zbraně? Nějakou si vem." + d2;
        }

        if (gameState.isInCombat()) {
            return d1 + "Neutíkej a nejdřív dobojuj." + d2;
        }

        for (Npc npc : currentLocation.getNpcs()) {
            if (npc.equals(targetLocationExit.containsNpc(npc))) {
                return d1 + npc.getMessage() + d2;
            }
        }

        Player player = gameState.getPlayer();
        double playerHp = player.getHp();
        double dmg = targetLocationExit.getDamage();
        String description = targetLocation.longDescription();

        if (targetLocationName.equals("cela_uprostřed")) {
            if (playerHp < dmg) {
                game.setTheEnd(true);
                return d1 + "Někdo tě napadl potom, co jsi vstoupil/a do cely a zabil tě." + d2;
            } else {
                player.setHp(playerHp - dmg);
                gameState.setCurrentLocation(targetLocation);
                gameState.setAttackedNpc("brutální_stráž");
                gameState.setInCombat(true);
                return d1+ description + "\nNěkdo tě bruálně napadl zezadu." + d2 +
                        targetLocationExit.getDamageMessage();
            }
        }
        if (targetLocationName.equals("hora") && !targetLocation.getNpcs().isEmpty()) {
            player.setHp(playerHp - dmg);
            gameState.setCurrentLocation(targetLocation);
            gameState.setAttackedNpc("medvěd");
            gameState.setInCombat(true);
            return d1 + description + "\nZaútočila na tebe divoká zvířata." + d2 +
                    targetLocationExit.getDamageMessage();
        }
        if (targetLocationName.equals("les")  && !targetLocation.getNpcs().isEmpty()) {
            player.setHp(playerHp - dmg);
            gameState.setCurrentLocation(targetLocation);
            gameState.setAttackedNpc("troll");
            gameState.setInCombat(true);
            return d1 + description + "\nZaútočil na tebe troll." + d2 +
                    targetLocationExit.getDamageMessage();

        }

        gameState.setCurrentLocation(targetLocation);
        return description;
    }
}

