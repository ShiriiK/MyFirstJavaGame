package gui;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import logic.GameState;
import util.Observer;

/**
 * Třída implementující rozhraní Observer.
 * GameAreaPanel zobrazuje obrázek aktuální lokace.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Marcel Valový
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-22
 */

public class GameAreaPanel implements Observer {
    private GameState gameState;
    private AnchorPane anchorPane = new AnchorPane();

    public GameAreaPanel(GameState gameState) {
        this.gameState = gameState;
        loadArea();
        gameState.registerObserver(this);
    }

    /**
     * Metoda pro nastavení anchodPane pro zobrazení obrázku aktuální lokace
     */
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
