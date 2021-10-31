package gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import logic.Game;
import logic.GameState;
import logic.Location;
import util.Observer;
import java.util.Set;

/**
 * Třída implementující rozhraní Observer.
 * ExitPanel zobrazuje obsah sousední lokace.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Marcel Valový
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-23
 */

public class ExitPanel implements Observer {

    private final VBox vbox = new VBox();
    private final FlowPane exitPanel = new FlowPane();
    private final TextArea console;
    private Game game;

    //kostruktor
    public ExitPanel(Game game, TextArea console) {
        this.game = game;
        this.console = console;
        GameState gameState = game.getGameState();
        init();
        gameState.registerObserver(this);
    }

    private void init() {
        vbox.setPrefWidth(500.0);
        vbox.setPrefHeight(400.0);
        Label label = new Label("Sousední lokace: ");
        label.setFont(Font.font("Garamond", FontWeight.BOLD, 25));
        label.setTextFill(Color.WHITE);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(label, exitPanel);

        loadCurrentExits();
    }

    /**
     * Metoda pro nastavení exitsPanel.
     */
    private void loadCurrentExits() {
        if (game.getGameState().getPhase() >= 2) {
            exitPanel.getChildren().clear();
            Set<Location> locationsSet = game.getGameState().getCurrentLocation().getTargetLocations();

            for (Location location : locationsSet) {
                String name = location.getName();
                ImageView imageView = new ImageView(new Image((GameState.class.getResourceAsStream("/zdroje/" + name + ".jpg")),
                        250, 100, false, false));

                clickOnExit(name, imageView);

                exitPanel.getChildren().add(imageView);
            }
        }
    }

    /**
     * Metoda pro zpracování akce, kdy hráč klikne na obrázek sousední lokace a pokusí se do této lokace přemístit
     *
     * @param name jméno lokace
     * @param imageView obrázek lokace
     */
    private void clickOnExit(String name, ImageView imageView) {
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            String command = "jdi ";
            console.appendText("\n" + command + name + "\n");
            String gameAnswer = game.processAction(command + name);
            console.appendText("\n" + gameAnswer + "\n");
        });
    }


    @Override
    public void update() {
        loadCurrentExits();
    }

    public Node getPanel() {return vbox; }

}

