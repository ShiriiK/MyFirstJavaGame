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
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import logic.Game;
import test.Runner;
import gui.ui.TextInterface;

/**
 * Spouštěcí třída aplikace.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Marcel Valový
 * @author Alena Kalivodová
 * @version ZS2021, 2021-11-10
 */

public class Main extends Application {

    public static Game game = new Game();
    public static final TextArea console = createConcole();
    private static final TextField userInput = new TextField();

    /**
     * Spouštěcí metoda aplikace. Vyhodnotí parametry, se kterými byla aplikace
     * spuštěna, a na základě nich rozhodne, jakou operaci provede <i>(hra v textovém nebo grafickém rozhraní, výpis
     * testovacích scénářů, spuštění testovacích scénářů(scénáře jsou nefuknkční, stejně tak testy))</i>.
     * <p>
     * Pokud byla aplikace spuštěna s nepodporovanými parametry, vypíše nápovědu
     * a skončí.
     *
     * @param args parametry aplikace z příkazové řádky
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
            } else if (args.length == 1 && args[0].equalsIgnoreCase("SHOW_SCENARIOS")) {
                Runner runner = new Runner();
                System.out.println(runner.showAllScenarios());
            } else if (args.length == 1 && args[0].equalsIgnoreCase("RUN_SCENARIOS")) {
                Runner runner = new Runner();
                System.out.println(runner.runAllScenarios());
            }  else {
                System.out.println("\nPro spuštění hry můžeš použít následující parametry:");
                System.out.println("  <žádné> nebo gui    : Zapne hru v grafickém rozhraní.");
                System.out.println("  text                : Zapne hru v textovém rozhraní.");
                System.out.println("  SHOW_SCENARIOS      : Vypíše kroky testovacího scénáře.(nefunkční)");
                System.out.println("  RUN_SCENARIOS       : Otestuje krory testovacího scénáře.(nefunkční)");

                throw  new IllegalArgumentException("Zadaný neplatný argument: " + parametr);
            }
        } else {
            launch(args);
        }
    }

    /**
     * Metoda pro vytvoření scény s grafickým rozhraním a její uspořádání.
     * @param primaryStage stage, které je přidělena scéna, kde se zobrazuje grafické rozhraní hry
     */
    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();

        //nastavení konzole
        Node node = setUpConsoleArea();
        borderPane.setCenter(node);
        BorderPane.setMargin(node, new Insets(10));

        //nastavení panelu s itemy v lokaci
        ItemPanel itemsPanel = new ItemPanel(game, console);


        //nastavení panelu s npc v lokaci
        NpcAndWeaponPanel npcsPanel = new NpcAndWeaponPanel(game, console);

        //nastavení pohlaví
        ScreenSelectGender selectGender = new ScreenSelectGender(game, console);

        //nastavení rasy
        ScreenSelectRace selectRace = new ScreenSelectRace(game, console);

        //nastavení jména
        ScreenSelectName selectName = new ScreenSelectName(game, console);

        //nastavení zobrazení souboje
        ScreenCombat combat = new ScreenCombat(game, console);

        //nastavaení zobrazení interakce s npc
        ScreenInteracting interacting = new ScreenInteracting(game, console);

        MenuPanel menuBar = new MenuPanel(game, console, primaryStage);

        //nastavení panelu lokace (obrázek aktuální lokace + daší viz parametry)
        BaseScreen baseScreen = new BaseScreen(game, console, primaryStage, itemsPanel, npcsPanel, selectGender, selectRace, selectName, interacting, combat, menuBar);
        borderPane.setTop(baseScreen.getGameMainScreen());

        //nastavení panelu východů
        ExitPanel exitPanel = new ExitPanel(borderPane, baseScreen);
        borderPane.setRight(exitPanel.getPanel());

        //nastavení panelu inventáře
        InventoryPanel inventoryPanel = new InventoryPanel(game, console);
        borderPane.setLeft(inventoryPanel.getPanel());

        //nastavení scény
        Scene scene = new Scene(borderPane, Constants.SCEEN_WIDTH, Constants.SCEEN_HEIGHT);
        scene.getStylesheets().add("adventura.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle(Constants.GAME_TITLE);
        primaryStage.setResizable(false);
        userInput.requestFocus();
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * Metoda pro nastaven9 console
     */
    private VBox setUpConsoleArea() {

        Label enterCommand = new Label("Zadej příkaz: ");
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
     * Metoda pro připravení textového pole, do kterého uživatel zadává příkazy a hra je vyhodnocuje.
     * @param console TextArea ve které se vypisují příkazy zadané hráčem a odpovědi hry na tyto příkazy
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
     * Metoda pro vytvoření console.
     * @return vytvořená concole
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