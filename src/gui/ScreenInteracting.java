package gui;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Game;
import logic.GameState;
import logic.Npc;
import logic.Player;
import util.Observer;
import java.util.Set;

/**
 * ScreenInteracting nastavuje zobrazení interactingScreen v top borderPane při zobrazení interakce s npc.
 * </p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 *  * @author Alena Kalivodová
 *  * @version ZS-2021, 2021-11-06
 */

public class ScreenInteracting implements Observer {

    private final Game game;
    private final TextArea console;
    private final HBox interactingScreen = new HBox();

    //Konstruktor
    public ScreenInteracting(Game game, TextArea console) {
        this.game = game;
        this.console = console;

        if(game.getGameState().isInteracting()){
            init();
        }

        game.getGameState().registerObserver(this);
    }

    /**
     * Metoda pro nastavení interactingScreen.
     */
    private void init() {
        interactingScreen.getChildren().clear();
        //Nastavení ImageVIew hráče
        setPlayerImageView();

        //Nastavení ImageView npc
        setNpcImageView();

    }

    /**
     * Metoda pro nastavení playerImageView.
     */
    private void setPlayerImageView() {
        Player player = game.getGameState().getPlayer();
        ImageView playerImageView;
        if (player.getPlayerGender().equals("žena")) {
            playerImageView = new ImageView(new Image(GameState.class.getResourceAsStream("/zdroje/"+ player.getRace().getName() +"_žena.jpg"),
                    900.0, 470.0, false, false));
        } else {
            playerImageView = new ImageView(new Image(GameState.class.getResourceAsStream("/zdroje/"+ player.getRace().getName() +"_muž.jpg"),
                    900.0, 470.0, false, false));
        }
        interactingScreen.getChildren().addAll(playerImageView);
    }

    /**
     * Metoda pro nastavené npcImageView.
     */
    private void setNpcImageView() {
        Npc npc = game.getGameState().getInteractingNpc();
        String npcName = npc.getName();

        ImageView npcImageView = new ImageView(new Image(GameState.class.getResourceAsStream("/zdroje/"+ npcName +".jpg"),
                    900.0, 470.0, false, false));

        interactingScreen.getChildren().addAll(npcImageView);

        npcImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //Zobrazí rozhovor s npc
            if (event.getButton() == MouseButton.PRIMARY) {
                console.appendText("\npromluv_si_s "+ npcName + "\n");
                String gameAnswer = game.processAction("promluv_si_s "+ npcName);
                console.appendText("\n" + gameAnswer + "\n");
            }
            //Odchod z obrazovky, kdy hráč komunikuje s npc do normální obrazovky s lokací
            else if (event.getButton() == MouseButton.MIDDLE) {
                game.getGameState().setInteracting(false);
                game.getGameState().setInteractingNpc(null);
            }
            //Vytvoření nové stage pro zobrazení možnosti výběru itemu, který má být předán
            else {
                Stage stage = new Stage();
                Button confirmButton = new Button("Potvrď výběr");
                ChoiceBox<String> choiceBox = new ChoiceBox<>();
                VBox vBox = new VBox();
                Label label = new Label();

                StyleIt(stage, confirmButton, choiceBox, vBox, label);

                Set<String> inventory = game.getGameState().getInventory().itemsInInventory();
                for (String item : inventory){
                    choiceBox.getItems().add(item);
                }

                actionGive(npcName, stage, choiceBox, confirmButton);

                vBox.getChildren().addAll(label, choiceBox, confirmButton);

                Scene scene =  new Scene(vBox);
                stage.setScene(scene);
                stage.showAndWait();
            }
        });
    }

    /**
     * Upraví vzhled následujících:
     * @param stage stage
     * @param confirmButton button na potvrzení výberu
     * @param choiceBox choiceBox s možnostmi výběru itemů
     * @param vBox ve kterém je zobraten label, choiceBox a confirmationButton
     * @param label label
     */
    private void StyleIt(Stage stage, Button confirmButton, ChoiceBox<String> choiceBox, VBox vBox, Label label) {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Vyber věc");

        choiceBox.setStyle( "-fx-font-family: Garamond");
        choiceBox.setStyle("-fx-font-size: 20.0");

        confirmButton.setFont(Font.font("Garamond", 20.0));

        label.setText("Vyber věc, kterou chceš předat nebo zavři okno");
        label.setFont(Font.font("Garamond", 20.0));
        label.setTextFill(Color.WHITE);

        vBox.setSpacing(3.0);
        vBox.setStyle(" -fx-background-color: BLACK;");
    }

    /**
     * Metoda pro zpracování akce, kdy hráč vybere item, který chce předat npc.
     * @param stage stage
     * @param choiceBox choiceBox
     * @param confirmButton button na potvrzení výběru
     */
    private void actionGive(String npcName, Stage stage, ChoiceBox<String> choiceBox, Button confirmButton) {
        confirmButton.setOnAction(e -> {
            String selected = choiceBox.getValue();

            console.appendText("\nnabídni " + npcName + " " + selected + "\n");
            String gameAnswer = game.processAction("nabídni " + npcName + " " + selected);
            console.appendText("\n" + gameAnswer + "\n");

            stage.close();
        });
    }

    public Node getInteractingScreen(){
        return interactingScreen;
    }

    @Override
    public void update() {
        if (game.getGameState().isInteracting()){
            init();
        }
    }
}
