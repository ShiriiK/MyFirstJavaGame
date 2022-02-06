package logic.factories;

import logic.blueprints.Location;
import logic.blueprints.Weapon;
import saving_tue.Main;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Factory for creating weapons
 */
public class WeaponFactory {

    public static final Weapon axe = new Weapon("axe", "Axe",1.2, false, "dwarf",0,0,0,0);
    public static final Weapon executioner_axe = new Weapon("executioner_axe", "Executioner axe", 1.4,true,"dwarf",10,0,10,0);
    public static final Weapon two_axes = new Weapon("two_axes", "Two axes", 1.4,true, "dwarf",0,20,0,0);
    public static final Weapon rune_axe = new Weapon("rune_axe", "Rune axe", 1.4, true, "dwarf",0,10,10,0);

    public static final Weapon sword = new Weapon("sword", "Sword",1.2, false, "human",0,0,0,0);
    public static final Weapon poisoned_sword = new Weapon("poisoned_sword", "Poisoned sword", 1.4, true, "human", 10,0,5,5);
    public static final Weapon greatsword = new Weapon("greatsword", "Greatsword",1.4, true, "human",10,0,10,0);
    public static final Weapon holysword = new Weapon("holysword","Holysword", 1.4, true, "human",0,10,0,10);

    public static final Weapon dagger = new Weapon("dagger", "Dagger",1.2, false, "drow",0,0,0,0);
    public static final Weapon poisoned_dagger = new Weapon ("poisoned_dagger", "Poisoned dagger", 1.4, true,"drow",10,0,5,5);
    public static final Weapon fire_dagger = new Weapon ("fire_dagger", "Fire dagger", 1.4,true, "drow",0,0,10,10);
    public static final Weapon curved_dagger = new Weapon("curved_dagger", "Curved dagger", 1.4,true, "drow",0,20,5,54);

    public static final Weapon bow = new Weapon("bow","Bow", 1.2, false, "elf",0,0,0,0);
    public static final Weapon longbow = new Weapon("longbow", "Longbow", 1.4, true, "elf",10,10,0,0);
    public static final Weapon mist_bow = new Weapon("mist_bow", "Mist bow", 1.4, true, "elf",0,0,10,10);
    public static final Weapon elven_sword = new Weapon("elven_sword","Elven sword", 1.4, true, "elf",5,5,5,5);

    public static final Weapon club = new Weapon("club", "Club",1.2, false, "barbarian",0,0,0,0);
    public static final Weapon mace = new Weapon("mace", "Mace", 1.4, true, "barbarian",10,10,0,0);
    public static final Weapon halberd = new Weapon("halberd", "Halberd",1.4, true, "barbarian",0,20,0,10);
    public static final Weapon spear = new Weapon("spear", "Spear",1.4, true, "barbarian",0,0,30,0);

    public static final Weapon staff = new Weapon ("staff","Staff", 1.2, false, "mage",0,0,0,0);
    public static final Weapon dragon_staff = new Weapon("dragon_staff","Dragon staff", 1.4, true, "mage",0,0,10,10);
    public static final Weapon crystal_staff = new Weapon("crystal_staff", "Crystal staff", 1.4, true, "mage",10,10,0,0);
    public static final Weapon ancient_staff = new Weapon("ancient_staff", "Ancient staff", 1.4, true, "mage",15,5,0,0);

    public static final Set<Weapon> weapons = new HashSet<>(Arrays.asList(axe, executioner_axe, two_axes, rune_axe, sword, poisoned_sword, greatsword, holysword, dagger, poisoned_dagger, fire_dagger,
            curved_dagger, bow, longbow, mist_bow, elven_sword, club, mace, halberd, spear, staff, dragon_staff, crystal_staff, ancient_staff));

    /**
     * Method for inserting weapons in armory based on players race
     */
    public static void insertWeapons() {
        Location armory = Main.game.getGameState().getCurrentLocation().getExit("forge").getTargetLocation().getExit("armory").getTargetLocation();
        for (Weapon weapon : WeaponFactory.weapons) {
            if (weapon.getRace().equals(Main.game.getGameState().getPlayer().getRace().getName().toLowerCase())){
                armory.addWeapon(weapon);
            }
        }
    }
}
