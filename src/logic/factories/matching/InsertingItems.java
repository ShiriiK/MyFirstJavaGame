package logic.factories.matching;

import logic.factories.ItemsFactory;
import logic.factories.LocationFactory;
import logic.factories.NpcsFactory;


public class InsertingItems {

    /**
     * Method for inserting items into locations, another items and also npcs
     */
    public static void insertItems(){
        LocationFactory.hidden_field.addItem(ItemsFactory.rock);
        LocationFactory.hidden_field.addItem(ItemsFactory.bush);
        LocationFactory.hidden_field.addItem(ItemsFactory.dummy);
        LocationFactory.hidden_field.addItem(ItemsFactory.bucket);
        LocationFactory.forge.addItem(ItemsFactory.hammer);
        LocationFactory.forge.addItem(ItemsFactory.oldAnvil);
        LocationFactory.forge.addItem(ItemsFactory.tools);
        LocationFactory.forge.addItem(ItemsFactory.furnace);
        LocationFactory.home.addItem(ItemsFactory.fireplace);
        LocationFactory.home.addItem(ItemsFactory.equipment);
        LocationFactory.home.addItem(ItemsFactory.carpet);
        LocationFactory.dining_room.addItem(ItemsFactory.jug);
        LocationFactory.dining_room.addItem(ItemsFactory.pot);
        LocationFactory.dining_room.addItem(ItemsFactory.leftovers);
        LocationFactory.room.addItem(ItemsFactory.beds);
        LocationFactory.room.addItem(ItemsFactory.book);
        LocationFactory.mountain.addItem(ItemsFactory.hugeTree);
        LocationFactory.lake.addItem(ItemsFactory.bigRock);
        LocationFactory.ghetto.addItem(ItemsFactory.deadBody);
        LocationFactory.ghetto.addItem(ItemsFactory.garbage);
        LocationFactory.entrance.addItem(ItemsFactory.torch);

        ItemsFactory.book.insertItem(ItemsFactory.permit);
        ItemsFactory.beds.insertItem(ItemsFactory.pillow);
        ItemsFactory.leftovers.insertItem(ItemsFactory.bread);
        ItemsFactory.hugeTree.insertItem(ItemsFactory.bag);
        ItemsFactory.hugeTree.insertItem(ItemsFactory.chest);
        ItemsFactory.chest.insertItem(ItemsFactory.bag2);
        ItemsFactory.bigRock.insertItem(ItemsFactory.shinyRock);
        ItemsFactory.deadBody.insertItem(ItemsFactory.key);
        ItemsFactory.garbage.insertItem(ItemsFactory.coin);

        NpcsFactory.girl.insertItem(ItemsFactory.stick);
        NpcsFactory.passageGuard.insertItem(ItemsFactory.masterKey);
    }
}
