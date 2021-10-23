package gui;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import logic.Game;
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

    private Game game;
    private HBox hbox = new HBox();
    private FlowPane itemPanel = new FlowPane();
    private TextArea console;

    //Konstruktor
    public ItemPanel(Game game, TextArea console) {
        this.game = game;
        this.console = console;
        GameState gameState = game.getGameState();
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
        Set<Item> itemsSet = game.getGameState().getCurrentLocation().getItems();

        for (Item item : itemsSet) {
            String name = item.getName();
            ImageView imageView = new ImageView(new Image((GameState.class.getResourceAsStream("/zdroje/" + name + ".png")),
                    110.0, 100.0, false, false));

            clickOnItem(name, imageView);

            itemPanel.getChildren().add(imageView);
        }
    }

    /**
     * Metoda pro zpracovaní akce, kdy hráč klikne na obrázek itemu v lokaci
     * - kliknutí pravým tlačítkem: prozkoumání předmětu
     * - kliknutí levým tlačítkem: pokus o sebrání předmětu
     *
     * @param name jméno itemu
     * @param imageView obrázek itemu
     */
    private void clickOnItem(String name, ImageView imageView) {
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            String command = " ";
            if (event.getButton() == MouseButton.SECONDARY) {
                command = "prozkoumat ";
            } else {
                command = "sebrat ";
            }
            console.appendText("\n" + command + name + "\n");
            String gameAnswer = game.processAction(command + name);
            console.appendText("\n" + gameAnswer + "\n");
        });
    }

    @Override
    public void update() {
        loadItems();
    }

    public Node getPanel() {
        return hbox;
    }
}
