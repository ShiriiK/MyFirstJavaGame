package saving_tue;

import gui.panels.*;
import gui.screens.*;
import gui.util.Constants;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import logic.Game;
import gui.ui.TextInterface;

/**
 * Application trigger class.
 *
 * @author Marcel Valový
 * @author Alena Kalivodová
 */

public class Main extends Application {

    public static Game game = new Game();
    public static final TextArea console = createConcole();
    private static final TextField userInput = new TextField();

    /**
     * Spouštěcí metoda aplikace. Evaluates the parameters with which the application was
     * the application was started with and decides what operation to perform based on them.
     * If the application was started with unsupported parameters, it prints a hint
     * and exits.
     *
     * @param args application parameters from the command line
     */
    public static void main(String[] args) {
        if (args.length > 0){
            String parametr = args[0];
            if (parametr.equals("gui")) {
                launch(args);
            } else if (parametr.equals("text")) {
                Game game = new Game();
                TextInterface ui = new TextInterface(game);
                ui.play();
                System.exit(0);
            } else {
                System.out.println("\nYou can use the following parameters to start the game:");
                System.out.println("<none> or gui : Turns the game on in the GUI.");
                System.out.println("text : Turns on the game in the text interface.");

                throw  new IllegalArgumentException("Invalid argument entered: " + parametr);
            }
        } else {
            launch(args);
        }
    }

    /**
     * A method for creating a GUI scene and arranging it.
     * @param primaryStage stage, which is assigned to the scene where the GUI of the game is displayed
     */
    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();

        // Console settings
        Node node = setUpConsoleArea();
        borderPane.setCenter(node);
        BorderPane.setMargin(node, new Insets(10));

        ItemPanel itemsPanel = new ItemPanel();
        NpcAndWeaponPanel npcsPanel = new NpcAndWeaponPanel();
        ScreenSelectGender selectGender = new ScreenSelectGender();
        ScreenSelectRace selectRace = new ScreenSelectRace();
        ScreenSelectName selectName = new ScreenSelectName();
        ScreenCombat combat = new ScreenCombat();
        ScreenInteracting interacting = new ScreenInteracting();
        MenuPanel menuBar = new MenuPanel(primaryStage);

        BaseScreen baseScreen = new BaseScreen(itemsPanel, npcsPanel, selectGender, selectRace, selectName, interacting, combat, menuBar);
        borderPane.setTop(baseScreen.getGameMainScreen());

        ExitPanel exitPanel = new ExitPanel(borderPane, baseScreen);
        borderPane.setRight(exitPanel.getPanel());

        InventoryPanel inventoryPanel = new InventoryPanel();
        borderPane.setLeft(inventoryPanel.getPanel());

        Scene scene = new Scene(borderPane, Constants.SCEEN_WIDTH, Constants.SCEEN_HEIGHT);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle(Constants.GAME_TITLE);
        primaryStage.getIcons().add(new Image("/other/icon.jpg"));
        primaryStage.setResizable(false);
        userInput.requestFocus();
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * Method for console setup
     */
    private VBox setUpConsoleArea() {

        Label enterCommand = new Label("Enter command: ");
        enterCommand.setStyle("-fx-font-size: 25.0");
        enterCommand.setStyle("-fx-font-weight: BOLD");

        setUpTextField(console);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.TOP_CENTER);
        hBox.getChildren().addAll(enterCommand, userInput);

        VBox consoleArea = new VBox(console, hBox);
        consoleArea.setAlignment(Pos.TOP_CENTER);
        consoleArea.setSpacing(10);

        return consoleArea;
    }

    /**
     * A method for preparing a text box where the user enters commands and the game evaluates them.
     * @param console TextArea in which the commands entered by the player and the game's responses to those commands are printed
     */
    private void setUpTextField(TextArea console) {
        userInput.setOnAction(event ->  {
            String command = userInput.getText();
            console.appendText(command);
            userInput.setText("");
            String gameAnswer = game.processAction(command);
            console.appendText("\n" + gameAnswer + "\n");

            if (game.theEnd()) {
                userInput.setEditable(false);
            }
        });
    }

    /**
     * Method for creating a console.
     * @return created concole
     */
    private static TextArea createConcole() {
        TextArea console = new TextArea();

        console.getStyleClass().add("console");
        console.setText(game.theBeginning());
        console.setEditable(false);
        console.setWrapText(true);
        console.setFont(Font.font("Garamond", 20.0));

        return console;
    }
}