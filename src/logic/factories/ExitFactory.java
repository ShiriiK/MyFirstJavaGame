package logic.factories;

import logic.blueprints.Exit;

/**
 * Factory for creating exits to locations
 */
public class ExitFactory {

    public static final Exit hiddne_fieldExit = new Exit(LocationFactory.hidden_field);
    public static final Exit dining_roomExit = new Exit(LocationFactory.dining_room);
    public static final Exit roomExit = new Exit(LocationFactory.room);
    public static final Exit alleyExit = new Exit(LocationFactory.alley);
    public static final Exit forestExit = new Exit(LocationFactory.forest);
    public static final Exit lakeExit = new Exit(LocationFactory.lake);
    public static final Exit mountainExit = new Exit(LocationFactory.mountain);
    public static final Exit forgeExit = new Exit(LocationFactory.forge);
    public static final Exit homeExit = new Exit(LocationFactory.home);
    public static final Exit armoryExit = new Exit(LocationFactory.armory);
    public static final Exit dungeonExit = new Exit(LocationFactory.dungeon);
    public static final Exit ghettoExit = new Exit(LocationFactory.ghetto);
    public static final Exit passageExit = new Exit(LocationFactory.street);
    public static final Exit entrenceExit = new Exit(LocationFactory.entrence);
    public static final Exit gateExit = new Exit(LocationFactory.gate);
    public static final Exit cityExit = new Exit(LocationFactory.city);
    public static final Exit coutyardExit = new Exit(LocationFactory.coutyard);
    public static final Exit cell1Exit = new Exit(LocationFactory.cell1);
    public static final Exit cell2Exit = new Exit(LocationFactory.cell2);
    public static final Exit cell3Exit = new Exit(LocationFactory.cell3);
}
