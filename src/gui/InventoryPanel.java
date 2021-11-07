package gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.Game;
import logic.Inventory;
import util.Observer;
import java.io.InputStream;
import java.util.Set;

/**
 * Třída implementující rozhraní Observer.
 * InventoryPanel nastavuje zobrazení inventoryPanel v left borderPane.
 * <p>
 * Tato třída je součástí jednoduché textové adventury  s grafickým rozhraním.
 *
 * @author Marcel Valový
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-06
 */

public class InventoryPanel implements Observer {

    private final Game game;
    private final TextArea console;
    private final VBox inventoryPanel = new VBox();
    private final FlowPane flowPane = new FlowPane();

    //Konstruktor
    public InventoryPanel(Game game, TextArea console) {
        this.game = game;
        this.console = console;
        Inventory inventory = game.getGameState().getInventory();

        init();

        inventory.registerObserver(this);
    }

    /**
     * Metoda pro nastavení inventoryPanel.
     */
    private void init() {
        Label label = new Label("Batoh:");
        label.setFont(Font.font("Garamond", FontWeight.BOLD, 25));
        label.setTextFill(Color.WHITE);

        inventoryPanel.getChildren().addAll(label, flowPane);
        inventoryPanel.setPrefWidth(500.0);
        inventoryPanel.setPrefHeight(400.0);

        loadImages();
    }

    /**
     * Metoda pro nastavení obrázků v inventáři.
     */
    private void loadImages() {
        if (game.getGameState().getPhase() >= 2) {
            flowPane.getChildren().clear();
            Set<String> itemsSet = game.getGameState().getInventory().itemsInInventory();

            for (String itemName : itemsSet) {
                String pictureName = "/zdroje/" + itemName + ".jpg";
                InputStream inputStream = InventoryPanel.class.getResourceAsStream(pictureName);
                Image image = new Image(inputStream, 250.0, 100.0, false, false);
                ImageView imageView = new ImageView(image);

                cilickOnItemInInventory(itemName, imageView);

                setTooltip(itemName, imageView);

                flowPane.getChildren().add(imageView);
            }
        }
    }

    private void setTooltip(String itemName, ImageView imageView) {
        Tooltip tip = new Tooltip(game.getGameState().getInventory().getItem(itemName).getDisplayName());
        tip.setFont(Font.font("Garamond", 30));
        Tooltip.install(imageView, tip);
    }

    /**
     * Metoda pro zpracování akce, kdy hráč klikne na obrázek itemu v inventáři.
     * @param itemName název itemu
     * @param imageView obrázek itemu
     */
    private void cilickOnItemInInventory(String itemName, ImageView imageView) {
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            String command = " ";
            if (event.getButton() == MouseButton.SECONDARY) {
                command = "prozkoumej ";
            } else {
                command = "zahoď ";
            }
            console.appendText("\n" + command + itemName + "\n");
            String gameAnswer = game.processAction(command + itemName);
            console.appendText("\n" + gameAnswer + "\n");
        });
    }

    public Node getPanel() {
        return inventoryPanel;
    }

    @Override
    public void update() {
        loadImages();
    }
}
