package gui.screens;

import gui.util.Constants;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import saving_tue.Main;

/**
 * SelectName sets the display of selectNameScreen in the top borderPane when displaying the name selection.
 * @author Alena Kalivodová
 */

public class ScreenSelectName {

    private final VBox selectNameScreen = new VBox();

    public ScreenSelectName(){
        selectNameScreen.setPrefWidth(Constants.SELECTION_WIDTH);
        selectNameScreen.setPrefHeight(Constants.SELECTION_HEIGHT);
        selectNameScreen.getStyleClass().add("screen");

        init();
    }

    /**
     * Method for setting selectNameScreen.
     */
    private void init() {
        Label label = new Label("Choose you name: ");
        label.setStyle("-fx-font-size: 70.0");

        TextField userInput = new TextField();
        userInput.getStyleClass().add("user_name");

        action(userInput);

        selectNameScreen.getChildren().addAll(label, userInput);
    }

    /**
     * A method for processing an action where the player chooses a name.
     * @param userInput the name that the player chooses
     */
    private void action(TextField userInput) {
        userInput.setOnAction(e -> {
            String name = userInput.getText();
            Main.console.appendText("name " + name);
            userInput.setText("");
            String gameAnswer = Main.game.processAction("name " + name);
            Main.console.appendText( gameAnswer);
        });
    }

    public Node getSelectName(){
        return selectNameScreen;
    }
}
