package logic;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro zaútočení na nepřítele.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-01
 */

public class ActionEnhancedCombat implements IAction {
    private Game game;
    private String[] names = {"speciální_útok"};

    /**
     * Konstuktor
     *
     * @param game hra ve které bude příkaz vykonán
     */
    public ActionEnhancedCombat(Game game) {
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
     * Provádí příkaz attack - zaútočí na npc (když je to možné), pokud to npc přežije, tak útok oplatí.
     *
     * @param parameters jeden parametr - jméno npc, na které hráč útočí
     * @return zpráva, která se vypíše hráči
     */
    @Override
    public String execute(String[] parameters) {
        String d1 = Game.makeItLookGood1();
        String d2 = Game.makeItLookGood2();

        GameState gameState = game.getGameState();
        int phase = gameState.getPhase();
        if (phase == 0) {
            return d1 + "Před útočením si vyber pohlaví." + d2;
        }
        if (phase == 1) {
            return d1 + "Před útočením si vyber jméno." + d2;
        }
        if (phase == 2) {
            return d1 + "Je těžké útočit, když nemáš zbraň." + d2;
        }
        if (parameters.length < 2) {
            return d1 + "Na koho chceš použít který útok?" + d2;
        }
        if (parameters.length > 2) {
            return d1 + "Nemůžeš útočit na víc nepřátel najednou nebo používat víc různých útoků." + d2;
        }

        String attackName = parameters[0];
        String npcName = parameters[1];
        Location currentLocation = gameState.getCurrentLocation();

        if (currentLocation.getNpc(npcName) == null) {
            return d1 + "Nemůžeš útočit na někoho, kdo tu není." + d2;
        }

        Npc attackedNpc = currentLocation.getNpc(npcName);

        if (attackedNpc.isFriendly()) {
            if ("gorm".equals(npcName)) {
                return d1 + "Proč útočit na Gorma????" + d2;
            }
            return d1 + "Není důvod útočit na toto npc." + d2;
        }

        Player player = gameState.getPlayer();
        String race = gameState.getPlayer().getRace().getName();
        double playerStr = player.getStr();
        double npcHp = attackedNpc.getHp();
        double playerHp = player.getHp();
        double npcStr = attackedNpc.getStr();
        double dmg = 0;
        double bonusDmg = gameState.getBonusDmg();
        double negetedDmg = gameState.getNegetedDmg();

        if(attackName.equals("útok_z_dálky")){
            dmg = playerStr/2.0 + bonusDmg;
            gameState.setNegetedDmg(5.0);

            String killed = killedNpc(d1, d2, gameState, npcName, currentLocation, npcHp, dmg);
            if (killed != null) return killed;

            String death = playerPossibleDeath(d1, d2, attackedNpc, player, npcHp, playerHp, npcStr, dmg, negetedDmg);
            if (death != null) return death;

            //Message při pokračování souboje
            return message(d1, d2, gameState, npcName, attackedNpc, player, npcStr, dmg);
        }
        if(attackName.equals("útok_z_blízka")) {
            dmg = playerStr + bonusDmg;

            String killed = killedNpc(d1, d2, gameState, npcName, currentLocation, npcHp, dmg);
            if (killed != null) return killed;

            String death = playerPossibleDeath(d1, d2, attackedNpc, player, npcHp, playerHp, npcStr, dmg, negetedDmg);
            if (death != null) return death;

            //Message při pokračování souboje
            return message(d1, d2, gameState, npcName, attackedNpc, player, npcStr, dmg);
        }

        switch(race){
            case("elf"):
                if(attackName.equals("volání_entů")){
                    dmg = 15.0 + bonusDmg;

                    String killed = killedNpc(d1, d2, gameState, npcName, currentLocation, npcHp, dmg);
                    if (killed != null) return killed;

                    String death = playerPossibleDeath(d1, d2, attackedNpc, player, npcHp, playerHp, npcStr, dmg, negetedDmg);
                    if (death != null) return death;

                    //Message při pokračování souboje
                    return message(d1, d2, gameState, npcName, attackedNpc, player, npcStr, dmg);
                } else if (attackName.equals("elfí_běsnění")) {
                    dmg = 0.0 + bonusDmg;
                    gameState.setNegetedDmg(50.0);

                    String death = playerPossibleDeath(d1, d2, attackedNpc, player, npcHp, playerHp, npcStr, dmg, negetedDmg);
                    if (death != null) return death;

                    //Message při pokračování souboje
                    return message(d1, d2, gameState, npcName, attackedNpc, player, npcStr, dmg);
                }
            case("temný_elf"):
                if(attackName.equals("pomatení")){
                    attackedNpc.setStr(1.0);

                    String death = playerPossibleDeath(d1, d2, attackedNpc, player, npcHp, playerHp, npcStr, dmg, negetedDmg);
                    if (death != null) return death;

                    //Message při pokračování souboje
                    return message(d1, d2, gameState, npcName, attackedNpc, player, npcStr, dmg);
                } else if (attackName.equals("volání_krve")) {
                    dmg = 0.0;
                    gameState.setBonusDmg(15.0);

                    String death = playerPossibleDeath(d1, d2, attackedNpc, player, npcHp, playerHp, npcStr, dmg, negetedDmg);
                    if (death != null) return death;

                    //Message při pokračování souboje
                    return message(d1, d2, gameState, npcName, attackedNpc, player, npcStr, dmg);
                }
            case("barbar"):
                if(attackName.equals("zuřivý_skok")){
                    dmg = 20 + bonusDmg;
                    String killed = killedNpc(d1, d2, gameState, npcName, currentLocation, npcHp, dmg);
                    if (killed != null) return killed;

                    String death = playerPossibleDeath(d1, d2, attackedNpc, player, npcHp, playerHp, npcStr, dmg, negetedDmg);
                    if (death != null) return death;

                    //Message při pokračování souboje
                    return message(d1, d2, gameState, npcName, attackedNpc, player, npcStr, dmg);
                } else if (attackName.equals("bojový_tanec")) {
                    gameState.setNegetedDmg(-10.0);
                    gameState.setBonusDmg(20.0);

                    String death = playerPossibleDeath(d1, d2, attackedNpc, player, npcHp, playerHp, npcStr, dmg, negetedDmg);
                    if (death != null) return death;

                    //Message při pokračování souboje
                    return message(d1, d2, gameState, npcName, attackedNpc, player, npcStr, dmg);
                }
            case("trpaslík"):
                if(attackName.equals("přivolání_blesků")){
                    dmg = 20 + bonusDmg;
                    String killed = killedNpc(d1, d2, gameState, npcName, currentLocation, npcHp, dmg);
                    if (killed != null) return killed;

                    String death = playerPossibleDeath(d1, d2, attackedNpc, player, npcHp, playerHp, npcStr, dmg, negetedDmg);
                    if (death != null) return death;

                    //Message při pokračování souboje
                    return message(d1, d2, gameState, npcName, attackedNpc, player, npcStr, dmg);
                } else if (attackName.equals("runová_bouře")) {
                    gameState.setNegetedDmg(-5.0);
                    attackedNpc.setStr(attackedNpc.getStr()-20);

                    String death = playerPossibleDeath(d1, d2, attackedNpc, player, npcHp, playerHp, npcStr, dmg, negetedDmg);
                    if (death != null) return death;

                    //Message při pokračování souboje
                    return message(d1, d2, gameState, npcName, attackedNpc, player, npcStr, dmg);
                }
            case("člověk"):
                if(attackName.equals("meč_spravedlnosti")){
                    dmg = 15 + bonusDmg;

                    String killed = killedNpc(d1, d2, gameState, npcName, currentLocation, npcHp, dmg);
                    if (killed != null) return killed;

                    String death = playerPossibleDeath(d1, d2, attackedNpc, player, npcHp, playerHp, npcStr, dmg, negetedDmg);
                    if (death != null) return death;

                    //Message při pokračování souboje
                    return message(d1, d2, gameState, npcName, attackedNpc, player, npcStr, dmg);
                } else if (attackName.equals("modlitba")) {
                    gameState.setNegetedDmg(15.0);
                    gameState.setBonusDmg(15.0);

                    String death = playerPossibleDeath(d1, d2, attackedNpc, player, npcHp, playerHp, npcStr, dmg, negetedDmg);
                    if (death != null) return death;

                    //Message při pokračování souboje
                    return message(d1, d2, gameState, npcName, attackedNpc, player, npcStr, dmg);
                }
            case("mág"):
                if(attackName.equals("ohnivá_koule")){
                   dmg = 25.0 + bonusDmg;

                    String killed = killedNpc(d1, d2, gameState, npcName, currentLocation, npcHp, dmg);
                    if (killed != null) return killed;

                    String death = playerPossibleDeath(d1, d2, attackedNpc, player, npcHp, playerHp, npcStr, dmg, negetedDmg);
                    if (death != null) return death;

                    //Message při pokračování souboje
                    return message(d1, d2, gameState, npcName, attackedNpc, player, npcStr, dmg);
                } else if (attackName.equals("zaklínání")){
                    attackedNpc.setStr(1.0);
                    gameState.setBonusDmg(15.0);

                    String death = playerPossibleDeath(d1, d2, attackedNpc, player, npcHp, playerHp, npcStr, dmg, negetedDmg);
                    if (death != null) return death;

                    //Message při pokračování souboje
                    return message(d1, d2, gameState, npcName, attackedNpc, player, npcStr, dmg);
                }
        }
            return  "Tvoje postava nemá takový útok.";
    }

    private String message(String d1, String d2, GameState gameState, String npcName, Npc attackedNpc, Player player, double npcStr, double dmg) {
        gameState.setInCombat(true);
        return  d1 + "Dal/a si " + dmg + " poškození. Tvůj oponent teď má " + attackedNpc.getHp() + " životů.\n" +
                "Příští kolo vyblokuješ navíc " + gameState.getNegetedDmg() + " poškození a dáš navíc " + gameState.getBonusDmg() +
                " poškození.\n" + npcName + " ti útok oplatil a způsobil ti " + npcStr +
                " poškození. Teď máš " + player.getHp() + " životů." + d2;
    }

    private String playerPossibleDeath(String d1, String d2, Npc attackedNpc, Player player, double npcHp, double playerHp, double npcStr, double dmg, double negetedDmg) {
        attackedNpc.setHp(npcHp - dmg);
        double npcDmg = 0;
        if(npcStr<negetedDmg){
            npcDmg = 0;
        } else {
            npcDmg = npcStr-negetedDmg;
        }
        player.setHp(playerHp - npcDmg);

        if (playerHp < npcStr) {
            game.setTheEnd(true);
            return d1 + "Umřel/a jsi." + d2;
        }
        return null;
    }

    private String killedNpc(String d1, String d2, GameState gameState, String npcName, Location currentLocation, double npcHp, double dmg) {
        if (npcHp <= dmg) {
            currentLocation.removeNpc(npcName);
            gameState.setUsedCharge(false);
            gameState.setUsedAttack3(false);
            gameState.setNegetedDmg(0);
            gameState.setBonusDmg(0);
            gameState.setInCombat(false);
            return d1 + "Zabil/a jste: " + npcName + "." + d2;
        }
        return null;
    }
}
