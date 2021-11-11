package gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import logic.Game;

/**
 * SelecctRace nastavuje zobrazení selectRaceScreen v top borderPane při výběru jména
 * </p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-06
 */

public class ScreenSelectRace {

    private final Game game;
    private final TextArea console;
    private final HBox buttons = new HBox();
    private final VBox selectRaceScreen = new VBox();

    //Konstruktor
    public ScreenSelectRace(Game game, TextArea console){
        this.game = game;
        this.console = console;

        init();
    }

    /**
     * Metoda pro nastavení selectRaceScreen.
     */
    private void init() {
        Label label = new Label("Vyber si rasu: ");
        label.setStyle("-fx-font-size: 70.0");

        setButtons();

        selectRaceScreen.setPrefWidth(1000.0);
        selectRaceScreen.setPrefHeight(570.0);
        selectRaceScreen.setSpacing(15.0);
        selectRaceScreen.setAlignment(Pos.CENTER);
        selectRaceScreen.getChildren().addAll(label, buttons);
    }

    /**
     * Metoda pro nastavení selectRaceSceen.
     */
    private void setButtons() {
        Tooltip elfTip = new Tooltip("Elf dostane pět bonusových bodů síly a má speciální útoky Volání entů a Elfí běsnění.");
        Tooltip dakrElfTip = new Tooltip("Temný elf dostane pět bonusových bodů síly a má speciální útoky Pomatení a Volání krve.");
        Tooltip barbarianTip = new Tooltip("Barbar dostane pět bonusových bodů života a má speciální útoky Zuřivý skok a Bojový tanec.");
        Tooltip dwarfTip = new Tooltip("Trpaslík dostane pět bonusových bodů života a má speciální útoky Přivolání blesů a Runová bouře.");
        Tooltip humanTip = new Tooltip("Člověk dostane tři bonusové bodůy síly i života a má speciální útoky Meč spravedlnosti a Modlitba.");
        Tooltip mageTip = new Tooltip("Mág dostane tři bonusové bodůy síly i života a má speciální útoky Ohnivá koule a Zaklínání.");


        Button elf = new Button("Elf");
        Tooltip.install(elf,elfTip);

        Button darkElf = new Button("Temný elf");
        Tooltip.install(darkElf,dakrElfTip);

        Button barbarian = new Button("Barbar");
        Tooltip.install(barbarian,barbarianTip);

        Button dwarf = new Button("Trpaslík");
        Tooltip.install(dwarf,dwarfTip);

        Button human = new Button("Člověk");
        Tooltip.install(human,humanTip);

        Button mage = new Button("Mág");
        mage.setFont(Font.font("Garamond", 50));
        Tooltip.install(mage,mageTip);

        action(elf, darkElf, barbarian, dwarf, human, mage);

        buttons.setPrefWidth(1000.0);
        buttons.setPrefHeight(100.0);
        buttons.setSpacing(15.0);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(elf, darkElf, barbarian, dwarf, human, mage);
    }

    /**
     * Metoda pro zpracování akce, kdy si hráč vybírá rasu.
     * @param elf button
     * @param darkElf button
     * @param barbarian button
     * @param dwarf button
     * @param human button
     * @param mage button
     */
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

    /**
     * @return selectRaceScreen
     */
    public Node getSelectRace(){
        return selectRaceScreen;
    }
}
