package gui.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import saving_tue.Main;

public class PlayerImageSetting {

    public static ImageView setPlayersView(int i){
        ImageView playerView = new ImageView(new Image("/player/" + Main.game.getGameState().getPlayer().getRace().getName().toLowerCase()
                + "_" + Main.game.getGameState().getPlayer().getPlayerGender() + ".jpg"));
        if(i == 1){
            playerView.setFitWidth(Constants.INT_PICS_WIDTH);
            playerView.setFitHeight(Constants.INT_PICS_HEIGHT);
            return playerView;
        } else if (i == 2){
            playerView.setFitWidth(Constants.COMBAT_PICS_WIDTH);
            playerView.setFitHeight(Constants.COMBAT_PICS_HEIGHT);
            return playerView;
        } else if (i == 3){
            playerView.setFitWidth(Constants.STATS_PICS_WIDTH);
            playerView.setFitHeight(Constants.STATS_PICS_HEIGHT);
            return playerView;
        }
        return null;
    }
}
