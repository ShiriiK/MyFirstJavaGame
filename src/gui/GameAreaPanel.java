package gui;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.*;
import main.GameBase;
import util.Observer;

/**
 * Třída implementující rozhraní Observer.
 * GameAreaPanel nastavuje zobrazení mainGameScreen v top borderPane.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Marcel Valový
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-06
 */

public class GameAreaPanel implements Observer {
    private final ItemPanel itemsPanel;
    private final RightPanel rightPanel;
    private final ScreenSelectGender selectGender;
    private final ScreenSelectRace selectRace;
    private final ScreenSelectName selectName;
    private final ScreenCombat combat;
    private final ScreenInteracting interacting;
    private final Game game;
    private final TextArea console;
    private final Stage stage;
    private final BorderPane gameMainScreen = new BorderPane();


    //Konstruktor
    public GameAreaPanel(Game game, TextArea console, Stage stage, ItemPanel itemsPanel, RightPanel rightPanel, ScreenSelectGender selectGender,
           ScreenSelectRace selectRace, ScreenSelectName selectName, ScreenInteracting interacting,
           ScreenCombat combat) {
        this.game = game;
        this.console = console;
        this.stage = stage;
        this.itemsPanel = itemsPanel;
        this.rightPanel = rightPanel;
        this.selectGender = selectGender;
        this.selectRace = selectRace;
        this.selectName = selectName;
        this.interacting = interacting;
        this.combat = combat;

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
     *  2) výběr jména
     *  3) interakce s npc
     *  4) souboj s npc
     *  5) normální - obrázek lokace uprostřed, obrázky npc/zbraní v pravo, obrázky itemů v levo
     */
    private void loadArea() {
        gameMainScreen.getChildren().clear();
        gameMainScreen.setTop(prepareMenu());
        gameMainScreen.setMaxHeight(570.0);
        if (game.getGameState().getPhase() == 0) {
            gameMainScreen.setCenter(selectGender.getSelectGender());
        } else if (game.getGameState().getPlayer().getRace().getName().equals("nic")) {
            gameMainScreen.setCenter(selectRace.getSelectRace());
        } else if (game.getGameState().getPhase() == 1) {
            gameMainScreen.setCenter(selectName.getSelectName());
        } else if (game.getGameState().isInCombat()){
            gameMainScreen.setCenter(combat.getCombatScreen());
        } else if (game.getGameState().isInteracting()){
            gameMainScreen.setCenter(interacting.getInteractingScreen());

        } else if (game.getGameState().getCurrentLocation().getName().equals("celna_na_pravo")){
            normalScreen();
            Button save = new Button();
            save.setOnAction(e-> {
                console.appendText("zachraň_tue");
                String gameAnswer = game.processAction("zachraň_tue");
                console.appendText("\n" + gameAnswer + "\n");
            });
            HBox buttonBox = new HBox(save);
            buttonBox.setAlignment(Pos.CENTER);
            gameMainScreen.setBottom(buttonBox);

        }  else {
            normalScreen();
        }
    }

    private MenuBar prepareMenu() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("Soubor");
        Menu helpMenu = new Menu("Nápověda");

        ImageView icon = new ImageView(new Image(GameAreaPanel.class.getResourceAsStream("/zdroje/icon.jpg"),
                40.0,25.0,false, true));

        MenuItem newGame = new MenuItem("Nová hra", icon);
        newGame.setAccelerator(KeyCombination.keyCombination("CTRL+N"));
        MenuItem end = new MenuItem("Konec");
        MenuItem map = new MenuItem("Mapa");
        MenuItem help = new MenuItem("Nápověda");

        newGame.setOnAction(e-> {
            stage.close();
            GameBase gameBase = new GameBase();
            Stage primaryStage = new Stage();
            gameBase.start(primaryStage);
        });

        end.setOnAction(e ->{
            game.setTheEnd(true);
            console.setEditable(false);
            console.appendText("konec");
            String gameAnswer = game.processAction("konec");
            console.appendText("\n" + gameAnswer + "\n");
        });

        map.setOnAction(e->{
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Mapa");

            ImageView imageView = new ImageView(new Image(GameAreaPanel.class.getResourceAsStream("/zdroje/mapa.jpg"),
                    1200.0, 600.0, true, false));

            Button close = new Button("Zavřít");
            close.setStyle("-fx-font-family: Garamond");
            close.setStyle("-fx-font-size: 25.0");
            close.setOnAction(event->{
                stage.close();
            });

            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.setStyle("-fx-background-color: BLACK");
            vBox.getChildren().addAll(imageView, close);

            Scene scene = new Scene(vBox);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        });

        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
        fileMenu.getItems().addAll(newGame,separatorMenuItem,end);
        helpMenu.getItems().addAll(map,help);

        menuBar.getMenus().addAll(fileMenu,helpMenu);

        return  menuBar;
    }

    /**
     * Metoda pro nastavení běžné obrazovky.
     */
    private void normalScreen() {
        Location location = game.getGameState().getCurrentLocation();
        Player player = game.getGameState().getPlayer();
        Partner partner = game.getGameState().getPartner();
        String locationName = location.getName();

        Label locationLabel = new Label("Aktuální lokace: " + location.getDisplayName());
        locationLabel.setStyle("-fx-font-size: 30.0");

        Tooltip locationTip = new Tooltip(location.getDescription());
        locationLabel.setTooltip(locationTip);

        Button playerButton = getPlayerButton(player);

        Button partnerButton = getPartnerButton(partner);

        BorderPane topPane = new BorderPane();
        topPane.setLeft(playerButton);
        topPane.setCenter(locationLabel);
        topPane.setRight(partnerButton);

        ImageView center = new ImageView(new Image
                (GameState.class.getResourceAsStream("/zdroje/" + locationName + ".jpg"),
                        1000.0, 550.0, false, false));

        VBox vBox = new VBox(topPane, center);
        gameMainScreen.setCenter(vBox);
        gameMainScreen.setLeft(itemsPanel.getPanel());
        gameMainScreen.setRight(rightPanel.getPanel());
    }

    private Button getPartnerButton(Partner partner) {
        Button partnerButton = new Button(partner.getPartnerName());
        partnerButton.getStylesheets().add("buttonpls.css");
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



            ImageView playerImageView = new ImageView(new Image(GameAreaPanel.class.getResourceAsStream(
                    "/zdroje/"+ partner.getPartnerName() + ".jpg"),
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

    private Button getPlayerButton(Player player) {
        Button playerButton = new Button("Hráč");
        playerButton.getStylesheets().add("buttonpls.css");
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



            ImageView playerImageView = new ImageView(new Image(GameAreaPanel.class.getResourceAsStream(
                    "/zdroje/"+ player.getRace().getName() +"_"+ player.getPlayerGender() + ".jpg"),
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

    public Node getGameMainScreen() {
        return gameMainScreen;
    }

    @Override
    public void update() {
        loadArea();
    }
}
