package gui;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import logic.GameState;
import logic.Item;
import util.Observer;
import java.util.Set;

/**
 * Třída implementující rozhraní Observer.
 * ItemPanel zobrazuje itemy nacházející se v aktuální lokaci.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-23
 */

public class ItemPanel implements Observer {

    private GameState gameState;
    private HBox hbox = new HBox();
    private FlowPane itemPanel = new FlowPane();

    //Konstruktor
    public ItemPanel(GameState gameState) {
        this.gameState = gameState;
        init();
        gameState.registerObserver(this);
    }

    private void init() {
        hbox.setPrefWidth(220.0);
        hbox.getChildren().add(itemPanel);

        loadItems();
    }

    /**
     * Metoda pro nastavení itemPanel.
     */
    private void loadItems() {
        itemPanel.getChildren().clear();
        Set<Item> itemsSet = gameState.getCurrentLocation().getItems();

        for (Item item : itemsSet) {
            String name = item.getName();
            ImageView imageView = new ImageView(new Image((GameState.class.getResourceAsStream("/zdroje/" + name + ".png")),
                    110.0, 100.0, false, false));
            itemPanel.getChildren().add(imageView);
        }
    }

    @Override
    public void update() {
        loadItems();
    }

    public Node getPanel() {
        return hbox;
    }
}
