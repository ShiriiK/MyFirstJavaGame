package logic.factories;

import logic.blueprints.Location;

/**
 * Factory for creating locations
 */
public class LocationFactory {

    public static final Location hidden_field = new Location("hidden_field", "Hidden field",
            "A hidden field you came across with friends while exploring the forest a few months ago, " +
                    "it's protected by a strong magical barrier, so no monsters from the forest can reach it. " +
                    "In the middle of this field stands a large, well-equipped house with a small forge nearby.", 3);
    public static final Location home = new Location("home", "Home",
            "The main lounge in the house.", 3);
    public static final Location dining_room = new Location("dining_room", "Dining room",
            "Spacious dining room.", 3);
    public static final Location room = new Location("room", "Room",
            "Your room.", 3);
    public static final Location forge = new Location("forge", "Forge",
            "Gorm's favorite place. If you need her, you can find her here.", 3);
    public static final Location armory = new Location("armory", "Armory",
            "You can choose your weapon here.", 3);
    public static final Location forest = new Location("forest", "Forest",
            "A dark, dangerous forest, you've lost Thorfinn, one of your partners, in it. " +
                    "Unfortunately, his rescue must wait until you get Tue out of dungeon. ", 4);
    public static final Location mountain = new Location("mountain", "Mountain",
            "The area below the mountains you crossed a few months ago. It was a difficult journey that still has " +
                    "to be continued. Unfortunately, the stop that the nearby town was supposed to represent has been extended indefinitely so far.", 4);
    public static final Location lake = new Location("lake", "Lake",
            "The lake to the west of the camp. It turned out to be a good source of food.", 4);
    public static final Location alley = new Location("alley", "Alley",
            "The alley leading to the city gate, which has become a popular viewing path for the giant troll.", 4);
    public static final Location gate = new Location("gate", "Gate",
            "A heavily guarded gate and the only road that leads into the city. To enter, you must show a pass.", 4);
    public static final Location city = new Location("city", "City",
            "A city plagued by poverty and hunger. The difference in the living standards of the people here is astronomical. " +
                    "Now the General himself likes to control the people.", 4);
    public static final Location ghetto = new Location("ghetto", "Ghetto",
            "The poorest part of town and the biggest. There's not much reason to stay here any longer than necessary.", 4);
    public static final Location street = new Location("street", "street",
            "A street leading to the main courtyard where someone like you can't get in.", 4);
    public static final Location coutyard = new Location("coutyard", "Coutyard",
            "", 5);
    public static final Location entrence = new Location("entrence", "Entrence",
            "The entrance to the dungeon where Tue is being held.", 4);
    public static final Location dungeon = new Location("dungeon", "Dungeon",
            "Underground dungeon. It's very dark in here, but the torch helps you see at least a few steps ahead.", 4);
    public static final Location cell1 = new Location("left_cell", "Left cell",
            "A small cell with only rats in it.", 4);
    public static final Location cell2 = new Location("middle_cell", "Middle cell",
            "Quite a large cell with many dark corners.", 4);
    public static final Location cell3 = new Location("richt_cell", "Richt cell",
            "A small, disgusting cell with Tue's almost lifeless body lying on the floor.", 4);
}
