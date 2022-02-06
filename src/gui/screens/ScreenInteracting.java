package gui.screens;

import gui.util.Constants;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
import logic.blueprints.Npc;
import logic.blueprints.Player;
import gui.util.Observer;
import saving_tue.Main;

import java.util.Set;

/**
 * ScreenInteracting sets the display of the interactingScreen in the top borderPane when displaying the interaction with npc.
 * @author Alena Kalivodová
 */

public class ScreenInteracting implements Observer {

    private final HBox interactingScreen = new HBox();

    public ScreenInteracting() {
        if(Main.game.getGameState().isInteracting()){
            init();
        }

        Main.game.getGameState().registerObserver(this);
    }

    /**
     * Method for setting interactingScreen.
     */
    private void init() {
        interactingScreen.getChildren().clear();
        setPlayerImageView();
        setNpcImageView();

    }

    /**
     * Method for setting playerImageView.
     */
    private void setPlayerImageView() {
        Player player = Main.game.getGameState().getPlayer();
        ImageView playerImageView;
        String race = player.getRace().getName();
        if (player.getPlayerGender().equals("female")) {
            playerImageView = new ImageView(new Image("/player/" + race +"_female.jpg",
                    Constants.INT_PICS_WIDTH, Constants.INT_PICS_HEIGHT, false, false));
        } else {
            playerImageView = new ImageView(new Image("/player/" + race +"_male.jpg",
                    Constants.INT_PICS_WIDTH, Constants.INT_PICS_HEIGHT, false, false));
        }
        interactingScreen.getChildren().add(playerImageView);
    }

    /**
     * Method for set npcImageView.
     */
    private void setNpcImageView() {
        Npc npc = Main.game.getGameState().getInteractingNpc();
        String npcName = npc.getName();

        ImageView npcImageView = new ImageView(new Image("/npcs/" + npcName +".jpg",
                Constants.INT_PICS_WIDTH, Constants.INT_PICS_HEIGHT, false, false, true));

        interactingScreen.getChildren().add(npcImageView);

        npcImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            // Displays conversation with npc
            if (event.getButton() == MouseButton.PRIMARY) {
                Main.console.appendText("\ntalk_to "+ npcName + "\n");
                String gameAnswer = Main.game.processAction("talk_to "+ npcName);
                Main.console.appendText("\n" + gameAnswer + "\n");
            }
            // Leaving the screen when the player interacts with the npc to the normal location screen
            else if (event.getButton() == MouseButton.MIDDLE) {
                Main.game.getGameState().setInteracting(false);
                Main.game.getGameState().setInteractingNpc(null);
            }
            // Create a new stage to display the option to select the item to be passed
            else {
                Stage stage = new Stage();
                Button confirmButton = new Button("Confirm selection");
                ChoiceBox<String> choiceBox = new ChoiceBox<>();
                VBox vBox = new VBox();
                Label label = new Label();

                StyleIt(stage, confirmButton, choiceBox, vBox, label);

                Set<String> inventory = Main.game.getGameState().getInventory().itemsInInventory();
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
     * Adjusts the appearance of the following:
     * @param stage stage
     * @param confirmButton button to confirm selection
     * @param choiceBox choiceBox with item selection options
     * @param vBox containing the label, choiceBox and confirmationButton
     * @param label label
     */
    private void StyleIt(Stage stage, Button confirmButton, ChoiceBox<String> choiceBox, VBox vBox, Label label) {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Select item");

        choiceBox.setStyle( "-fx-font-family: Garamond");
        choiceBox.setStyle("-fx-font-size: 20.0");

        confirmButton.setFont(Font.font("Garamond", 20.0));

        label.setText("Select the item you want to give or close the window");
        label.setFont(Font.font("Garamond", 20.0));
        label.setTextFill(Color.WHITE);

        vBox.setSpacing(3.0);
        vBox.setStyle(" -fx-background-color: BLACK;");
    }

    /**
     * A method for processing an action where the player selects an item to pass to the npc.
     * @param stage stage
     * @param choiceBox choiceBox
     * @param confirmButton button to confirm the selection
     */
    private void actionGive(String npcName, Stage stage, ChoiceBox<String> choiceBox, Button confirmButton) {
        confirmButton.setOnAction(e -> {
            String selected = choiceBox.getValue();

            Main.console.appendText("\ngive " + npcName + " " + selected + "\n");
            String gameAnswer = Main.game.processAction("give " + npcName + " " + selected);
            Main.console.appendText("\n" + gameAnswer + "\n");

            stage.close();
        });
    }

    /**
     * Updates the screen when communicating with an npc (only if the player is communicating with an npc)
     */
    @Override
    public void update() {
        if (Main.game.getGameState().isInteracting()){
            init();
        }
    }

    public Node getInteractingScreen(){
        return interactingScreen;
    }
}
