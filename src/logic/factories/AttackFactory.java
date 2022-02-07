package logic.factories;

import logic.blueprints.ChargeAttack;
import logic.blueprints.SpecialAttack;

import java.util.HashMap;

/**
 * Factory for creating charge and special attacks
 */

public class AttackFactory {

    public static SpecialAttack cryOfEnts = new SpecialAttack("Cry of Ents", 35, 0, RaceFactory.elf);
    public static SpecialAttack delusion = new SpecialAttack("Delusion", 0, 50, RaceFactory.drow);
    public static SpecialAttack furiousJump = new SpecialAttack("Furious jump", 50, 0, RaceFactory.barbarian);
    public static SpecialAttack lightningSummoning = new SpecialAttack("Lightning summoning", 40, 0, RaceFactory.dwarf);
    public static SpecialAttack swordOfJustice = new SpecialAttack("Sword of justice", 40, 0, RaceFactory.human);
    public static SpecialAttack fireball = new SpecialAttack("Fireball", 60, 0, RaceFactory.mage);

    public static final HashMap<String, SpecialAttack> specialAttacks = new HashMap<String, SpecialAttack>(){{
        put(RaceFactory.elf.getName(), cryOfEnts);
        put(RaceFactory.drow.getName(), delusion);
        put(RaceFactory.barbarian.getName(), furiousJump);
        put(RaceFactory.dwarf.getName(), lightningSummoning);
        put(RaceFactory.human.getName(), swordOfJustice);
        put(RaceFactory.mage.getName(), fireball);
    }};


    public static ChargeAttack elvenFury = new ChargeAttack("Elven fury", 0, 50, 0, RaceFactory.elf);
    public static ChargeAttack cryForBlood = new ChargeAttack("Cry for blood", 0, 0, 40, RaceFactory.drow);
    public static ChargeAttack warDance = new ChargeAttack("War dance", 0, -10, 70, RaceFactory.barbarian);
    public static ChargeAttack runeStrom = new ChargeAttack("Rune storm", 80, -10, 0, RaceFactory.dwarf);
    public static ChargeAttack prayer = new ChargeAttack("Prayer", 0, 40, 40, RaceFactory.human);
    public static ChargeAttack incantations = new ChargeAttack("Incantations", 0, 30, 30, RaceFactory.mage);

    public static final HashMap<String, ChargeAttack> chargeAttacks = new HashMap<String, ChargeAttack>(){{
        put(RaceFactory.elf.getName(), elvenFury);
        put(RaceFactory.drow.getName(), cryForBlood);
        put(RaceFactory.barbarian.getName(), warDance);
        put(RaceFactory.dwarf.getName(), runeStrom);
        put(RaceFactory.human.getName(), prayer);
        put(RaceFactory.mage.getName(), incantations);
    }};


}
