package gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.Game;
import logic.GameState;
import logic.Location;
import util.Observer;

import java.nio.channels.FileLock;
import java.util.Set;

/**
 * Třída implementující rozhraní Observer.
 * ExitPanel nastavuje zobrazení exitsPanel v right borderPane.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Marcel Valový
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-06
 */

public class ExitPanel implements Observer {

    private final Game game;
    private final TextArea console;
    private final VBox exitsPanel = new VBox();
    private final FlowPane flowPane = new FlowPane();

    //kostruktor
    public ExitPanel(Game game, TextArea console) {
        this.game = game;
        this.console = console;
        GameState gameState = game.getGameState();

        init();

        gameState.registerObserver(this);
    }

    /**
     * Metoda pro nastavení exitsPanel.
     */
    private void init() {
        Label label = new Label("Sousední lokace: ");
        label.setFont(Font.font("Garamond", FontWeight.BOLD, 25));
        label.setTextFill(Color.WHITE);

        exitsPanel.getChildren().addAll(label, flowPane);
        exitsPanel.setPrefWidth(500.0);
        exitsPanel.setPrefHeight(400.0);
        exitsPanel.setAlignment(Pos.TOP_CENTER);

        loadCurrentExits();
    }

    /**
     * Metoda pro nastavení obrázků sousedních lokací.
     */
    private void loadCurrentExits() {
        if (game.getGameState().getPhase() >= 2) {
            flowPane.getChildren().clear();
            Set<Location> locationsSet = game.getGameState().getCurrentLocation().getTargetLocations();

            for (Location location : locationsSet) {
                String locationName = location.getName();
                ImageView imageView = new ImageView(new Image((GameState.class.getResourceAsStream("/zdroje/" + locationName + ".jpg")),
                        250, 100, false, false));

                clickOnExit(locationName, imageView);

                setTooltip(location, imageView);

                flowPane.getChildren().add(imageView);
            }
        }
    }

    private void setTooltip(Location location, ImageView imageView) {
        Tooltip tip = new Tooltip(location.getDisplayName());
        tip.setFont(Font.font("Garamond", 30));
        Tooltip.install(imageView, tip);
    }

    /**
     * Metoda pro zpracování akce, kdy hráč klikne na obrázek sousední lokace.
     * @param locationName jméno lokace
     * @param imageView obrázek lokace
     */
    private void clickOnExit(String locationName, ImageView imageView) {
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            String command = "jdi ";
            console.appendText("\n" + command + locationName + "\n");
            String gameAnswer = game.processAction(command + locationName);
            console.appendText("\n" + gameAnswer + "\n");
        });
    }

    public Node getPanel() {return exitsPanel; }

    @Override
    public void update() {
        loadCurrentExits();
    }
}

