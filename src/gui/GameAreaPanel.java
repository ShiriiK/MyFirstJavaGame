package gui;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import logic.GameState;
import logic.Location;
import util.Observer;

import java.io.InputStream;

public class GameAreaPanel implements Observer {
    private GameState gameState;
    private Image currentLocationImage;
    private AnchorPane anchorPane = new AnchorPane();

    public GameAreaPanel(GameState gameState) {
        this.gameState = gameState;

        loadArea();

        gameState.registerObserver(this);
    }

    private void loadArea() {
        String location = gameState.getCurrentLocation().getName();
        ImageView currentLocationImageView = new ImageView(new Image
                (GameState.class.getResourceAsStream("/zdroje/" + location + ".jpg"),
                        950, 450, false, false));

        anchorPane.getChildren().add(currentLocationImageView);
    }


    @Override
    public void update() {
        loadArea();
    }

    public Node getAnchorPane() {
        return anchorPane;
    }
}
