package gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import logic.Game;

/**
 * SelectName nastavuje zobrazení selectNameScreen v top borderPane při zobrazení výběru jména.
 * </p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-05
 */

public class ScreenSelectName {

    private final Game game;
    private final TextArea console;
    private final VBox selectNameScreen = new VBox();

    //Konstruktor
    public ScreenSelectName(Game game, TextArea console){
        this.game = game;
        this.console = console;

        init();
    }

    /**
     * Metoda pro nastavení selectNameScreen.
     */
    private void init() {
        Label label = new Label("Vyber si jméno: ");
        label.setStyle("-fx-font-size: 70.0");

        TextField userInput = new TextField();
        userInput.setFont(Font.font("Garamond", 50));
        userInput.setAlignment(Pos.CENTER);

        action(userInput);

        selectNameScreen.setPrefWidth(1000.0);
        selectNameScreen.setPrefHeight(570.0);
        selectNameScreen.setSpacing(15.0);
        selectNameScreen.setAlignment(Pos.CENTER);
        selectNameScreen.setId("#vBox-custom");
        selectNameScreen.getChildren().addAll(label, userInput);
    }

    /**
     * Metoda pro zpracování akce, kdy si hráč vybírá jméno.
     * @param userInput jméno, které si hráč vybere
     */
    private void action(TextField userInput) {
        userInput.setOnAction(e -> {
            String name = userInput.getText();
            console.appendText("jméno " + name);
            userInput.setText("");
            String gameAnswer = game.processAction("jméno " + name);
            console.appendText( gameAnswer);
        });
    }

    /**
     * @return selectName
     */
    public Node getSelectName(){
        return selectNameScreen;
    }
}
