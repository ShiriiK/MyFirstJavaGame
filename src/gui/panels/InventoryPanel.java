package gui.panels;

import javafx.geometry.Pos;
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
import logic.Game;
import logic.blueprints.Inventory;
import gui.util.Observer;

import java.util.Set;

/**
 * Třída implementující rozhraní Observer.
 * InventoryPanel nastavuje zobrazení inventoryPanel v left borderPane.
 * <p>
 * Tato třída je součástí jednoduché textové adventury  s grafickým rozhraním.
 *
 * @author Marcel Valový
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-010
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

        inventoryPanel.getChildren().addAll(label, flowPane);
        inventoryPanel.setPrefWidth(500.0);
        inventoryPanel.setPrefHeight(400.0);
        inventoryPanel.setAlignment(Pos.TOP_CENTER);

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

                ImageView imageView = new ImageView(new Image("/pics/" + itemName + ".jpg", 250.0,
                        100.0, false, false, true));

                cilickOnItemInInventory(itemName, imageView);

                Tooltip tip = new Tooltip(game.getGameState().getInventory().getItem(itemName).getDisplayName());
                Tooltip.install(imageView, tip);

                flowPane.getChildren().add(imageView);
            }
        }
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

    /**
     * @return inventoryPanel
     */
    public Node getPanel() {
        return inventoryPanel;
    }

    /**
     * Aktualizuje obráky itemů v inventáři
     */
    @Override
    public void update() {
        loadImages();
    }
}
