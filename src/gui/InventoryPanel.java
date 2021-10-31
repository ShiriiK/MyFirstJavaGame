package gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
    private Game game;
    private TextArea console;

    //Konstruktor
    public InventoryPanel(Game game, TextArea console) {
        this.game = game;
        this.console = console;
        Inventory inventory = game.getGameState().getInventory();
        init();
        inventory.registerObserver(this);
    }

    private void init() {
        vbox.setPrefWidth(500.0);
        vbox.setPrefHeight(400.0);
        Label label = new Label("Bahot:");
        label.setFont(Font.font("Garamond", FontWeight.BOLD, 25));
        label.setTextFill(Color.WHITE);
        vbox.getChildren().addAll(label, inventoryPanel);

        loadImages();
    }

    /**
     * Metoda pro nastavení inventoryPanel.
     */
    private void loadImages() {
        if (game.getGameState().getPhase() >= 2) {
            inventoryPanel.getChildren().clear();
            Set<String> itemsSet = game.getGameState().getInventory().itemsInInventory();

            for (String item : itemsSet) {
                String pictureName = "/zdroje/" + item + ".jpg";
                InputStream inputStream = InventoryPanel.class.getResourceAsStream(pictureName);
                Image image = new Image(inputStream, 250.0, 150.0, false, false);
                ImageView imageView = new ImageView(image);

                cilickOnItemInInventory(item, imageView);

                inventoryPanel.getChildren().add(imageView);
            }
        }
    }

    private void cilickOnItemInInventory(String item, ImageView imageView) {
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            String command = " ";
            if (event.getButton() == MouseButton.SECONDARY) {
                command = "prozkoumej ";
            } else {
                command = "zahoď ";
            }
            console.appendText("\n" + command + item + "\n");
            String gameAnswer = game.processAction(command + item);
            console.appendText("\n" + gameAnswer + "\n");
        });
    }

    @Override
    public void update() {
        loadImages();
    }

    public Node getPanel() {
        return vbox;
    }
}
