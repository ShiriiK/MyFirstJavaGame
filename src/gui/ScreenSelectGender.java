package gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.Game;

/**
 * SelecctGender nastavuje tlačítka na výběr pohlaví
 * </p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-05
 */

public class ScreenSelectGender {

    private Game game;
    private TextArea console;
    private VBox vBox = new VBox();

    //Konstruktor
    public ScreenSelectGender(Game game, TextArea console){
        this.game = game;
        this.console = console;

        selectGender();
    }

    private void selectGender() {
        Label label = new Label("Vyber si pohlaví: ");
        label.setFont(Font.font("Garamond", 70));
        label.setTextFill(Color.WHITE);

        Button female = new Button("Žena");
        female.setFont(Font.font("Garamond", 50));

        Button male = new Button("Muž");
        male.setFont(Font.font("Garamond", 50));

        action(female, male);

        init(label, female, male);
    }

    private void action(Button female, Button male) {
        female.setOnAction(e -> {
            console.appendText("pohlaví žena");
            String gameAnswer = game.processAction("pohlaví žena");
            console.appendText(gameAnswer);
        });
        male.setOnAction(e-> {
            console.appendText("pohlaví muž");
            String gameAnswer = game.processAction("pohlaví muž");
            console.appendText(gameAnswer);
        });
    }

    private void init(Label label, Button female, Button male) {
        vBox.setPrefWidth(1000.0);
        vBox.setPrefHeight(570.0);
        vBox.setSpacing(15.0);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, male, female);
    }

    public Node getSelectGender(){
        return vBox;
    }
}
