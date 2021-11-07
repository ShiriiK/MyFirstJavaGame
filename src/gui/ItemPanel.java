package gui;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import logic.*;
import util.Observer;
import java.util.Set;

/**
 * Třída implementující rozhraní Observer.
 * ItemPanel nastavuje zobrazení itemsPanel v top boarderPane při zobrazení normální obrazovky.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-06
 */

public class ItemPanel implements Observer {

    private final Game game;
    private final TextArea console;
    private final HBox itemsPanel = new HBox();
    private final FlowPane flowPane = new FlowPane();

    //Konstruktor
    public ItemPanel(Game game, TextArea console) {
        this.game = game;
        this.console = console;

        init();

        game.getGameState().registerObserver(this);
        game.getGameState().getInventory().registerObserver(this);
        game.getGameState().getCurrentLocation().registerObserver(this);

    }

    /**
     * Metoda pro nastavení itemsPanel.
     */
    private void init() {
        itemsPanel.getChildren().clear();
        itemsPanel.setPrefWidth(450.0);
        itemsPanel.setPrefHeight(570.0);
        itemsPanel.getChildren().add(flowPane);

        loadItems();
    }

    /**
     * Metoda pro nastavení obrázků itemů v lokaci.
     */
    private void loadItems() {
        flowPane.getChildren().clear();
        Set<Item> itemsSet = game.getGameState().getCurrentLocation().getItems();

        for (Item item : itemsSet) {
            String itemName = item.getName();
            ImageView imageView = new ImageView(new Image((GameState.class.getResourceAsStream("/zdroje/" + itemName + ".jpg")),
                    200.0, 100.0, false, false));

            clickOnItem(itemName, imageView);

            setTooltip(item, imageView);

            flowPane.getChildren().add(imageView);
        }
    }

    private void setTooltip(Item item, ImageView imageView) {
        Tooltip tip = new Tooltip(item.getDisplayName());
        tip.setFont(Font.font("Garamond", 30));
        Tooltip.install(imageView, tip);
    }

    /**
     * Metoda pro zpracovaní akce, kdy hráč klikne na obrázek itemu v lokaci.
     * @param itemName jméno itemu
     * @param imageView obrázek itemu
     */
    private void clickOnItem(String itemName, ImageView imageView) {
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            String command = " ";
            if (event.getButton() == MouseButton.SECONDARY) {
                command = "prozkoumej ";
            } else {
                command = "seber ";
            }
            console.appendText("\n" + command + itemName + "\n");
            String gameAnswer = game.processAction(command + itemName);
            console.appendText("\n" + gameAnswer + "\n");
        });
    }

    public Node getPanel() {
        return itemsPanel;
    }

    @Override
    public void update() {
        loadItems();
    }
}
