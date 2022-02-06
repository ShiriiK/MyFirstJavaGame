package logic.actions;

import gui.util.Constants;
import logic.*;
import logic.blueprints.Location;
import logic.blueprints.Npc;
import logic.blueprints.Player;
import logic.blueprints.Weapon;
import saving_tue.Main;

import java.util.Arrays;

/**
 * Třída implementující příkaz pro zaútočení na nepřítele.
 * @author Alena Kalivodová
 */

public class ActionEnhancedCombat implements IAction {
    private final String[] names = {"special_attack"};

    /**
     * The method used to identify the validity of commands.
     * @return possible command names
     */
    @Override
    public String[] getName() {
        return Arrays.copyOf(names, 1);
    }

    /**
     * Executes the attack command - attacks the npc (if possible), if the npc survives, it attacks back.
     * @param parameters one parameter - the name of the npc the player is attacking
     */
    @Override
    public String execute(String[] parameters) {

        GameState gameState = Main.game.getGameState();
        PhaseChecker.basicChecker();
        PhaseChecker.advancedChecker();

        if (parameters.length < 2) {
            return Constants.d1 + "Who do you want attack?" + Constants.d2;
        }
        if (parameters.length > 2) {
            return Constants.d1 + "You cannot attack multiple enemies at once or use multiple attacks." + Constants.d2;
        }

        String attackName = parameters[0];
        String npcName = parameters[1];
        Location currentLocation = gameState.getCurrentLocation();

        if (currentLocation.getNpc(npcName) == null) {
            return Constants.d1 + "You can't attack someone who's not here." + Constants.d2;
        }

        Npc attackedNpc = currentLocation.getNpc(npcName);

        if (attackedNpc.isFriendly()) {
            if ("gorm".equals(npcName)) {
                return Constants.d1 + "Why attack Gorm????" + Constants.d2;
            }
            return Constants.d1 + "There's no reason to attack this npc." + Constants.d2;
        }

        Player player = gameState.getPlayer();
        String race = gameState.getPlayer().getRace().getName();
        Weapon weapon = player.getPlayerWeapon();
        //síla hráče = str * multiplikátor zbraně
        double playerStr = player.getStr();
        //hp hráče ve chvíli,kdy zaútočil
        double playerHp = player.getHp();
        //bere se ve chvíli, kdy hráč útočí, tudíž v něm není zahrnutý bonus dmg, který si útokem může nastavit
        double bonusDmg = gameState.getPlayer().getBonusDmg();
        //bere se ve chvíli, kdy hráč útočí, tudíž v něm není zahrnutý negeted dmg, který si útokem může nastavit
        double negetedDmg = gameState.getPlayer().getNegetedDmg();
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
        //bonusové dmg ze zbraně
        double weaponBonusDmg = weapon.getBonusDmg();
        double weaponBonusBlock = weapon.getBonusBlock();
        double weaponBonusSpecialAttack = weapon.getBonusSpecialAttack();
        double weaponBonusCharge = weapon.getBonusCharge();


        if(attackName.equals("útok_s_úskokem")){
            dmg = playerStr/2.0 + bonusDmg;
            gameState.getPlayer().setNegetedDmg(20.0 + weaponBonusBlock);

            setNegetedDmg = 10.0;
        }
        if(attackName.equals("útok_z_blízka")) {
            dmg = playerStr + bonusDmg+ weaponBonusDmg;
        }

        boolean usedAttack = gameState.getPlayer().isUsedAttack3();
        boolean usedCharge = gameState.getPlayer().isUsedCharge();

        switch(race){
            case("elf"):
                if(attackName.equals("volání_entů") && !usedAttack){
                    dmg = dmg + 35.0 + weaponBonusSpecialAttack;
                    gameState.getPlayer().setUsedAttack3(true);
                    break;
                } else if (attackName.equals("elfí_běsnění") && !usedCharge) {
                    dmg = bonusDmg + weaponBonusCharge;
                    setNegetedDmg = 50.0;
                    gameState.getPlayer().setUsedCharge(true);
                    break;
                }
            case("temný_elf"):
                if(attackName.equals("pomatení") && !usedAttack){
                    dmg = dmg + weaponBonusSpecialAttack;
                    setNegetedDmg = 50.0;
                    gameState.getPlayer().setUsedAttack3(true);
                    break;
                } else if (attackName.equals("volání_krve") && !usedCharge) {
                    dmg = dmg + weaponBonusCharge;
                    setBonusDmg = 40.0;
                    gameState.getPlayer().setUsedCharge(true);
                    break;
                }
            case("barbar"):
                if(attackName.equals("zuřivý_skok") && !usedAttack){
                    dmg = dmg + 50.0 + weaponBonusSpecialAttack;
                    gameState.getPlayer().setUsedAttack3(true);
                    break;
                } else if (attackName.equals("bojový_tanec") && !usedCharge) {
                    dmg = dmg + weaponBonusCharge;
                    setNegetedDmg = -10.0;
                    setBonusDmg = 70.0;
                    gameState.getPlayer().setUsedCharge(true);
                    break;
                }
            case("trpaslík"):
                if(attackName.equals("přivolání_blesků") && !usedAttack){
                    dmg = dmg + 40.0 + weaponBonusSpecialAttack;
                    gameState.getPlayer().setUsedAttack3(true);
                    break;
                } else if (attackName.equals("runová_bouře") && !usedCharge) {
                    dmg = dmg + 80.0 + weaponBonusCharge;
                    setNegetedDmg = -10.0;
                    gameState.getPlayer().setUsedCharge(true);
                    break;
                }
            case("člověk"):
                if(attackName.equals("meč_spravedlnosti") && !usedAttack){
                    dmg = dmg + 40.0 + weaponBonusSpecialAttack;
                    gameState.getPlayer().setUsedAttack3(true);
                    break;
                } else if (attackName.equals("modlitba") && !usedCharge) {
                    dmg = dmg + weaponBonusCharge;
                    setBonusDmg = 40.0;
                    setNegetedDmg = 40.0;
                    gameState.getPlayer().setUsedCharge(true);
                    break;
                }
            case("mág"):
                if(attackName.equals("ohnivá_koule") && !usedAttack){
                   dmg = 60.0 + bonusDmg + weaponBonusSpecialAttack;
                   gameState.getPlayer().setUsedAttack3(true);
                   break;
                } else if (attackName.equals("zaklínání") && !usedCharge){
                    dmg = dmg + weaponBonusCharge;
                    setBonusDmg = 30.0;
                    setNegetedDmg = 30.0;
                    gameState.getPlayer().setUsedCharge(true);
                    break;
                }
                if(!attackName.equals("útok_z_blízka") && !attackName.equals("útok_s_úskokem")){
                    return  Constants.d1 + "Tvoje postava nemá takový útok nebo byl již vyčerpán maximální počet použití." + Constants.d2;
                }
        }
        String killed = killedNpc(gameState, npcName, currentLocation, npcHp, dmg);
        if (killed != null) return killed;

        return message(gameState, npcName, attackedNpc, player, npcStr, dmg, setBonusDmg,setNegetedDmg);
    }

    private String message(GameState gameState, String npcName, Npc attackedNpc,
                           Player player, double npcStr, double dmg, double setBonusDmg, double setNegetedDmg) {
        if (!gameState.isInCombat()){
            gameState.setInCombat(true);
        }
        gameState.getPlayer().setRound(gameState.getPlayer().getRound() + 1);

        //NASTAVOVÁNÍ MESSEGŮ
        String dmgMessege = "";
        double newNpcHp = attackedNpc.getHp()-dmg;
        if ((dmg >0.0)) {
            dmgMessege = "Dal/a si " + dmg + " poškození.\n Tvůj oponent teď má " + newNpcHp + " životů.\n";
        }
        String negetedMessege = "";
        //spojení nevyužitých bloků z předchozích kol s právě získaným blokem
        double negetedDmg = gameState.getPlayer().getNegetedDmg() + setNegetedDmg;
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
        gameState.getPlayer().setNegetedDmg(restOfNegeted);
        gameState.getPlayer().setBonusDmg(setBonusDmg);

        if(restOfHp < 0.0){
            Main.game.setTheEnd(true);
            return Constants.d1 + "Umřel/a jsi." + Main.game.epilog() + Constants.d2;
        }

        return  Constants.d1 + dmgMessege + negetedMessege + bonusMessege + takenDmgMessege + "Teď máš " + restOfHp + " životů." + Constants.d2;
    }

    private String killedNpc(GameState gameState, String npcName, Location currentLocation, double npcHp, double dmg) {
        if (npcHp <= dmg) {
            currentLocation.removeNpc(npcName);
            gameState.getPlayer().setUsedCharge(false);
            gameState.getPlayer().setUsedAttack3(false);
            gameState.getPlayer().setNegetedDmg(0.0);
            gameState.getPlayer().setBonusDmg(0.0);
            gameState.setInCombat(false);
            gameState.getPlayer().setUsedCharge(false);
            gameState.getPlayer().setUsedAttack3(false);
            gameState.getPlayer().setRound(0);
            return Constants.d1 + "Zabil/a jste: " + npcName + "." + Constants.d2;
        }
        return null;
    }
}
