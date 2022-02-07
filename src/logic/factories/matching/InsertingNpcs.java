package logic.factories.matching;

import logic.factories.ExitFactory;
import logic.factories.LocationFactory;
import logic.factories.NpcsFactory;

public class InsertingNpcs {

    /**
     * Method for inserting npcs into locations and exits
      */
    public static void insertNpcs(){
        LocationFactory.alley.addNpc(NpcsFactory.trollKing);
        LocationFactory.forge.addNpc(NpcsFactory.gorm);
        LocationFactory.left_cell.addNpc(NpcsFactory.rat);
        LocationFactory.middle_cell.addNpc(NpcsFactory.brutalGuard);
        LocationFactory.right_cell.addNpc(NpcsFactory.tue);
        LocationFactory.city.addNpc(NpcsFactory.general);
        LocationFactory.city.addNpc(NpcsFactory.beggar);
        LocationFactory.gate.addNpc(NpcsFactory.gateGuard);
        LocationFactory.forest.addNpc(NpcsFactory.troll);
        LocationFactory.lake.addNpc(NpcsFactory.frog);
        LocationFactory.mountain.addNpc(NpcsFactory.wolf);
        LocationFactory.mountain.addNpc(NpcsFactory.bear);
        LocationFactory.street.addNpc(NpcsFactory.passageGuard);
        LocationFactory.ghetto.addNpc(NpcsFactory.girl);
        LocationFactory.entrance.addNpc(NpcsFactory.dungeonGuard);

        ExitFactory.gateExit.insertNpc(NpcsFactory.trollKing);
        ExitFactory.cityExit.insertNpc(NpcsFactory.gateGuard);
        ExitFactory.ghettoExit.insertNpc(NpcsFactory.general);
        ExitFactory.entrenceExit.insertNpc(NpcsFactory.general);
        ExitFactory.passageExit.insertNpc(NpcsFactory.general);
        ExitFactory.gateExit.insertNpc(NpcsFactory.general);
        ExitFactory.dungeonExit.insertNpc(NpcsFactory.dungeonGuard);
    }
}
