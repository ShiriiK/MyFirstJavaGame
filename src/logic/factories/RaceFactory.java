package logic.factories;

import logic.blueprints.Race;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Factory for creating races
 */
public class RaceFactory {

    public static final Race elf = new Race("Elf", 0, 5);
    public static final Race drow = new Race("Drow", 0, 5);
    public static final Race barbarian = new Race("Barbarian", 5, 0);
    public static final Race dwarf = new Race("Dwarf", 5, 0);
    public static final Race human = new Race("Human", 3, 2);
    public static final Race mage = new Race("Mage", 2, 3);

    public static final Set<Race> races = Stream.of(elf, drow, barbarian, dwarf, human, mage).collect(Collectors.toSet());

    public static Race getRace(String raceName){
        for (Race race : races){
            if (race.getName().equals(raceName)){
                return race;
            }
        }
        return null;
    }
}
