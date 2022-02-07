package gui.panels;

import gui.util.Constants;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import gui.util.Observer;
import logic.blueprints.Item;
import saving_tue.Main;

import java.util.Set;

/**
 * Class implementing the Observer interface.
 * ItemPanel sets up displayed items in the top boarderPane when the normal screen is displayed.
 * @author Alena Kalivodová
 */

public class ItemPanel implements Observer {
    private final HBox itemsPanel = new HBox();
    private final FlowPane flowPane = new FlowPane();

    public ItemPanel() {
        Main.game.getGameState().registerObserver(this);
        Main.game.getGameState().getInventory().registerObserver(this);
        Main.game.getGameState().getCurrentLocation().registerObserver(this);

        init();
    }

    /**
     * Method for setting up itemsPanel.
     */
    private void init() {
        itemsPanel.getChildren().clear();
        itemsPanel.getStyleClass().add("top_panels");
        itemsPanel.getChildren().add(flowPane);

        loadItems();
    }

    /**
     * Method for setting images of item in a location.
     */
    private void loadItems() {
        flowPane.getChildren().clear();

        for (Item item : Main.game.getGameState().getCurrentLocation().getItems()) {

            // Finding image
            ImageView imageView = new ImageView(new Image("/items/" + item.getName() + ".jpg",
                    Constants.TOP_PICS_WIDTH, Constants.TOP_PICS_HEIGHT, false, false, true));

            // Installing tip
            Tooltip tip = new Tooltip(item.getDisplayName());
            Tooltip.install(imageView, tip);

            // Putting action on image of item
            clickOnItem(item.getName(), imageView);

            flowPane.getChildren().add(imageView);
        }
    }

    /**
     * A method for processing an action when player clicks on a picture of an item in a location.
     */
    private void clickOnItem(String itemName, ImageView imageView) {
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            String command = " ";
            if (event.getButton() == MouseButton.SECONDARY) {
                command = "explore ";
            } else {
                command = "pickup ";
            }
            Main.console.appendText("\n" + command + itemName + "\n");
            String gameAnswer = Main.game.processAction(command + itemName);
            Main.console.appendText("\n" + gameAnswer + "\n");
        });
    }

    /**
     * Updates item images in the location
     */
    @Override
    public void update() {
        loadItems();
    }

    public Node getPanel() {
        return itemsPanel;
    }
}
