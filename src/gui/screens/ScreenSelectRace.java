package gui.screens;

import gui.util.Constants;
import gui.util.ToolTipFactory;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.blueprints.Race;
import logic.factories.RaceFactory;
import saving_tue.Main;

/**
 * SelecctRace sets the display of selectRaceScreen in the top borderPane when selecting a name.
 * @author Alena Kalivodová
 */

public class ScreenSelectRace {

    private final HBox buttons = new HBox();
    private final VBox selectRaceScreen = new VBox();

    public ScreenSelectRace(){
        selectRaceScreen.setPrefWidth(Constants.SELECTION_WIDTH);
        selectRaceScreen.setPrefHeight(Constants.SELECTION_HEIGHT);
        selectRaceScreen.getStyleClass().add("screen");

        init();
    }

    /**
     * Method for setting selectRaceScreen.
     */
    private void init() {
        Label label = new Label("Choose race: ");
        label.setStyle("-fx-font-size: 70.0");

        setButtons();

        selectRaceScreen.getChildren().addAll(label, buttons);
    }

    /**
     * Method for setting selectRaceSceen.
     */
    private void setButtons() {
        for (Race race : RaceFactory.races){

            // Creating button with name of the race
            Button raceButton = new Button(race.getName());

            // Finding tooltip for given race and installing it on button
            ToolTipFactory.raceTips.forEach((key, value) -> {
                if (key.equals(race)){
                    ToolTipFactory.raceTips.remove(this);
                    Tooltip.install(raceButton, value);
                }});

            // Adding action for setting race on each button
            raceButton.setOnAction(event -> {
                Main.console.appendText("race " + raceButton.getText().toLowerCase());
                String gameAnswer = Main.game.processAction("race " + raceButton.getText().toLowerCase());
                Main.console.appendText(gameAnswer);
            });

            // Putting button to HBox
            buttons.getChildren().add(raceButton);
        }

        buttons.getStyleClass().add("race_selection");
       }

    public Node getSelectRace(){
        return selectRaceScreen;
    }
}
