package game;

import gui.ExitPanel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import logic.Game;
import test.Runner;
import ui.TextInterface;

/**
 * Spouštěcí třída aplikace.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Marcel Valový
 * @author Alena Kalivodová
 * @version ZS2021, 2021-10-16
 */

public class GameBase extends Application {

    private final Game game = new Game();
    private TextField userInput;

    /**
     * Spouštěcí metoda aplikace. Vyhodnotí parametry, se kterými byla aplikace
     * spuštěna, a na základě nich rozhodne, jakou operaci provede <i>(hra v textovém nebo grafickém rozhraní, výpis
     * testovacích scénářů, spuštění testovacích scénářů)</i>.
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
                System.out.println("  SHOW_SCENARIOS      : Vypíše kroky testovacího scénáře.");
                System.out.println("  RUN_SCENARIOS       : Otestuje krory testovacího scénáře.");

                throw  new IllegalArgumentException("Zadaný neplatný argument: " + parametr);
            }
        } else {
            launch(args);
        }

    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();

        TextArea console = createConcole();
        borderPane.setCenter(console);

        Label enterCommand = new Label("Zadej příkaz: ");
        enterCommand.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        prepareTextField(console);

        prepareLowerBox(borderPane, enterCommand);

        ExitPanel exitPanel = new ExitPanel(game.getGameState());
        borderPane.setRight(exitPanel.getListView());

        Scene scene = new Scene(borderPane, 800, 450);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Adventura");
        userInput.requestFocus();
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });



    }

    private void prepareLowerBox(BorderPane borderPane, Label enterCommand) {
        HBox lowerBox = new HBox();
        lowerBox.setAlignment(Pos.CENTER);
        lowerBox.getChildren().addAll(enterCommand, userInput);
        borderPane.setBottom(lowerBox);
    }

    private void prepareTextField(TextArea console) {
        userInput = new TextField();
        userInput.setOnAction(event ->  {
            String command = userInput.getText();
            console.appendText("\n" + command + "\n");
            userInput.setText("");
            String gameAnswer = game.processAction(command);
            console.appendText("\n" + gameAnswer + "\n");

            if (game.theEnd()) {
                userInput.setEditable(false);
            }
        });
    }

    private TextArea createConcole() {
        TextArea console = new TextArea();
        console.setText(game.theBeginning());
        console.setEditable(false);
        return console;
    }

}
