package gui.screens;

import gui.util.Constants;
import logic.factories.ToolTipFactory;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import saving_tue.Main;

/**
 * SelectGender sets the display of selectGenderScreen in the top boraderPane when displaying the gender selection.
 * @author Alena Kalivodová
 */

public class ScreenSelectGender {

    private final VBox selectGenderScreen = new VBox();

    public ScreenSelectGender(){
        selectGenderScreen.setPrefWidth(Constants.SELECTION_WIDTH);
        selectGenderScreen.setPrefHeight(Constants.SELECTION_HEIGHT);
        selectGenderScreen.getStyleClass().add("screen");

        init();
    }

    /**
     * Method for setting selectGenderScreen.
     */
    private void init() {
        Label label = new Label("Choose your gender: ");
        label.setStyle("-fx-font-size: 70.0");

        Button female = new Button("Female");
        Button male = new Button("Male");;

        Tooltip.install(female, ToolTipFactory.femaleTip);
        Tooltip.install(male, ToolTipFactory.maleTip);

        action(female, male);

        selectGenderScreen.getChildren().addAll(label, male, female);
    }

    /**
     * Method for processing an action where the player clicks on the gender selection button.
     * @param female button
     * @param male button
     */
    private void action(Button female, Button male) {
        female.setOnAction(e -> {
            Main.console.appendText("gender female");
            String gameAnswer = Main.game.processAction("gender female");
            Main.console.appendText(gameAnswer);
        });


        male.setOnAction(e-> {
            Main.console.appendText("gender male");
            String gameAnswer = Main.game.processAction("gender male");
            Main.console.appendText(gameAnswer);
        });

    }

    public Node getSelectGender(){
        return selectGenderScreen;
    }
}
