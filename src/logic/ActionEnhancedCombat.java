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

    //Konstruktor
    public ActionEnhancedCombat(Game game) {
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
     * Provádí příkaz attack - zaútočí na npc (když je to možné), pokud to npc přežije, tak útok oplatí.
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
        //síla hráče = str * multiplikátor zbraně
        double playerStr = player.getStr();
        //hp hráče ve chvíli,kdy zaútočil
        double playerHp = player.getHp();
        //bere se ve chvíli, kdy hráč útočí, tudíž v něm není zahrnutý bonus dmg, který si útokem může nastavit
        double bonusDmg = gameState.getBonusDmg();
        //bere se ve chvíli, kdy hráč útočí, tudíž v něm není zahrnutý negeted dmg, který si útokem může nastavit
        double negetedDmg = gameState.getNegetedDmg();
        //síla npc na začátku souboje
        double npcStr = attackedNpc.getStr();
        //hp npc na začátku souboje
        double npcHp = attackedNpc.getHp();
        //dmg, který hráč dá
        double dmg = bonusDmg;
        //bonus dmg nastavený toto kolo
        double setBonusDmg = 0.0;
        //negeted dmg nastavený toto kolo
        double setNegetedDmg = 0.0;


        if(attackName.equals("útok_z_dálky")){
            dmg = playerStr/2.0 + bonusDmg;
            gameState.setNegetedDmg(20.0);

            setNegetedDmg = 10.0;
        }
        if(attackName.equals("útok_z_blízka")) {
            dmg = playerStr + bonusDmg;
        }

        boolean usedAttack = gameState.isUsedAttack3();
        boolean usedCharge = gameState.isUsedCharge();

        switch(race){
            case("elf"):
                if(attackName.equals("volání_entů") && !usedAttack){
                    dmg = dmg + 35.0;
                    gameState.setUsedAttack3(true);
                    break;
                } else if (attackName.equals("elfí_běsnění") && !usedCharge) {
                    dmg = bonusDmg;
                    setNegetedDmg = 50.0;
                    gameState.setUsedCharge(true);
                    break;
                }
            case("temný_elf"):
                if(attackName.equals("pomatení") && !usedAttack){
                    setNegetedDmg = 50.0;
                    gameState.setUsedAttack3(true);
                    break;
                } else if (attackName.equals("volání_krve") && !usedCharge) {
                    setBonusDmg = 40.0;
                    gameState.setUsedCharge(true);
                    break;
                }
            case("barbar"):
                if(attackName.equals("zuřivý_skok") && !usedAttack){
                    dmg = dmg + 50.0 ;
                    gameState.setUsedAttack3(true);
                    break;
                } else if (attackName.equals("bojový_tanec") && !usedCharge) {
                    setNegetedDmg = -10.0;
                    setBonusDmg = 70.0;
                    gameState.setUsedCharge(true);
                    break;
                }
            case("trpaslík"):
                if(attackName.equals("přivolání_blesků") && !usedAttack){
                    dmg = dmg + 40.0;
                    gameState.setUsedAttack3(true);
                    break;
                } else if (attackName.equals("runová_bouře") && !usedCharge) {
                    dmg = dmg + 80.0;
                    setNegetedDmg = -10.0;
                    gameState.setUsedCharge(true);
                    break;
                }
            case("člověk"):
                if(attackName.equals("meč_spravedlnosti") && !usedAttack){
                    dmg = dmg + 40.0;
                    gameState.setUsedAttack3(true);
                    break;
                } else if (attackName.equals("modlitba") && !usedCharge) {
                    setBonusDmg = 40.0;
                    setNegetedDmg = 40.0;
                    gameState.setUsedCharge(true);
                    break;
                }
            case("mág"):
                if(attackName.equals("ohnivá_koule") && !usedAttack){
                   dmg = 60.0 + bonusDmg;
                   gameState.setUsedAttack3(true);
                   break;
                } else if (attackName.equals("zaklínání") && !usedCharge){
                    setBonusDmg = 30.0;
                    setNegetedDmg = 30.0;
                    gameState.setUsedCharge(true);
                    break;
                }
                if(!attackName.equals("útok_z_blízka") && !attackName.equals("útok_z_dálky")){
                    return  d1 + "Tvoje postava nemá takový útok nebo byl již vyčerpán maximální počet použití." + d2;
                }
        }
        String killed = killedNpc(d1, d2, gameState, npcName, currentLocation, npcHp, dmg);
        if (killed != null) return killed;

        return message(d1, d2, gameState, npcName, attackedNpc, player, npcStr, dmg, setBonusDmg,setNegetedDmg);
    }

    private String message(String d1, String d2, GameState gameState, String npcName, Npc attackedNpc,
                           Player player, double npcStr, double dmg, double setBonusDmg, double setNegetedDmg) {
        gameState.setInCombat(true);
        gameState.setRound(gameState.getRound() + 1);

        //NASTAVOVÁNÍ MESSEGŮ
        String dmgMessege = "";
        double newNpcHp = attackedNpc.getHp()-dmg;
        if ((dmg >0.0)) {
            dmgMessege = "Dal/a si " + dmg + " poškození.\n Tvůj oponent teď má " + newNpcHp + " životů.\n";
        }
        String negetedMessege = "";
        //spojení nevyužitých bloků z předchozích kol s právě získaným blokem
        double negetedDmg = gameState.getNegetedDmg() + setNegetedDmg;
        if(setNegetedDmg>0.0){
            negetedMessege = "Blok nastaven na " + negetedDmg + " poškození.\n";
        }
        String bonusMessege = "";
        if(setBonusDmg>0.0){
            bonusMessege = "Příští kolo dáš navíc " + setBonusDmg + " poškození.\n";
        }
        String takenDmgMessege = "";
        //zbytek bloku po odečtení útoku npc
        double restOfNegeted;
        if ((negetedDmg - npcStr) > 0.0) {
            restOfNegeted = (negetedDmg-npcStr);
        } else {
            restOfNegeted = 0.0;
        }

        //dmg, který dostane hráč, když je od něho odečten blok
        double takenDmg;
        if((npcStr-negetedDmg) > 0.0) {
            takenDmg = (npcStr-negetedDmg);
        } else if (negetedDmg<0.0){
            takenDmg = (npcStr - negetedDmg);
        } else {
            takenDmg = 0.0;
        }

        if(negetedDmg>0.0) {
            if (npcStr < negetedDmg) {
                takenDmgMessege = "Vyblokováno " + npcStr + " poškození.\n Další kolo můžeš vyblokovat ještě " + restOfNegeted + "poškození.\n";
            } else {
                takenDmgMessege = "Vyblokováno " + negetedDmg + " poškození. Do dalšího kola už ti blok nezbyl a " + npcName + " ti dal " + takenDmg + "poškození.\n";
            }
        } else {
            takenDmgMessege = npcName + "ti dal " + npcStr + "poškození.\n";
        }

        //NASTAVENÍ HODNOT UVNITŘ HRY
        double restOfHp = player.getHp() - takenDmg;

        player.setHp(restOfHp);
        attackedNpc.setHp(newNpcHp);
        gameState.setNegetedDmg(restOfNegeted);
        gameState.setBonusDmg(setBonusDmg);

        if(restOfHp < 0.0){
            game.setTheEnd(true);
            return d1 + "Umřel/a jsi." + game.epilog() + d2;
        }

        return  d1 + dmgMessege + negetedMessege + bonusMessege + takenDmgMessege + "Teď máš " + restOfHp + " životů." + d2;
    }

    private String killedNpc(String d1, String d2, GameState gameState, String npcName, Location currentLocation, double npcHp, double dmg) {
        if (npcHp <= dmg) {
            currentLocation.removeNpc(npcName);
            gameState.setUsedCharge(false);
            gameState.setUsedAttack3(false);
            gameState.setNegetedDmg(0.0);
            gameState.setBonusDmg(0.0);
            gameState.setInCombat(false);
            gameState.setUsedCharge(false);
            gameState.setUsedAttack3(false);
            gameState.setRound(0);
            return d1 + "Zabil/a jste: " + npcName + "." + d2;
        }
        return null;
    }
}
