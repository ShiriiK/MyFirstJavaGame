package gui.panels;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.blueprints.Player;
import saving_tue.Main;

public class PlayerPanel {

    public static final Button getPlayerButton() {
        Player player = Main.game.getGameState().getPlayer();
        Button playerButton = new Button("Player");
        playerButton.getStyleClass().add("bbutton");

        // New stage with stats and player picture (+ button to close it)
        playerButton.setOnAction(e->{
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Player");

            Label info = new Label(player.getPlayersStats());

            Button closeButton = new Button("Close");
            closeButton.setOnAction(event->{
                stage.close();
            });

            VBox stats = new VBox();
            stats.getStyleClass().add("info");
            stats.getChildren().addAll(info, closeButton);

            ImageView playerImageView = new ImageView(new Image("/player/" + player.getRace().getName() + "_" + player.getPlayerGender() + ".jpg",
                    900.0, 470.0, false, false));

            BorderPane playerPane = new BorderPane();
            playerPane.setLeft(playerImageView);
            playerPane.setCenter(stats);

            Scene scene = new Scene(playerPane);
            scene.getStylesheets().add("style.css");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();

        });
        return playerButton;
    }
}
