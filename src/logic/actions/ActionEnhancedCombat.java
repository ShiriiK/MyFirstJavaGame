package logic.actions;

import gui.util.Constants;
import logic.*;
import logic.blueprints.*;
import logic.factories.AttackFactory;
import logic.factories.RaceFactory;
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
        Weapon weapon = player.getPlayerWeapon();


        int dmg = player.getBonusDmg();
        int bonusDmg = player.getBonusDmg();
        int negetableDmg = player.getNegetedDmg();
        int newBonusDmg = 0;
        int newNegetableDmg = 0;

        if(attackName.equals("basic_attack")) {
            dmg += player.getStr() + weapon.getBonusDmg();
        }
        if(attackName.equals("dodge")){
            newNegetableDmg += 20 + weapon.getBonusBlock();
            player.setNegetedDmg(player.getNegetedDmg() + newNegetableDmg);
        }
        if(attackName.equals("special_attack")) {
            SpecialAttack specialAttack = AttackFactory.specialAttacks.get(RaceFactory.getRace(player.getRace().getName()).getName());
            if(specialAttack.getDmg() != 0){
                dmg += weapon.getBonusSpecialAttack() + specialAttack.getDmg();
            }
            if(specialAttack.getNegetableDmg() != 0){
                newNegetableDmg += weapon.getBonusSpecialAttack() + specialAttack.getNegetableDmg();
                player.setNegetedDmg(player.getNegetedDmg() + newNegetableDmg);
            }
            player.setUsedSpecialAttacks(true);
        }
        if (attackName.equals("charge_attack")){
            ChargeAttack chargeAttack = AttackFactory.chargeAttacks.get(RaceFactory.getRace(player.getRace().getName()).getName());
            if(chargeAttack.getDmg() != 0){
                dmg += weapon.getBonusCharge() + chargeAttack.getDmg();
            }
            if(chargeAttack.getBonusDmg() != 0){
                newBonusDmg += weapon.getBonusCharge() + chargeAttack.getBonusDmg();

            }
            if(chargeAttack.getNegetableDmg() != 0){
                negetableDmg += weapon.getBonusCharge() + chargeAttack.getNegetableDmg();
                player.setNegetedDmg(player.getNegetedDmg() + newNegetableDmg);
            }
            player.setUsedCharge(true);
        }

        String damageMessage = "";
        int newNpcHp = attackedNpc.getHp();
        if (dmg != 0){
            newNpcHp -= dmg;
            attackedNpc.setHp(newNpcHp);
            player.setBonusDmg(0);
            damageMessage = "You've dealt " + dmg + " damage.\nYour opponent now has " + newNpcHp + " hp.\n";
        }

        String negetedDmgMessage = "";
        int block = negetableDmg + newNegetableDmg;
        if ((negetableDmg + newNegetableDmg) > 0){
            negetedDmgMessage = "You can block " + block + " damage.\n";

        }

        String bonusDmgMessage = "";
        if (newBonusDmg != 0){
            bonusDmgMessage = "Next round you will deal " + bonusDmg + " damage more.\n";
            player.setBonusDmg(newBonusDmg);
        }

        String takenDmgMessage = "";

        int restOfNegetable;
        if ((block - attackedNpc.getStr()) > 0) {
            restOfNegetable = (block-attackedNpc.getStr());
            player.setNegetedDmg(restOfNegetable);
        } else {
            restOfNegetable = 0;
            player.setNegetedDmg(0);
        }

        int takenDmg;
        if((attackedNpc.getStr()-negetableDmg) > 0){
            takenDmg = attackedNpc.getStr() - negetableDmg;
        } else if (block < 0){
            takenDmg = attackedNpc.getStr() - negetableDmg;
            player.setNegetedDmg(0);
        } else {
            takenDmg = 0;
        }

        int restOfHp = player.getHp() - takenDmg;

        if (block > 0){
            if (attackedNpc.getStr() < block){
                takenDmgMessage = "You've blocked " + attackedNpc.getStr() + " damage and you can still block " + restOfNegetable + " damage.";
            } else {
                takenDmgMessage = "You've blocked " + negetableDmg + " and you are left with no more block. " + npcName.toUpperCase() + " caused " + takenDmg +
                "You now have " + restOfHp + " hp.";
            }
        } else {
            takenDmgMessage = npcName.toUpperCase() + " caused " + takenDmg + " damage." + " You now have " + restOfHp + " hp.";
        }


        String dead = "";
        if (restOfHp < 0){
            Main.game.setTheEnd(true);
            dead = "You died.\n";
        }

        String npcDead = "";
        if (newNpcHp <= 0){
            currentLocation.removeNpc(npcName);
            gameState.getPlayer().setUsedCharge(false);
            gameState.getPlayer().setUsedSpecialAttacks(false);
            gameState.getPlayer().setNegetedDmg(0);
            gameState.getPlayer().setBonusDmg(0);
            gameState.getPlayer().setRound(0);
            gameState.setInCombat(false);
            npcDead = "You've killed " + npcName;
        }
        return damageMessage + negetedDmgMessage + bonusDmgMessage + takenDmgMessage + dead + npcDead;
    }
}
