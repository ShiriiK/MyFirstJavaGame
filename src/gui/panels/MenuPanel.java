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

        prepareMenu();
    }

    /**
     * Method for setting up menu items
     */
    private void prepareMenu() {
        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");

        ImageView icon = new ImageView(new Image("/other/icon.jpg",
                40.0,25.0,false, true, true));

        MenuItem newGame = new MenuItem("New game", icon);
        newGame.setAccelerator(KeyCombination.keyCombination("CTRL+N"));
        MenuItem end = new MenuItem("End");
        MenuItem map = new MenuItem("Map");
        MenuItem help = new MenuItem("Help");

        actionNewGame(newGame);
        actionEndGame(end);
        actionMap(map);
        actionHelp(help);

        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
        fileMenu.getItems().addAll(newGame,separatorMenuItem,end);
        helpMenu.getItems().addAll(map,help);

        menuBar.getMenus().addAll(fileMenu,helpMenu);
    }

    /**
     * Method for processing the action when the player clicks on the MenuItem help
     */
    private void actionHelp(MenuItem help) {
        help.setOnAction(e->{
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Help");

            WebView view = new WebView();
            view.getEngine().load(getClass().getResource("/other/help.html").toExternalForm());

            Scene scene = new Scene(view);
            stage.setScene(scene);
            stage.showAndWait();
        });
    }

    /**
     * Method for processing the action when the player clicks on the MenuItem map
     */
    private void actionMap(MenuItem map) {
        map.setOnAction(e->{
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Map");

            ImageView mapImage = new ImageView(new Image("/other/map.jpg", 1200.0, 600.0,
                    true, false, true));
            mapImage.setFitHeight(600);
            mapImage.setFitWidth(1200);

            Button close = new Button("Close");
            close.setStyle("-fx-font-family: Garamond");
            close.setStyle("-fx-font-size: 25.0");

            close.setOnAction(event->{
                stage.close();
            });

            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.setStyle("-fx-background-color: BLACK");
            vBox.getChildren().addAll(mapImage, close);

            Scene scene = new Scene(vBox);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        });
    }

    /**
     * Method for processing the action when the player clicks on the MenuItem end
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
     * Method for processing the action when the player clicks on the MenuItem newGame
     */
    private void actionNewGame(MenuItem newGame) {
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
