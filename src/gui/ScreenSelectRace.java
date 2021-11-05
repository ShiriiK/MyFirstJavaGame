package gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.Game;

/**
 * SelecctRace nastavuje tlačítka na výběr rasy
 * </p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-05
 */

public class ScreenSelectRace {

    private Game game;
    private TextArea console;
    private HBox hBox = new HBox();
    private VBox vBox = new VBox();

    //Konstruktor
    public ScreenSelectRace(Game game, TextArea console){
        this.game = game;
        this.console = console;

        selectRace();
    }

    private void selectRace() {
        Label label = new Label("Vyber si rasu: ");
        label.setFont(Font.font("Garamond", 70));
        label.setTextFill(Color.WHITE);

        Button elf = new Button("Elf");
        elf.setFont(Font.font("Garamond", 50));

        Button darkElf = new Button("Temný elf");
        darkElf.setFont(Font.font("Garamond", 50));

        Button barbarian = new Button("Barbar");
        barbarian.setFont(Font.font("Garamond", 50));

        Button dwarf = new Button("Trpaslík");
        dwarf.setFont(Font.font("Garamond", 50));

        Button human = new Button("Člověk");
        human.setFont(Font.font("Garamond", 50));

        Button mage = new Button("Mág");
        mage.setFont(Font.font("Garamond", 50));

        action(elf, darkElf, barbarian, dwarf, human, mage);

        setHBox(elf, darkElf, barbarian, dwarf, human, mage);

        setVBox(label, hBox);
    }

    private void action(Button elf, Button darkElf, Button barbarian, Button dwarf, Button human, Button mage) {
        elf.setOnAction(e ->{
            console.appendText("rasa elf");
            String gameAnswer = game.processAction("rasa elf");
            console.appendText(gameAnswer);
        });
        darkElf.setOnAction(e ->{
            console.appendText("rasa temný_elf");
            String gameAnswer = game.processAction("rasa temný_elf");
            console.appendText(gameAnswer);
        });
        barbarian.setOnAction(e ->{
            console.appendText("rasa barbar");
            String gameAnswer = game.processAction("rasa barbar");
            console.appendText(gameAnswer);
        });
        dwarf.setOnAction(e ->{
            console.appendText("rasa trpaslík");
            String gameAnswer = game.processAction("rasa trpaslík");
            console.appendText(gameAnswer);
        });
        human.setOnAction(e ->{
            console.appendText("rasa člověk");
            String gameAnswer = game.processAction("rasa člověk");
            console.appendText(gameAnswer);
        });
        mage.setOnAction(e ->{
            console.appendText("rasa mág");
            String gameAnswer = game.processAction("rasa mág");
            console.appendText(gameAnswer);
        });
    }

    private void setHBox(Button elf, Button darkElf, Button barbarian, Button dwarf, Button human, Button mage) {
        hBox.setPrefWidth(1000.0);
        hBox.setPrefHeight(100.0);
        hBox.setSpacing(15.0);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(elf, darkElf, barbarian, dwarf, human, mage);
    }

    private void setVBox(Label label, HBox hBox) {
        vBox.setPrefWidth(1000.0);
        vBox.setPrefHeight(570.0);
        vBox.setSpacing(15.0);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, hBox);

    }

    public Node getSelectRace(){
        return vBox;
    }
}
