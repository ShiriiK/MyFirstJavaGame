package gui.panels;

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
import saving_tue.Main;

/**
 * MenuPanel sets up the display of the MenuBarPanel in the top borderPanel of the GameAreaPanel.
 * @author Alena Kalivodová
 */

public class MenuPanel {

    private final Stage primaryStage;
    private final MenuBar menuBar = new MenuBar();

    public MenuPanel(Stage primaryStage) {
        this.primaryStage = primaryStage;

        ImageView icon = new ImageView(new Image("/other/icon.jpg",
                40.0,25.0,false, true, true));

        prepareMenu(icon);
    }

    /**
     * Method for setting up menu items
     */
    private void prepareMenu(ImageView icon) {
        // Creating menus
        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");

        // Creating menu items
        MenuItem end = new MenuItem("End");
        MenuItem map = new MenuItem("Map");
        MenuItem help = new MenuItem("Help");
        MenuItem newGame = new MenuItem("New game", icon);
        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();

        // Putting action on menuItems
        actionNewGame(newGame);
        actionEndGame(end);
        actionMap(map);
        actionHelp(help);

        // Putting items into menus
        fileMenu.getItems().addAll(newGame,separatorMenuItem,end);
        helpMenu.getItems().addAll(map,help);

        // Putting menus into menuBar
        menuBar.getMenus().addAll(fileMenu,helpMenu);
    }

    /**
     * Method for opening help by clicking on MenuItem help
     */
    private void actionHelp(MenuItem help) {
        help.setOnAction(e->{
            // Setting stage
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Help");

            // Setting WebView
            WebView view = new WebView();
            view.getEngine().load(getClass().getResource("/other/help.html").toExternalForm());

            // Setting scene and showing stage
            Scene scene = new Scene(view);
            stage.setScene(scene);
            stage.showAndWait();
        });
    }

    /**
     * Method for opening map by clicking on MenuItem map
     */
    private void actionMap(MenuItem map) {
        map.setOnAction(e->{
            // Setting stage
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Map");
            stage.setResizable(false);

            // Setting map image
            ImageView mapImage = new ImageView(new Image("/other/map.jpg", 1200.0, 600.0,
                    true, false, true));
            mapImage.setFitHeight(600);
            mapImage.setFitWidth(1200);

            // Setting button
            Button close = new Button("Close");
            close.setStyle("-fx-font-family: Garamond");
            close.setStyle("-fx-font-size: 25.0");

            // Setting close button
            close.setOnAction(event->{
                stage.close();
            });

            // Setting layout
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.setStyle("-fx-background-color: BLACK");
            vBox.getChildren().addAll(mapImage, close);

            // Setting scene and showing stage
            Scene scene = new Scene(vBox);
            stage.setScene(scene);
            stage.showAndWait();
        });
    }

    /**
     * Method for ending game by clicking on MenuItem end
     */
    private void actionEndGame(MenuItem end) {
        end.setOnAction(e ->{
            Main.game.setTheEnd(true);
            Main. console.setEditable(false);

            Main.console.appendText("end");
            String gameAnswer = Main.game.processAction("end");
            Main. console.appendText("\n" + gameAnswer + "\n");
        });
    }

    /**
     * Method for creating new game by clicking on MenuItem newGame
     */
    private void actionNewGame(MenuItem newGame) {
        newGame.setAccelerator(KeyCombination.keyCombination("CTRL+N"));

        newGame.setOnAction(e-> {
            primaryStage.close();
            Main main = new Main();
            Stage primaryStage = new Stage();
            main.start(primaryStage);
        });
    }

    public Node getMenuBar() {
        return menuBar;
    }
}
