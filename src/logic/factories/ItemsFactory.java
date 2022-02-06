package logic.factories;

import logic.blueprints.Item;

/**
 * Factory for creating items
 */
public class ItemsFactory {

    public static final Item bigRock = new Item("big_rock", "Big rock", false,
            "The only thing that stands out here.");
    public static final Item shinyRock = new Item("shinning_rock", "Shinning rock", true,
            "Blue shinning rock. You can try giving it to Grom, maybe she'll use it for something.");
    public static final Item torch = new Item("torch", "Torch", true,
            "Ignited torch.");
    public static final Item hugeTree = new Item("huge_tree", "Huge tree", false,
            "A big, weird tree. You can see the chest under its roots. And even deeper underneath, " +
                    "you can see something else. Try continuing your search..");
    public static final Item bag = new Item("bag", "Bag", true,
            "A bag full of gold.");
    public static final Item deadBody = new Item("corpse", "Corpse", false,
            "Some guy's corpse. Oh, yuck.");
    public static final Item key = new Item("key", "Key", true,
            "A rusty looking key, I wonder what it opens?");
    public static final Item bush = new Item("bush", "Bush", false,
            "The bush next to the tent. Gorm likes to pick blueberries from it.");
    public static final Item hammer = new Item("hammer", "Hammer", false,
            "Gorm's favorite hammer, you better not touch it.");
    public static final Item oldAnvil = new Item("old_anvil", "Old anvil", false,
            "An old anvil that Gorm refuses to replace with a new one.");
    public static final Item tools = new Item("tools", "Nářadí", false,
            "A lot of blacksmithing tools. One of the best in the country, or so Gorm says.");
    public static final Item furnace = new Item("furnace", "Furnace", false,
            "Forge furnace.");
    public static final Item beds = new Item("beds", "Beds", false,
            "A soft warm bed.");
    public static final Item pillow = new Item("pillow", "Pillow", true,
            "Your favorite fluffy pillow, nice and warm at night.");
    public static final Item fireplace = new Item("fireplace", "Fireplace", false,
            "A small fireplace inside the house.");
    public static final Item equipment = new Item("equipment", "Equipment", false,
            "Old rusty weapons and armors.");
    public static final Item pot = new Item("pot", "Pot", false,
            "Cooking pot.");
    public static final Item carpet = new Item("carpet", "Carpet", false,
            "Fuzzy carpet.");
    public static final Item jug = new Item("jug", "Jug", false,
            "A jug full of water.");
    public static final Item leftovers = new Item("leftovers", "Leftovers", false,
            "Leftovers from lunch the day before yesterday.");
    public static final Item dummy = new Item("dummy", "Dummy", false,
            "A dummy for combat practice.");
    public static final Item bucket = new Item("bucket", "Bucket", false,
            "Leaky bucket.");
    public static final Item rock = new Item("rock", "Rock", true,
            "Just a stone.");
    public static final Item permit = new Item("permit", "Permit", true,
            "A pass to the city.");
    public static final Item book = new Item("book", "Book", false,
            "Pile of papers.");
    public static final Item masterKey = new Item("master_key", "Master key", true,
            "A key that opens almost everything.");
    public static final Item chest = new Item("chest", "Chest", false,
            "");
    public static final Item bag2 = new Item("bag", "Bag", true,
            "Bag full of money.");
    public static final Item stick = new Item("stick", "Stick", true,
            "A stick from a little girl.");
    public static final Item bread = new Item("bread", "Bread", true,
            "Mouldy bread.");
    public static final Item garbage = new Item("garbage", "Garbage", false,
            "Nasty smelling garbage.");
    public static final Item coin = new Item("coin", "Coin", true,
            "Pennies that are not worth much.");
    public static final Item beer = new Item("beer", "Beer",true,
            "Just a regular beer.");
}
