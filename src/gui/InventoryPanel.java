package gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.Inventory;
import util.Observer;
import java.io.InputStream;
import java.util.Set;

/**
 * Třída implementující rozhraní Observer.
 * InventoryPanel zobrazuje obsah inventáře hráče.
 * <p>
 * Tato třída je součástí jednoduché textové adventury  s grafickým rozhraním.
 *
 * @author Marcel Valový
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-21
 */

public class InventoryPanel implements Observer {

    private final VBox vbox = new VBox();
    private final FlowPane inventoryPanel = new FlowPane();
    private Inventory inventory;

    //Konstruktor
    public InventoryPanel(Inventory inventory) {
        this.inventory = inventory;
        init();
        inventory.registerObserver(this);
    }

    private void init() {
        vbox.setPrefWidth(220);
        Label label = new Label("Bahot:");
        label.setFont(Font.font("Garamond", FontWeight.BOLD, 25));
        vbox.getChildren().addAll(label, inventoryPanel);

        loadImages();
    }

    /**
     * Metoda pro nastavení inventoryPanel.
     */
    private void loadImages() {
        inventoryPanel.getChildren().clear();
        Set<String> itemsSet = inventory.itemsInInventory();

        for (String item : itemsSet) {
            String pictureName = "/zdroje/" + item + ".png";
            InputStream inputStream = InventoryPanel.class.getResourceAsStream(pictureName);
            Image image = new Image(inputStream, 110, 100,false, false);
            ImageView imageView = new ImageView(image);
            inventoryPanel.getChildren().add(imageView);
        }
    }

    @Override
    public void update() {
        loadImages();
    }

    public Node getPanel() {
        return vbox;
    }
}
