package gui.screens;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import logic.Game;

/**
 * SelecctGender nastavuje zobrazení selectGenderScreen v top boraderPane při zobrazení výběru pohlaví.
 * </p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-10
 */

public class ScreenSelectGender {

    private final Game game;
    private final TextArea console;
    private final VBox selectGenderScreen = new VBox();

    //Konstruktor
    public ScreenSelectGender(Game game, TextArea console){
        this.game = game;
        this.console = console;

        init();
    }

    /**
     * Metoda pro nastavení selectGenderScreen.
     *
     */
    private void init() {
        Label label = new Label("Vyber si pohlaví: ");
        label.setFont(Font.font("Garamond"));
        label.setStyle("-fx-font-size: 70.0");


        Button female = new Button("Žena");
        Tooltip femaleTip = new Tooltip("Když si vybereš, že chceš být žena tak:\n" +
                "Budeš mít 80 životů a 30 síly.");
        femaleTip.setFont(Font.font("Garamond", 30));
        Tooltip.install(female, femaleTip);

        Button male = new Button("Muž");;

        Tooltip maleTip = new Tooltip("Když si vybereš, že chceš být muž tak:\n" +
                "Budeš mít 100 životů a 20 síly.");
        maleTip.setFont(Font.font("Garamond", 30));
        Tooltip.install(male, maleTip);

        action(female, male);

        selectGenderScreen.setPrefWidth(1000.0);
        selectGenderScreen.setPrefHeight(570.0);
        selectGenderScreen.setSpacing(15.0);
        selectGenderScreen.setAlignment(Pos.CENTER);
        selectGenderScreen.setId("#vBox-custom");
        selectGenderScreen.getChildren().addAll(label, male, female);
    }

    /**
     * Metoda pro zpracování akce, kdy hráč klikne na button výběru pohlaví.
     * @param female button
     * @param male button
     */
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

    /**
     * @return selectGenderScreen
     */
    public Node getSelectGender(){
        return selectGenderScreen;
    }
}
