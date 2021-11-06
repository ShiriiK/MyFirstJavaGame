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
        double dmg = 0;
        //bonus dmg nastavený toto kolo
        double setBonusDmg = 0.0;
        //negeted dmg nastavený toto kolo
        double setNegetedDmg = 0.0;
        //čílso, o kolik se sníží původní síla npc
        double loweredStr = 0.0;
        //nastavení se na true pokud hráč spellem nastaví npc str na 1
        boolean setNpcDmgToOne = false;

        if(attackName.equals("útok_z_dálky")){
            dmg = playerStr/2.0 + bonusDmg;
            gameState.setNegetedDmg(10.0);

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
                    dmg = 15.0 + bonusDmg;
                    gameState.setUsedAttack3(true);
                    break;
                } else if (attackName.equals("elfí_běsnění") && !usedCharge) {
                    dmg = 0.0 + bonusDmg;
                    setNegetedDmg = 50.0;
                    gameState.setUsedCharge(true);
                    break;
                }
            case("temný_elf"):
                if(attackName.equals("pomatení") && !usedAttack){
                    attackedNpc.setStr(1.0);
                    setNpcDmgToOne = true;
                    gameState.setUsedAttack3(true);
                    break;
                } else if (attackName.equals("volání_krve") && !usedCharge) {
                    dmg = 0.0 + bonusDmg;
                    setBonusDmg = 15.0;
                    gameState.setUsedCharge(true);
                    break;
                }
            case("barbar"):
                if(attackName.equals("zuřivý_skok") && !usedAttack){
                    dmg = 20 + bonusDmg;
                    gameState.setUsedAttack3(true);
                    break;
                } else if (attackName.equals("bojový_tanec") && !usedCharge) {
                    setNegetedDmg = -10.0;
                    setBonusDmg = 20.0;
                    gameState.setUsedCharge(true);
                    break;
                }
            case("trpaslík"):
                if(attackName.equals("přivolání_blesků") && !usedAttack){
                    dmg = 20 + bonusDmg;
                    gameState.setUsedAttack3(true);
                    break;
                } else if (attackName.equals("runová_bouře") && !usedCharge) {
                    attackedNpc.setStr(attackedNpc.getStr()-20);
                    setNegetedDmg = -5.0;
                    loweredStr = -20.0;
                    gameState.setUsedCharge(true);
                    break;
                }
            case("člověk"):
                if(attackName.equals("meč_spravedlnosti") && !usedAttack){
                    dmg = 15 + bonusDmg;
                    gameState.setUsedAttack3(true);
                    break;
                } else if (attackName.equals("modlitba") && !usedCharge) {
                    setBonusDmg = 15.0;
                    setNegetedDmg = 15.0;
                    gameState.setUsedCharge(true);
                    break;
                }
            case("mág"):
                if(attackName.equals("ohnivá_koule") && !usedAttack){
                   dmg = 25.0 + bonusDmg;
                   gameState.setUsedAttack3(true);
                   break;
                } else if (attackName.equals("zaklínání") && !usedCharge){
                    attackedNpc.setStr(1.0);
                    setBonusDmg = 15.0;
                    setNpcDmgToOne = true;
                    gameState.setUsedCharge(true);
                    break;
                }
                if(!attackName.equals("útok_z_blízka") && !attackName.equals("útok_z_dálky")){
                    return  d1 + "Tvoje postava nemá takový útok nebo byl již vyčerpán maximální počet použití." + d2;
                }
        }
        String killed = killedNpc(d1, d2, gameState, npcName, currentLocation, npcHp, dmg);
        if (killed != null) return killed;

        return message(d1, d2, gameState, npcName, attackedNpc, player, npcStr, dmg, setBonusDmg,setNegetedDmg, setNpcDmgToOne, loweredStr);
    }

    private String message(String d1, String d2, GameState gameState, String npcName, Npc attackedNpc, Player player, double npcStr, double dmg, double setBonusDmg, double setNegetedDmg, boolean setNpcDmgToOne, double loweredStr) {
        gameState.setInCombat(true);

        //NASTAVOVÁNÍ MESSEGŮ
        String dmgMessege = "";
        double newNpcHp = attackedNpc.getHp()-dmg;
        if ((dmg >0.0)) {
            dmgMessege = "Dal/a si " + dmg + " poškození.\n Tvůj oponent teď má " + newNpcHp + " životů.\n";
        }
        String negetedMessege = "";
        //spojení nevyužitých bloků z předchozích kol s právě získaným blokem
        double negetedDmg = gameState.getNegetedDmg() + setNegetedDmg;
        if(negetedDmg>0){
            negetedMessege = "Blok nastaven na " + negetedDmg + " poškození.\n";
        }
        String bonusMessege = "";
        if(setBonusDmg>0.0){
            bonusMessege = "Příští kolo dáš navíc " + setBonusDmg + " poškození.\n";
        }
        double newStr = npcStr-loweredStr;
        String npcStrMessege ="";
        if(loweredStr>0.0){
            if (newStr < 0.0) {
                newStr = 0.0;
            }
            npcStrMessege = "Síle tvého nepřítele byla snížena z " + npcStr + " na " + newStr + ".\n";
            attackedNpc.setStr(newStr);
        }
        String oneDmgMessege = "";
        if(setNpcDmgToOne){
            oneDmgMessege = "Snížení síly npc na naprosté minimum.\n";
            gameState.getAttackedNpc().setStr(1.0);
        }
        String takenDmgMessege = "";
        double restOfNegeted = negetedDmg - npcStr;
        double takenDmg = npcStr - negetedDmg;
        if(negetedDmg>0.0) {
            if (newStr < negetedDmg) {
                takenDmgMessege = "Vyblokováno " + npcStr + " poškození.\n Další kolo můžeš vyblokovat ještě " + restOfNegeted + "poškození.\n";
            } else {
                takenDmgMessege = "Vyblokováno " + negetedDmg + " poškození. Do dalšího kola už ti blok nezbyl a " + npcName + " ti dal " + takenDmg + "poškození.\n";
            }
        } else {
            takenDmgMessege = npcName + "ti dal " + npcStr + "poškození.\n";
        }

        //NASTAVENÍ HODNOT UVNITŘ HRY
        double restOfHp;
        if(takenDmg<0.0){
            restOfHp = player.getHp();
        } else {
            restOfHp = player.getHp() - takenDmg;
        }

        player.setHp(restOfHp);
        attackedNpc.setHp(newNpcHp);
        gameState.setNegetedDmg(restOfNegeted);
        gameState.setBonusDmg(setBonusDmg);

        if(restOfHp < 0.0){
            game.setTheEnd(true);
            return d1 + "Umřel/a jsi." + d2;
        }

        //Znovu vrátí dmg npc na jeho původní hodnotu
        attackedNpc.setStr(npcStr);

        return  d1 + dmgMessege + negetedMessege + bonusMessege + npcStrMessege + oneDmgMessege + takenDmgMessege + "Teď máš " + restOfHp + " životů." + d2;
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
            return d1 + "Zabil/a jste: " + npcName + "." + d2;
        }
        return null;
    }
}
