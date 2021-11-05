package gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.Game;

/**
 * SelectName nastavuje TextField pro výběr jména
 * </p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-05
 */

public class ScreenSelectName {

    private Game game;
    private TextArea console;
    private VBox vBox = new VBox();

    //Konstruktor
    public ScreenSelectName(Game game, TextArea console){
        this.game = game;
        this.console = console;

        selectName();
    }

    private void selectName() {
        Label label = new Label("Vyber si jméno: ");
        label.setFont(Font.font("Garamond", 70));
        label.setTextFill(Color.WHITE);

        TextField userInput = new TextField();
        userInput.requestFocus();
        userInput.setFont(Font.font("Garamond", 50));
        userInput.setAlignment(Pos.CENTER);

        action(userInput);

        ïnit(label, userInput);

    }

    private void action(TextField userInput) {
        userInput.setOnAction(e -> {
            String name = userInput.getText();
            console.appendText("jméno " + name);
            userInput.setText("");
            String gameAnswer = game.processAction("jméno " + name);
            console.appendText( gameAnswer);
        });
    }

    private void ïnit(Label label, TextField userInput) {
        vBox.setPrefWidth(1000.0);
        vBox.setPrefHeight(570.0);
        vBox.setSpacing(15.0);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, userInput);
    }

    public Node getSelectName(){
        return vBox;
    }
}
