package gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
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
 * @version ZS-2021, 2021-10-23
 */

public class GameAreaPanel implements Observer {
    private ItemPanel itemsPanel;
    private NpcPanel npcsPanel;
    private GameState gameState;
    private BorderPane borderPane = new BorderPane();

    public GameAreaPanel(GameState gameState, ItemPanel itemsPanel, NpcPanel npcsPanel) {
        this.gameState = gameState;
        this.itemsPanel = itemsPanel;
        this.npcsPanel = npcsPanel;
        loadArea();
        gameState.registerObserver(this);

        borderPane.setStyle(" -fx-background-color: WHITE;");
        borderPane.setStyle(" -fx-border-width: 5; -fx-border-color: BLACK; -fx-padding: 5;");
    }

    /**
     * Metoda pro nastavení anchodPane pro zobrazení obrázku aktuální lokace
     */
    private void loadArea() {
        String location = gameState.getCurrentLocation().getName();
        ImageView currentLocationImageView = new ImageView(new Image
                (GameState.class.getResourceAsStream("/zdroje/" + location + ".jpg"),
                        860, 450, false, false));

        borderPane.setCenter(currentLocationImageView);
        borderPane.setLeft(itemsPanel.getPanel());
        borderPane.setRight(npcsPanel.getPanel());
    }

    @Override
    public void update() {
        loadArea();
    }

    public Node getBorderPane() {
        return borderPane;
    }
}
