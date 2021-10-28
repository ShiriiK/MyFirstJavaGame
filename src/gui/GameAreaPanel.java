package gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import logic.Game;
import logic.GameState;
import logic.Location;
import util.Observer;

/**
 * Třída implementující rozhraní Observer.
 * GameAreaPanel zobrazuje obrázek aktuální lokace.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Marcel Valový
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-23
 */

public class GameAreaPanel implements Observer {
    private ItemPanel itemsPanel;
    private RightPanel npcsPanel;
    private Game game;
    private BorderPane borderPane = new BorderPane();
    private final TextArea console;

    public GameAreaPanel(Game game, ItemPanel itemsPanel, RightPanel npcsPanel, TextArea console) {
        this.game = game;
        this.itemsPanel = itemsPanel;
        this.npcsPanel = npcsPanel;
        this.console = console;
        loadArea();
        GameState gameState = game.getGameState();
        gameState.registerObserver(this);

        borderPane.setStyle(" -fx-background-color: WHITE;");
        borderPane.setStyle(" -fx-border-width: 5; -fx-border-color: BLACK; -fx-padding: 5;");
    }

    /**
     * Metoda pro nastavení anchodPane pro zobrazení obrázku aktuální lokace
     */
    private void loadArea() {
        if (game.getGameState().getPhase() == 0) {
            ChooseGenderButtons();

        } else if (game.getGameState().getPhase() == 1) {
            ChooseNameTextField();

        } else {
            Location location = game.getGameState().getCurrentLocation();
            String locationName = location.getName();

            Label locationLabel = new Label("Aktuální lokace: " + locationName);
            locationLabel.setFont(Font.font("Garamond", 50));

            Tooltip locationTip = new Tooltip(location.getDescription());
            locationTip.setFont(Font.font("Garamond", 30));
            locationLabel.setTooltip(locationTip);

            HBox hBox = new HBox(locationLabel);
            hBox.setAlignment(Pos.CENTER);

            borderPane.setTop(hBox);

            ImageView center = new ImageView(new Image
                    (GameState.class.getResourceAsStream("/zdroje/" + locationName + ".jpg"),
                            860, 450, false, false));
            
            borderPane.setCenter(center);
            borderPane.setLeft(itemsPanel.getPanel());
            borderPane.setRight(npcsPanel.getPanel());
        }
    }

    private void ChooseNameTextField() {
        Label label = new Label("Vyber si jméno: ");
        label.setFont(Font.font("Garamond", 70));

        TextField userInput = new TextField();
        userInput.requestFocus();
        userInput.setFont(Font.font("Garamond", 70));
        userInput.setAlignment(Pos.CENTER);

        userInput.setOnAction(e -> {
            String name = userInput.getText();
            console.appendText("jméno " + name);
            userInput.setText("");
            String gameAnswer = game.processAction("jméno " + name);
            console.appendText( gameAnswer);
        });

        VBox vBox = new VBox();
        vBox.setPrefWidth(860);
        vBox.setPrefHeight(450);
        vBox.getChildren().addAll(label, userInput);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15.0);
        borderPane.setCenter(vBox);
    }


    private void ChooseGenderButtons() {
        Label label = new Label("Vyber si pohlaví: ");
        label.setFont(Font.font("Garamond", 70));

        Button female = new Button("Žena");
        female.setFont(Font.font("Garamond", 50));

        Button male = new Button("Muž");
        male.setFont(Font.font("Garamond", 50));

        female.setOnAction(e -> {
            console.appendText("pohlaví žena");
            String gameAnswer = game.processAction("pohlaví žena");
            console.appendText(gameAnswer);
        });
        male.setOnAction(e-> {
            console.appendText("pohlaví muž");
            String gameAnswer = game.processAction("pohlaví muž");
            console.appendText(gameAnswer);
        });

        VBox vBox = new VBox();
        vBox.setPrefWidth(860);
        vBox.setPrefHeight(450);
        vBox.getChildren().addAll(label, female, male);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15.0);
        borderPane.setCenter(vBox);
    }

    @Override
    public void update() {
        loadArea();
    }

    public Node getBorderPane() {
        return borderPane;
    }
}
