package logic.factories;

import javafx.scene.control.Tooltip;
import logic.blueprints.Race;
import java.util.HashMap;

/**
 * Factory for creating tooltips
 */
public class ToolTipFactory {
    public static final Tooltip elfTip = new Tooltip("The elf gets five bonus power points and has the special attacks Call of the Ents and Elf Rage.");
    public static final Tooltip drowTip = new Tooltip("The drows gets five bonus power points and has the special attacks Confusion and Call of Blood.");
    public static final Tooltip barbarianTip = new Tooltip("The barbarian gets five bonus hit points and has the special attacks Furious Leap and Battle Dance.");
    public static final Tooltip dwarfTip = new Tooltip("A dwarf gets five bonus hit points and has the special attacks Summon Blight and Rune Storm.");
    public static final Tooltip humanTip = new Tooltip("A human gets three bonus points of Strength and Life and has the special attacks Sword of Justice and Prayer.");
    public static final Tooltip mageTip = new Tooltip("The mage gets three bonus points of power and life and has the special attacks Fireball and Conjuration.");

    public static final HashMap<Race, Tooltip> raceTips = new HashMap<Race, Tooltip>(){{
        put(RaceFactory.elf, elfTip);
        put(RaceFactory.drow, drowTip);
        put(RaceFactory.barbarian, barbarianTip);
        put(RaceFactory.dwarf, dwarfTip);
        put(RaceFactory.human, humanTip);
        put(RaceFactory.mage, mageTip);
    }};

    public static final Tooltip femaleTip = new Tooltip("When you choose to be a woman:\nYou'll have 40 hp and 15 str.");
    public static final Tooltip maleTip = new Tooltip("When you choose to be a man so:\nYou'll have 50 hp and 10 str.");

    public static final Tooltip elfSpecialAttackTip = new Tooltip(AttackFactory.cryOfEnts.getName() + " will deal " + AttackFactory.cryOfEnts.getDmg() + " damage.");
    public static final Tooltip drowSpecialAttackTip = new Tooltip(AttackFactory.delusion.getName() + " will negete " + AttackFactory.delusion.getNegetableDmg() + " damage.");
    public static final Tooltip barbarianSpecialAttackTip = new Tooltip(AttackFactory.furiousJump.getName() + " will deal " + AttackFactory.furiousJump.getDmg() + " damage.");
    public static final Tooltip dwarfSpecialAttackTip = new Tooltip(AttackFactory.lightningSummoning.getName() + " will deal " + AttackFactory.lightningSummoning.getDmg() + " damage.");
    public static final Tooltip humanSpecialAttackTip = new Tooltip(AttackFactory.swordOfJustice.getName() + " will deal " + AttackFactory.swordOfJustice.getDmg() + " damage.");
    public static final Tooltip mageSpecialAttackTip = new Tooltip(AttackFactory.fireball.getName() + " will deal " + AttackFactory.fireball.getDmg() + " damage.");

    public static final HashMap<Race, Tooltip> specialAttacksTips = new HashMap<Race, Tooltip>(){{
        put(RaceFactory.elf, elfSpecialAttackTip);
        put(RaceFactory.drow, drowSpecialAttackTip);
        put(RaceFactory.barbarian, barbarianSpecialAttackTip);
        put(RaceFactory.dwarf, dwarfSpecialAttackTip);
        put(RaceFactory.human, humanSpecialAttackTip);
        put(RaceFactory.mage, mageSpecialAttackTip);
    }};

    public static final Tooltip elfChargeTip = new Tooltip(AttackFactory.elvenFury.getName() + " will negate " + AttackFactory.elvenFury.getNegetableDmg() + " damage.");
    public static final Tooltip drowChargeTip = new Tooltip(AttackFactory.cryForBlood.getName() + " will accumulate " + AttackFactory.cryForBlood.getBonusDmg() + " bonus damage.");
    public static final Tooltip barbarianChargeTip = new Tooltip(AttackFactory.warDance.getName() + " will deal you " + AttackFactory.furiousJump.getNegetableDmg() + " damage. And accumulates " + AttackFactory.warDance.getBonusDmg() + " bonus damage.");
    public static final Tooltip dwarfChargeTip = new Tooltip(AttackFactory.runeStrom.getName() + " will deal " + AttackFactory.runeStrom.getDmg() + " damage. And also deals " + AttackFactory.runeStrom.getNegetableDmg() + " to you.");
    public static final Tooltip humanChargeTip = new Tooltip(AttackFactory.prayer.getName() + " will negate " + AttackFactory.prayer.getNegetableDmg() + " damage. And also accumulates " + AttackFactory.prayer.getBonusDmg() + " bonus damage.");
    public static final Tooltip mageChargeTip = new Tooltip(AttackFactory.incantations.getName() + " will negate " + AttackFactory.incantations.getNegetableDmg() + " damage. And also accumulates " + AttackFactory.incantations.getBonusDmg() + " bonus damage.");

    public static final HashMap<Race, Tooltip> chargeTips = new HashMap<Race, Tooltip>(){{
        put(RaceFactory.elf, elfChargeTip);
        put(RaceFactory.drow, drowChargeTip);
        put(RaceFactory.barbarian, barbarianChargeTip);
        put(RaceFactory.dwarf, dwarfChargeTip);
        put(RaceFactory.human, humanChargeTip);
        put(RaceFactory.mage, mageChargeTip);
    }};
}
