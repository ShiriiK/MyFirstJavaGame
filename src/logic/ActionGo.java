package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro pohyb mezi lokacemi.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */

public class ActionGo implements IAction {
    private Game game;
    private String[] names = {"jít", "jít_do", "jít_k"};

    /**
     * Konstuktor
     *
     * @param game hra ve které bude příkaz vykonán
     */
    public ActionGo(Game game) {
        this.game = game;
    }

    /**
     * Metoda použitá pro identifikování platnosti příkazů.
     *
     * @return možné názvy příkazů
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 3);
    }

    /**
     * Provádí příkaz go - přesune hráče z jedné lokace do druhé.
     *
     * @param parameters jeden parametr - cílová lokace
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return "\nVyber si pohlaví, než někam půjdeš.";
        }
        if (phase == 1) {
            return "\nVyber si jméno, než někam půjdeš.";
        }
        if (parameters.length < 1) {
            return "\nA kam to bude?";
        }
        if (parameters.length > 1) {
            return "\nNemůžeš jít na víc míst najednou.";
        }

        String targetLocationName = parameters[0];
        Location currentLocation = gameState.getCurrentLocation();

        if (currentLocation.getExit(targetLocationName) == null) {
            return "\nOdtud se tam nedostaneš.";
        }

        Exit targetLocationExit = currentLocation.getExit(targetLocationName);
        Location targetLocation = targetLocationExit.getTargetLocation();

        if (phase != targetLocation.getPhase()) {
            return "\nOpravdu si myslíš, že tě Gorm nechá opustit oblast kempu bez zbraně? Nějakou si vem.";
        }

        for (Npc npc : currentLocation.getNpcs()) {
            if (npc.equals(targetLocationExit.containsNpc(npc))) {
                return "\n" + npc.getMessage();
            }
        }


        Inventory inventory = gameState.getInventory();

        if (targetLocationName.equals("žalář") && !inventory.containsItem("pochodeň")) {
            return "\nBez pochodně tam nejdeš.";
        }
        if (targetLocationName.equals("cela3")
                && !inventory.containsItem("klíč") && !inventory.containsItem("univerzální_klíč")) {
            return "\nTahle cela je zamčená.";
        }
        if (targetLocationName.equals("nádvoří")) {
            return "\nTy mi opravdu nevěříš co?";
        }

        Player player = gameState.getPlayer();
        double playerHp = player.getHp();
        double dmg = targetLocationExit.getDamage();
        String description = targetLocation.longDescription();

        if (targetLocationName.equals("cela2")) {
            if (playerHp < dmg) {
                game.setTheEnd(true);
                return "\nNěkdo tě napadl potom, co jsi vstoupil/a do cely a zabil tě.";
            } else {
                player.setHp(playerHp - dmg);
                gameState.setCurrentLocation(targetLocation);
                return "\n" + description + "\nNěkdo tě bruálně napadl zezadu.\n" +
                        targetLocationExit.getDamageMessage();
            }
        }
        if (targetLocationName.equals("hora")) {
            player.setHp(playerHp - dmg);
            gameState.setCurrentLocation(targetLocation);
            return "\n" + description + "\nZaútočili na tebe divocí králíci.\n" +
                    targetLocationExit.getDamageMessage();
        }
        if (targetLocationName.equals("les")) {
            player.setHp(playerHp - dmg);
            gameState.setCurrentLocation(targetLocation);
            return "\n" + description + "\nZaútočil na tebe troll.\n" +
                    targetLocationExit.getDamageMessage();
        }

        gameState.setCurrentLocation(targetLocation);
        return "\n" + description;
    }
}

