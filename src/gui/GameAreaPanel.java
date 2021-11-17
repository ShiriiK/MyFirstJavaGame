package gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.*;
import util.Observer;

/**
 * Třída implementující rozhraní Observer.
 * GameAreaPanel nastavuje zobrazení mainGameScreen v top borderPane.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-10
 */

public class GameAreaPanel implements Observer {

    private final ItemPanel itemsPanel;
    private final RightPanel rightPanel;
    private final ScreenSelectGender selectGender;
    private final ScreenSelectRace selectRace;
    private final ScreenSelectName selectName;
    private final ScreenCombat combat;
    private final ScreenInteracting interacting;
    private final MenuPanel menuBar;
    private final Game game;
    private final TextArea console;
    private final BorderPane gameMainScreen = new BorderPane();

    //Konstruktor
    public GameAreaPanel(Game game, TextArea console, Stage stage, ItemPanel itemsPanel, RightPanel rightPanel, ScreenSelectGender selectGender,
                         ScreenSelectRace selectRace, ScreenSelectName selectName, ScreenInteracting interacting,
                         ScreenCombat combat, MenuPanel menuBar) {
        this.game = game;
        this.console = console;
        this.itemsPanel = itemsPanel;
        this.rightPanel = rightPanel;
        this.selectGender = selectGender;
        this.selectRace = selectRace;
        this.selectName = selectName;
        this.interacting = interacting;
        this.combat = combat;
        this.menuBar = menuBar;

        gameMainScreen.setStyle(" -fx-background-color: WHITE;");
        gameMainScreen.setStyle(" -fx-padding: 5;");

        loadArea();

        game.getGameState().registerObserver(this);
    }

    /**
     * Metoda pro nastavení gameMainScreen.
     * Existuje 6 různých verzí gameMainScreen:
     *  1) výběr pohlaví
     *  2) výběr rasy
     *  3) výběr jména
     *  4) interakce s npc
     *  5) souboj s npc
     *  6) obrazovka v lokaci cela na pravo
     */
    private void loadArea() {
        gameMainScreen.getChildren().clear();
        gameMainScreen.setTop(menuBar.getMenuBar());
        gameMainScreen.setMaxHeight(570.0);
        //Výběr pohlaví
        if (game.getGameState().getPhase() == 0) {
            gameMainScreen.setCenter(selectGender.getSelectGender());
        }
        //Výběr rasy
        else if (game.getGameState().getPlayer().getRace().getName().equals("nic")) {
            gameMainScreen.setCenter(selectRace.getSelectRace());
        }
        //Výběr jména
        else if (game.getGameState().getPhase() == 1) {
            gameMainScreen.setCenter(selectName.getSelectName());
        }
        //Souboj
        else if (game.getGameState().isInCombat()){
            gameMainScreen.setCenter(combat.getCombatScreen());
        }
        //Komunikování
        else if (game.getGameState().isInteracting()){
            gameMainScreen.setCenter(interacting.getInteractingScreen());

        }
        //Normální obrazovka
        else {
            normalScreen();
        }
    }

    /**
     * Metoda pro nastavení běžné obrazovky.
     */
    private void normalScreen() {
        Location location = game.getGameState().getCurrentLocation();

        //Nastavení názvu aktuální lokace
        Label locationLabel = new Label("Aktuální lokace: " + location.getDisplayName());
        locationLabel.setStyle("-fx-font-size: 30.0");

        Tooltip locationTip = new Tooltip(location.getDescription());
        locationLabel.setTooltip(locationTip);

        //Nastavení tlačítka pro zobrazení statů hráče
        Button playerButton = getPlayerButton();
        //Nastavení tlačítka pro zobrazení statů partnera
        Button partnerButton = getPartnerButton();

        //Nastavení rozložení topPane
        BorderPane topPane = new BorderPane();
        topPane.setLeft(playerButton);
        topPane.setCenter(locationLabel);
        topPane.setRight(partnerButton);

        //Nastavení obrázku aktuální lokace
        ImageView center = new ImageView(new Image("/zdroje/" + location.getName() + ".jpg",
                        1000.0, 550.0, false, false, true));

        VBox vBox = new VBox(topPane, center);
        gameMainScreen.setCenter(vBox);
        gameMainScreen.setLeft(itemsPanel.getPanel());
        gameMainScreen.setRight(rightPanel.getPanel());
    }

    /**
     * Metoda pro nastavení buttonu pro partnera
     * @return button pro partnera
     */
    private Button getPartnerButton() {
        Partner partner = game.getGameState().getPartner();
        Button partnerButton = new Button(partner.getPartnerName());
        partnerButton.getStylesheets().add("buttonpls.css");

        //zobrazení nové stage se staty a obrázkem partnera (+ button na její zavření)
        partnerButton.setOnAction(e->{
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Parťák");

            Label label = new Label(partner.getPartner());
            label.setFont(Font.font("Garamond",30.0));
            label.setStyle("-fx-text-fill: WHITE");
            Button close = new Button("Zavřít");
            close.setFont(Font.font("Garamond",30.0));

            close.setOnAction(event->{
                stage.close();
            });

            BorderPane inPane = new BorderPane();
            inPane.setStyle(" -fx-background-color: BLACK;");
            inPane.setTop(label);
            inPane.setBottom(close);

            ImageView playerImageView = new ImageView(new Image("/zdroje/" + partner.getPartnerName() + ".jpg",
                    900.0, 470.0, false, false));

            BorderPane playerPane = new BorderPane();
            playerPane.setStyle("-fx-background-color: BLACK");
            playerPane.setLeft(playerImageView);
            playerPane.setCenter(inPane);

            Scene scene = new Scene(playerPane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();

        });
        return partnerButton;
    }

    private Button getPlayerButton() {
        Player player = game.getGameState().getPlayer();
        Button playerButton = new Button("Hráč");
        playerButton.getStylesheets().add("buttonpls.css");

        //zobrazení nové stage se staty a obrázkem hráče (+ button na její zavření)
        playerButton.setOnAction(e->{
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Hráč");

            Label label = new Label(player.getPlayer());
            label.setFont(Font.font("Garamond",30.0));
            label.setStyle("-fx-text-fill: WHITE");
            Button close = new Button("Zavřít");
            close.setFont(Font.font("Garamond",30.0));

            close.setOnAction(event->{
                stage.close();
            });

            BorderPane inPane = new BorderPane();
            inPane.setStyle(" -fx-background-color: BLACK;");
            inPane.setTop(label);
            inPane.setBottom(close);

            ImageView playerImageView = new ImageView(new Image("/zdroje/" + player.getRace().getName() + "_" + player.getPlayerGender() + ".jpg",
                    900.0, 470.0, false, false));

            BorderPane playerPane = new BorderPane();
            playerPane.setStyle("-fx-background-color: BLACK");
            playerPane.setLeft(playerImageView);
            playerPane.setCenter(inPane);

            Scene scene = new Scene(playerPane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();

        });
        return playerButton;
    }

    /**
     * @return gameMainScreen
     */
    public Node getGameMainScreen() {
        return gameMainScreen;
    }

    /**
     * Aktualizuje gameMainScreen
     */
    @Override
    public void update() {
        loadArea();
    }
}
