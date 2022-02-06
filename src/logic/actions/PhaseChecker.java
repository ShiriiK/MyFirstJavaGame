package logic.actions;

import gui.util.Constants;
import saving_tue.Main;

public class PhaseChecker {

    public static String firstChecker(){
        if (Main.game.getGameState().getPhase() == 0) {
            return Constants.d1 + "Choose your gender." + Constants.d2;
        }
        return null;
    }
    public static String secondChecker(){
        if (Main.game.getGameState().getPhase() == 1) {
            return Constants.d1 + "Choose your race." + Constants.d2;
        }
        return null;
    }
    public static String basicChecker(){
        if (Main.game.getGameState().getPhase() == 0) {
            return Constants.d1 + "Choose your gender." + Constants.d2;
        }
        if (Main.game.getGameState().getPhase() == 1) {
            return Constants.d1 + "Choose your race." + Constants.d2;
        }
        if (Main.game.getGameState().getPhase() == 2) {
            return Constants.d1 + "Choose your name." + Constants.d2;
        }
        return null;
    }
    public static String advancedChecker(){
        if (Main.game.getGameState().getPhase() == 3) {
            return Constants.d1 + "Get your weapon." + Constants.d2;
        }
        return null;
    }
}
