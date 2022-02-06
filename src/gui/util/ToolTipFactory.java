package gui.util;

import javafx.scene.control.Tooltip;
import logic.blueprints.Race;
import logic.factories.RaceFactory;
import java.util.HashMap;

/**
 * Factory for tooltips to make the code cleaner
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

    public static final  Tooltip femaleTip = new Tooltip("When you choose to be a woman:\nYou'll have 80 hp and 30 str.");
    public static final  Tooltip maleTip = new Tooltip("When you choose to be a man so:\nYou'll have 100 life and 20 power.");
}
