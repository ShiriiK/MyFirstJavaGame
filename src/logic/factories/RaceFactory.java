package logic.factories;

import logic.blueprints.Race;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Factory for creating races
 */
public class RaceFactory {

    public static final Race elf = new Race("Elf", "volání_entů", "elfí_běsnění");
    public static final Race drow = new Race("Drow", "pomatení", "volání_krve");
    public static final Race barbarian = new Race("Barbarian", "zuřivý_skok", "bojový_tanec");
    public static final Race dwarf = new Race("Dwarf", "přivolání_blesků", "runová_bouře");
    public static final Race human = new Race("Human", "meč_spravedlnosti", "modlitba");
    public static final Race mage = new Race("Mage", "ohnivá_koule", "zaklínání");
    public static final Set<Race> races = Stream.of(elf, drow, barbarian, dwarf, human, mage).collect(Collectors.toSet());
}
