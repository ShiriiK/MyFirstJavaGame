package gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Game;
import main.GameBase;

/**
 * MenuPanel nastavuje zobrazení menuBarPanel v top borderPane v GameAreaPanel.
 * </p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-10
 */


public class MenuPanel {
    private final Game game;
    private final TextArea console;
    private final Stage primaryStage;
    private final MenuBar menuBar = new MenuBar();

    //Konstruktor
    public MenuPanel(Game game, TextArea console, Stage primaryStage) {
        this.game = game;
        this.console = console;
        this.primaryStage = primaryStage;

        prepareMenu();
    }

    /**
     * Metoda pro nastavení jednotlivých menu a jejich itemů
     */
    private void prepareMenu() {
        Menu fileMenu = new Menu("Soubor");
        Menu helpMenu = new Menu("Nápověda");

        ImageView icon = new ImageView(new Image("/zdroje/icon.jpg",
                40.0,25.0,false, true, true));

        MenuItem newGame = new MenuItem("Nová hra", icon);
        newGame.setAccelerator(KeyCombination.keyCombination("CTRL+N"));
        MenuItem end = new MenuItem("Konec");
        MenuItem map = new MenuItem("Mapa");
        MenuItem help = new MenuItem("Nápověda");

        actionNewGame(newGame);
        actionEndGame(end);
        actionMap(map);
        actionHelp(help);

        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
        fileMenu.getItems().addAll(newGame,separatorMenuItem,end);
        helpMenu.getItems().addAll(map,help);

        menuBar.getMenus().addAll(fileMenu,helpMenu);
    }

    private void actionHelp(MenuItem help) {
        help.setOnAction(e->{
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Nápověda");

            WebView view = new WebView();
            view.getEngine().load(getClass().getResource("/zdroje/napoveda.html").toExternalForm());

            Scene scene = new Scene(view);
            stage.setScene(scene);
            stage.showAndWait();
        });
    }

    /**
     * Metoda pro zpracování akce, kdy hráč klikne na MenuItem map
     * @param map MenuItem
     */
    private void actionMap(MenuItem map) {
        map.setOnAction(e->{
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Mapa");

            ImageView imageView = new ImageView(new Image("/zdroje/mapa.jpg", 1200.0, 600.0,
                    true, false, true));

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
    }

    /**
     * Metoda pro zpracování akce, kdy hráč klikne na MenuItem end
     * @param end MenuItem
     */
    private void actionEndGame(MenuItem end) {
        end.setOnAction(e ->{
            game.setTheEnd(true);
            console.setEditable(false);
            console.appendText("konec");
            String gameAnswer = game.processAction("konec");
            console.appendText("\n" + gameAnswer + "\n");
        });
    }

    /**
     * Metoda pro zpracování akce, kdy hráč klikne na MenuItem newGame
     * @param newGame MenuItem
     */
    private void actionNewGame(MenuItem newGame) {
        newGame.setOnAction(e-> {
            primaryStage.close();
            GameBase gameBase = new GameBase();
            Stage primaryStage = new Stage();
            gameBase.start(primaryStage);
        });
    }

    /**
     * @return menuBar
     */
    public Node getMenuBar() {
        return menuBar;
    }
}
