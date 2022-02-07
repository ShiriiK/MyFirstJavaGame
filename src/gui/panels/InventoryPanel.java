package gui.panels;

import gui.util.Constants;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import gui.util.Observer;
import saving_tue.Main;

/**
 * Class implementing the Observer interface.
 * InventoryPanel sets up inventory that will be displayed in left borderPane.
 * @author Marcel Valový
 * @author Alena Kalivodová
 */

public class InventoryPanel implements Observer {

    private final VBox inventoryPanel = new VBox();
    private final FlowPane itemsInInventory = new FlowPane();

    public InventoryPanel() {
        Main.game.getGameState().getInventory().registerObserver(this);

        init();
    }

    /**
     * Method for setting up inventoryPanel.
     */
    private void init() {
        Label label = new Label("Inventory:");

        inventoryPanel.getChildren().addAll(label, itemsInInventory);
        inventoryPanel.getStyleClass().add("bottom_panels");

        loadImages();
    }

    /**
     * Method for setting images of items in the inventory.
     */
    private void loadImages() {
        if (Main.game.getGameState().getPhase() >= 2) {
            itemsInInventory.getChildren().clear();

            for (String itemName : Main.game.getGameState().getInventory().itemsInInventory()) {

                // Finding image
                ImageView imageView = new ImageView(new Image("/items/" + itemName + ".jpg", Constants.BOTTOM_PICS_WIDTH,
                        Constants.BOTTOM_PICS_HEIGHT, false, false, true));

                // Installing tip
                Tooltip tip = new Tooltip(Main.game.getGameState().getInventory().getItem(itemName).getDisplayName());
                Tooltip.install(imageView, tip);

                // Putting action on image of item
                cilickOnItemInInventory(itemName, imageView);

                itemsInInventory.getChildren().add(imageView);
            }
        }
    }

    /**
     * A method for processing an action when player clicks on the image of an item in the inventory.
     */
    private void cilickOnItemInInventory(String itemName, ImageView itemImageView) {
        itemImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            String command = " ";
            if (event.getButton() == MouseButton.SECONDARY) {
                command = "explore ";
            } else {
                command = "drop ";
            }
            Main.console.appendText("\n" + command + itemName + "\n");
            String gameAnswer = Main.game.processAction(command + itemName);
            Main.console.appendText("\n" + gameAnswer + "\n");
        });
    }

    /**
     * Updates item images in inventory
     */
    @Override
    public void update() {
        loadImages();
    }

    public Node getPanel() {
        return inventoryPanel;
    }
}
