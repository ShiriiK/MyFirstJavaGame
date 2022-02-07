package gui.panels;

import gui.util.NpcImageSetting;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.blueprints.Partner;
import saving_tue.Main;

/**
 * Stage for displaying partners stats
 */

public class PartnerStatsPanel {

    public static Button getPartnerButton() {
        Partner partner = Main.game.getGameState().getPartner();
        Button partnerButton = new Button(partner.getPartnerName());
        partnerButton.getStyleClass().add("bbutton");

        // New stage with stats and partner's picture (+ button to close it)
        partnerButton.setOnAction(e->{
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Partner");

            Label label = new Label(partner.getPartner());

            Button close = new Button("Close");
            close.setOnAction(event->{
                stage.close();
            });

            VBox stats = new VBox();
            stats.getChildren().addAll(label,close);

            ImageView partnerImageView = NpcImageSetting.setNpcView(partner.getPartnerName(), 3);

            BorderPane partnerPane = new BorderPane();
            partnerPane.setLeft(partnerImageView);
            partnerPane.setCenter(stats);

            Scene scene = new Scene(partnerPane);
            scene.getStylesheets().add("style.css");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();

        });
        return partnerButton;
    }
}
