package gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import logic.Inventory;
import util.Observer;
import java.io.InputStream;
import java.util.Set;

public class InventoryPanel implements Observer {

    private final VBox vbox = new VBox();
    private final FlowPane itemsPanel = new FlowPane();
    private Inventory inventory;

    public InventoryPanel(Inventory inventory) {
        this.inventory = inventory;
        init();
        inventory.registerObserver(this);
    }

    private void init() {
        vbox.setPrefWidth(220);
        Label label = new Label("Bahot:");
        vbox.getChildren().addAll(label, itemsPanel);

        loadImages();
    }

    private void loadImages() {
        itemsPanel.getChildren().clear();
        Set<String> itemsSet = inventory.itemsInInventory();

        for (String item : itemsSet) {
            String pictureName = "/zdroje/" + item + ".png";
            InputStream inputStream = InventoryPanel.class.getResourceAsStream(pictureName);
            Image image = new Image(inputStream, 110, 100,false, false);
            ImageView imageView = new ImageView(image);
            itemsPanel.getChildren().add(imageView);
        }
    }

    @Override
    public void update() {
        loadImages();
    }

    public Node getPanel() {
        return vbox;
    }

    public void gameRestart(Inventory inventory) {
        this.inventory = inventory;
        this.inventory.registerObserver(this);
        update();
    }
}
