package logic.factories.matching;

import logic.factories.ExitFactory;
import logic.factories.LocationFactory;

public class MatchingExitsAndLocations {

    /**
     * Method for matching exits to locations
     */
    public static void matchExitsAndLocations(){
        LocationFactory.alley.addExit(ExitFactory.hiddne_fieldExit);
        LocationFactory.alley.addExit(ExitFactory.gateExit);
        LocationFactory.hidden_field.addExit(ExitFactory.alleyExit);
        LocationFactory.hidden_field.addExit(ExitFactory.forestExit);
        LocationFactory.hidden_field.addExit(ExitFactory.lakeExit);
        LocationFactory.hidden_field.addExit(ExitFactory.mountainExit);
        LocationFactory.hidden_field.addExit(ExitFactory.homeExit);
        LocationFactory.hidden_field.addExit(ExitFactory.forgeExit);
        LocationFactory.forge.addExit(ExitFactory.hiddne_fieldExit);
        LocationFactory.forge.addExit(ExitFactory.armoryExit);
        LocationFactory.armory.addExit(ExitFactory.forgeExit);
        LocationFactory.home.addExit(ExitFactory.hiddne_fieldExit);
        LocationFactory.home.addExit(ExitFactory.dining_roomExit);
        LocationFactory.home.addExit(ExitFactory.roomExit);
        LocationFactory.room.addExit(ExitFactory.homeExit);
        LocationFactory.dining_room.addExit(ExitFactory.homeExit);
        LocationFactory.left_cell.addExit(ExitFactory.dungeonExit);
        LocationFactory.middle_cell.addExit(ExitFactory.dungeonExit);
        LocationFactory.city.addExit(ExitFactory.ghettoExit);
        LocationFactory.city.addExit(ExitFactory.passageExit);
        LocationFactory.city.addExit(ExitFactory.entrenceExit);
        LocationFactory.city.addExit(ExitFactory.gateExit);
        LocationFactory.gate.addExit(ExitFactory.alleyExit);
        LocationFactory.gate.addExit(ExitFactory.cityExit);
        LocationFactory.forest.addExit(ExitFactory.hiddne_fieldExit);
        LocationFactory.ghetto.addExit(ExitFactory.cityExit);
        LocationFactory.lake.addExit(ExitFactory.hiddne_fieldExit);
        LocationFactory.mountain.addExit(ExitFactory.hiddne_fieldExit);
        LocationFactory.street.addExit(ExitFactory.cityExit);
        LocationFactory.street.addExit(ExitFactory.coutyardExit);
        LocationFactory.entrance.addExit(ExitFactory.cityExit);
        LocationFactory.entrance.addExit(ExitFactory.dungeonExit);
        LocationFactory.dungeon.addExit(ExitFactory.cell1Exit);
        LocationFactory.dungeon.addExit(ExitFactory.cell2Exit);
        LocationFactory.dungeon.addExit(ExitFactory.cell3Exit);
    }
}
