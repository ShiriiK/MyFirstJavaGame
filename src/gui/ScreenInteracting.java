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
 * ScreenInteracting nastavuje ImageView hráče a npc, se kterým komunikuje
 * </p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 *  * @author Alena Kalivodová
 *  * @version ZS-2021, 2021-11-05
 */

public class ScreenInteracting implements Observer {

    private Game game;
    private TextArea console;
    private ImageView playerImageView = new ImageView();
    private ImageView npcImageVIew = new ImageView();
    Stage stage = new Stage();
    Button confirmButton = new Button("Potvrď výběr");
    ChoiceBox<String> choiceBox = new ChoiceBox<>();
    Label label = new Label();
    private VBox vBox = new VBox();

    public ScreenInteracting(Game game, TextArea console) {
        this.game = game;
        this.console = console;
        GameState gameState = game.getGameState();

        gameState.registerObserver(this);

        if(gameState.isInteracting()){
            init();
        }
    }

    private void init() {
        //Nastavení ImageVIew hráče
        Player player = game.getGameState().getPlayer();
        setPlayerImageView(player);

        //Nastavení ImageView npc
        Npc npc = game.getGameState().getInteractingNpc();
        String npcName = npc.getName();

        setNpcImageView(npcName);

    }

    private void setPlayerImageView(Player player) {
        Image playerImage;
        if (player.getPlayerGender().equals("female")) {
            playerImage = new Image("/zdroje/"+ player.getRace().getName() +"_žena.jpg",
                    900.0, 470.0, false, false);
        } else {
            playerImage = new Image ("/zdroje/"+ player.getRace().getName() +"_muž.jpg",
                    900.0, 470.0, false, false);
        }
        playerImageView.setImage(playerImage);
    }

    private void setNpcImageView(String npcName) {
        Image npcImage = new Image
                ("/zdroje/" + npcName + ".jpg", 900.0, 470.0, false, false);

        npcImageVIew.setImage(npcImage);

        npcImageVIew.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //Zobrazí rozhovor s npc
            if (event.getButton() == MouseButton.PRIMARY) {
                console.appendText("\npromluv_si_s "+ npcName + "\n");
                String gameAnswer = game.processAction("promluv_si_s "+ npcName);
                console.appendText("\n" + gameAnswer + "\n");
            }
            //Odchod z obrazovky, kdy hráč komunikuje s npc do normální obrazovky s lokací
            else if (event.getButton() == MouseButton.MIDDLE) {
                game.getGameState().setInteracting(false);

            }
            //Vytvoření nové stage pro zobrazení možnosti výběru itemu, který má být předán
            else {
                StyleIt();

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

    private void StyleIt() {
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

    private void actionGive(String npcName, Stage stage, ChoiceBox<String> choiceBox, Button confirmButton) {
        confirmButton.setOnAction(e -> {
            String selected = choiceBox.getValue();

            console.appendText("\nnabídni " + npcName + " " + selected + "\n");
            String gameAnswer = game.processAction("nabídni " + npcName + " " + selected);
            console.appendText("\n" + gameAnswer + "\n");

            stage.close();
        });
    }

    public Node getPlayer(){
        return playerImageView;
    }

    public Node getNpc(){
        return npcImageVIew;
    }

    @Override
    public void update() {
        if (game.getGameState().isInteracting()){
            init();
        }
    }
}
