package logic.factories;

import saving_tue.Main;

/**
 * Factory for setting partner
 */
public class PartnerFactory {

    public static void setYrsa(){
        Main.game.getGameState().getPartner().setPartnerName("Yrsa");
        Main.game.getGameState().getPartner().setPartnerWeapon(WeaponFactory.dagger);
        Main.game.getGameState().getPartner().setStr(20.0);
        Main.game.getGameState().getPartner().setHp(80.0);
        Main.game.getGameState().getPartner().setStr(30.0);
        Main.game.getGameState().getPartner().setHp(100.0);
    }

    public static void setTorsten(){
        Main.game.getGameState().getPartner().setPartnerName("Torsten");
        Main.game.getGameState().getPartner().setPartnerWeapon(WeaponFactory.axe);
        Main.game.getGameState().getPartner().setStr(30.0);
        Main.game.getGameState().getPartner().setHp(100.0);
        Main.game.getGameState().getPartner().setStr(30.0);
        Main.game.getGameState().getPartner().setHp(80.0);
    }
}
