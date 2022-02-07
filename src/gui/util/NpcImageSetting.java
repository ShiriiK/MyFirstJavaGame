package gui.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NpcImageSetting {

    public static ImageView setNpcView(String npcName, int i){
        ImageView partnerView = new ImageView(new Image("/npcs/" + npcName.toLowerCase() + ".jpg"));
        if(i == 1){
            partnerView.setFitWidth(Constants.INT_PICS_WIDTH);
            partnerView.setFitHeight(Constants.INT_PICS_HEIGHT);
            return partnerView;
        } else if (i == 2){
            partnerView.setFitWidth(Constants.COMBAT_PICS_WIDTH);
            partnerView.setFitHeight(Constants.COMBAT_PICS_HEIGHT);
            return partnerView;
        } else if (i == 3){
            partnerView.setFitWidth(Constants.STATS_PICS_WIDTH);
            partnerView.setFitHeight(Constants.STATS_PICS_HEIGHT);
            return partnerView;
        }
        return null;
    }
}
