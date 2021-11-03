package gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.*;
import util.Observer;
import java.util.Set;

/**
 * Třída implementující rozhraní Observer.
 * GameAreaPanel zobrazuje top borderPane.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Marcel Valový
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-01
 */

public class GameAreaPanel implements Observer {
    private ItemPanel itemsPanel;
    private RightPanel rightPanel;
    private Game game;
    private BorderPane borderPane = new BorderPane();
    private final TextArea console;

    public GameAreaPanel(Game game, ItemPanel itemsPanel, RightPanel rightPanel, TextArea console) {
        this.game = game;
        this.itemsPanel = itemsPanel;
        this.rightPanel = rightPanel;
        this.console = console;
        loadArea();
        GameState gameState = game.getGameState();
        gameState.registerObserver(this);

        borderPane.setStyle(" -fx-background-color: WHITE;");
        borderPane.setStyle(" -fx-padding: 5;");
    }

    /**
     * Metoda pro nastavení top borderPane v GameBase.
     * Existuje 6 různých obrazovek, které mohou být v top borderPane.
     *  1) výběr pohlaví - kliknutím na tlačítko muž nebo žena si hráč vybere pohlaví
     *  2) výběr rasy - kliknutím na tlačítko temný_elf, barbar, elf, člověk, trpaslík, mág
     *  2) výběr jména - zobrazí se textfield, kam může hráč zadat jméno
     *  3) zobrazení aktuální lokace společně s itemy a npc/zbraněmi v ní
     *  4) souboj s npc
     *  5) komunikace s npc
     */
    private void loadArea() {
        borderPane.getChildren().clear();
        borderPane.setMaxHeight(570.0);
        if (game.getGameState().getPhase() == 0) {
            chooseGenderButtons();
        } else if (game.getGameState().getPlayer().getRace().getName().equals("nic")) {
            selectRace();
        } else if (game.getGameState().getPhase() == 1) {
            chooseNameTextField();
        } else if (game.getGameState().isInCombat()){
            inCombatField();
        } else if (game.getGameState().isInteracting()){
            comunicatingField();
        } else {
            normalScreen();
        }
    }

    /**
     * Metoda pro načtení běžné obrazovky - zahrnuje obrázek aktuální lokace uprostřed, label s jejm názvem nahoře,
     * v pravo jsou pak zobrazeny itemy v aktuální lokaci a v levo npc nebp zbraně v aktální lokaci
     */
    private void normalScreen() {
        Location location = game.getGameState().getCurrentLocation();
        String locationName = location.getName();

        Label locationLabel = new Label("Aktuální lokace: " + locationName);
        locationLabel.setFont(Font.font("Garamond", 30));
        locationLabel.setTextFill(Color.WHITE);

        Tooltip locationTip = new Tooltip(location.getDescription());
        locationTip.setFont(Font.font("Garamond", 30));
        locationLabel.setTooltip(locationTip);

        HBox hBox = new HBox(locationLabel);
        hBox.setAlignment(Pos.CENTER);

        borderPane.setTop(hBox);

        ImageView center = new ImageView(new Image
                (GameState.class.getResourceAsStream("/zdroje/" + locationName + ".jpg"),
                        1000.0, 570.0, false, false));

        borderPane.setCenter(center);
        borderPane.setLeft(itemsPanel.getPanel());
        borderPane.setRight(rightPanel.getPanel());
    }

    /**
     * Metoda pro načtení obrazovky, když hráč komunikuje.
     */
    private void comunicatingField() {
        Player player = game.getGameState().getPlayer();
        ImageView playerImageView;
        if (player.getPlayerGender().equals("female")) {
            playerImageView = new ImageView(new Image
                    (GameState.class.getResourceAsStream("/zdroje/"+ player.getRace().getName() +"_žena.jpg"),
                            900.0, 570.0, false, false));
        } else {
            playerImageView = new ImageView(new Image
                    (GameState.class.getResourceAsStream("/zdroje/"+player.getRace().getName() +"_muž.jpg"),
                            900.0, 570.0, false, false));
        }

        borderPane.setLeft(playerImageView);

        Npc comunicatingNpc = game.getGameState().getComunicatingNpc();
        String comunicatingNpcName = comunicatingNpc.getName();

        ImageView comunicationgNpcImageView;
        comunicationgNpcImageView = new ImageView(new Image
                (GameState.class.getResourceAsStream("/zdroje/" + comunicatingNpcName + ".jpg"),
                        900.0, 570.0, false, false));

        clickOnComunicatingNpc(comunicatingNpcName, comunicationgNpcImageView);

        borderPane.setRight(comunicationgNpcImageView);
    }

    /**
     * Metoda, která vyhodnocuje, jakou akci, chce hráč, během komunikace s npc, provést.
     * @param comunicatingNpcName
     * @param comunicationgNpcImageView
     */
    private void clickOnComunicatingNpc(String comunicatingNpcName, ImageView comunicationgNpcImageView) {
        comunicationgNpcImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //Zobrazí rozhovor s npc
            if (event.getButton() == MouseButton.PRIMARY) {
                console.appendText("\npromluv_si_s "+ comunicatingNpcName + "\n");
                String gameAnswer = game.processAction("promluv_si_s "+ comunicatingNpcName);
                console.appendText("\n" + gameAnswer + "\n");
            }
            //Odchod z obrazovky, kdy hráč komunikuje s npc do normální obrazovky s lokací
            else if (event.getButton() == MouseButton.MIDDLE) {
                game.getGameState().setInteracting(false);

            }
            //Vytvoření nové stage pro zobrazení možnosti výběru itemu, který má být předán
            else {
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Vyber věc");

                Label label = new Label();
                label.setText("Vyber věc, kterou chceš předat nebo zavři okno");
                label.setFont(Font.font("Garamond", FontWeight.BOLD, 25.0));
                label.setTextFill(Color.WHITE);

                ChoiceBox<String> choiceBox = new ChoiceBox<>();
                choiceBox.setStyle( "-fx-font-family: Garamond");
                choiceBox.setStyle("-fx-font-weight: BOLD");
                choiceBox.setStyle("-fx-font-size: 25.0");

                Set<String> inventory = game.getGameState().getInventory().itemsInInventory();
                for (String item : inventory){
                    choiceBox.getItems().add(item);
                }

                Button confirmButton = new Button("Potvrď výběr");
                confirmButton.setFont(Font.font("Garamond", 20));
                confirmButton.setOnAction(e -> {
                    String selected = choiceBox.getValue();

                    console.appendText("\nnabídni " + comunicatingNpcName + " " + selected + "\n");
                    String gameAnswer = game.processAction("nabídni " + comunicatingNpcName + " " + selected);
                    console.appendText("\n" + gameAnswer + "\n");

                    stage.close();
                });

                VBox vBox = new VBox();
                vBox.setSpacing(3.0);
                vBox.setStyle(" -fx-background-color: BLACK;");
                vBox.getChildren().addAll(label, choiceBox, confirmButton);

                Scene scene =  new Scene(vBox);
                stage.setScene(scene);
                stage.showAndWait();
            }
        });
    }

    /**
     * Metoda pro načtení obrazovky, když je hráč v souboji.
     */
    private void inCombatField() {
        //Nastavení obrázku hráče
        Player player = game.getGameState().getPlayer();
        ImageView playerImageView;
        if (player.getPlayerGender().equals("female")) {
            playerImageView = new ImageView(new Image
                    (GameState.class.getResourceAsStream("/zdroje/"+ player.getRace().getName() +"_žena.jpg"),
                            900.0, 470.0, false, false));
        } else {
            playerImageView = new ImageView(new Image
                    (GameState.class.getResourceAsStream("/zdroje/"+player.getRace().getName() +"_muž.jpg"),
                            900.0, 470.0, false, false));
        }

        borderPane.setLeft(playerImageView);

        //Nastavení obrázku npc
        Npc attackedNpc = game.getGameState().getAttackedNpc();
        String attackedNpcName = attackedNpc.getName();

        ImageView attackedNpcImageView;
        attackedNpcImageView = new ImageView(new Image
                (GameState.class.getResourceAsStream("/zdroje/" + attackedNpcName + ".jpg"),
                        900.0, 470.0, false, false));

        ClickOnAttackedNpc(attackedNpcName, attackedNpcImageView);

        borderPane.setRight(attackedNpcImageView);

        Button attack1 = new Button("Normální útok");
        attack1.setFont(Font.font("Garamond", 50));

        Button attack2 = new Button("Útok z dálky");
        attack2.setFont(Font.font("Garamond", 50));

        Button attack3 = new Button();
        attack3.setFont(Font.font("Garamond", 50));

        Button charge = new Button();
        charge.setFont(Font.font("Garamond", 50));

        String race = player.getRace().getName();
        switch (race){
            case "elf":
                attack3.setText("Volání entů");
                charge.setText("Elfí běsnění");
                break;
            case "temný_elf":
                attack3.setText("Pomatení");
                charge.setText("Volání krve");
                break;
            case "barbar":
                attack3.setText("Zuřivý skok");
                charge.setText("Bojový tanec");
                break;
            case "trpaslík":
                attack3.setText("Přivolání blesků");
                charge.setText("Runová bouře");
                break;
            case "člověk":
                attack3.setText("Meč spravedlnosti");
                charge.setText("Modlitba");
                break;
            case "mág":
                attack3.setText("Ohnivá koule");
                charge.setText("Zaklínání");
                break;
        }

        HBox hBox = new HBox();
        hBox.setPrefWidth(1600.0);
        hBox.setPrefHeight(100.0);
        hBox.getChildren().addAll(attack1,attack2,attack3,charge);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15.0);


        clickOnButtonInCombat(player, attackedNpcName, attack1, attack2, attack3, charge);


        borderPane.setBottom(hBox);
    }

    private void clickOnButtonInCombat(Player player, String attackedNpcName, Button attack1, Button attack2, Button attack3, Button charge) {
        attack1.setOnAction(e->{
            console.appendText("\nspeciální_útok "+ player.getRace().getAttack1() + "\n");
            String gameAnswer = game.processAction("speciální_útok "+ player.getRace().getAttack1() + " "+ attackedNpcName);
            console.appendText("\n" + gameAnswer + "\n");
        });
        attack2.setOnAction(e->{
            console.appendText("\nspeciální_útok útok_z_dálky\n");
            String gameAnswer = game.processAction("speciální_útok útok_z_dálky "+ attackedNpcName);
            console.appendText("\n" + gameAnswer + "\n");
        });
        attack3.setOnAction(e->{
            console.appendText("\nspeciální_útok " + player.getRace().getAttack3() + "\n");
            String gameAnswer = game.processAction("speciální_útok " + player.getRace().getAttack3() + " " + attackedNpcName);
            console.appendText("\n" + gameAnswer + "\n");
        });
        charge.setOnAction(e->{
            console.appendText("\nspeciální_útok " + player.getRace().getCharge() + "\n");
            String gameAnswer = game.processAction("speciální_útok " + player.getRace().getCharge() + " " + attackedNpcName);
            console.appendText("\n" + gameAnswer + "\n");
        });
    }

    /**
     * Metoda, kteá vyhodnocuje, jakou akci, chce hráč, během souboje s npc, provést.
     * @param attackedNpcName jméno npc
     * @param attackedNpcImageView obrázek npc, na který hráč kliká
     */
    private void ClickOnAttackedNpc(String attackedNpcName, ImageView attackedNpcImageView) {
        attackedNpcImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //Zaútočí na npc s parťákem
            if (event.getButton() == MouseButton.PRIMARY) {
                console.appendText("\nzaútoč_s_parťákem_na "+ attackedNpcName + "\n");
                String gameAnswer = game.processAction("zaútoč_s_parťákem_na "+ attackedNpcName);
                console.appendText("\n" + gameAnswer + "\n");
            }
            //Parťák použije speciální sílu
            else if (event.getButton() == MouseButton.SECONDARY){
                console.appendText(game.getGameState().getPartner().getPartnerName()
                        + "vyvolává mocnou bouři\n");
                game.getGameState().setBonusDmg(20.0);
            }});
    }

    /**
     * Metoda pro načtení druhé obrazovky, která objeví po výběru pohlaví.
     */
    private void chooseNameTextField() {
        Label label = new Label("Vyber si jméno: ");
        label.setFont(Font.font("Garamond", 70));
        label.setTextFill(Color.WHITE);

        TextField userInput = new TextField();
        userInput.requestFocus();
        userInput.setFont(Font.font("Garamond", 70));
        userInput.setAlignment(Pos.CENTER);


        userInput.setOnAction(e -> {
            String name = userInput.getText();
            console.appendText("jméno " + name);
            userInput.setText("");
            String gameAnswer = game.processAction("jméno " + name);
            console.appendText( gameAnswer);
        });

        VBox vBox = new VBox();
        vBox.setPrefWidth(1000.0);
        vBox.setPrefHeight(570.0);
        vBox.getChildren().addAll(label, userInput);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15.0);
        borderPane.setCenter(vBox);
        vBox.requestFocus();
    }

    /**
     * Metoda pro načtení druhé zobrazené obrazovky s výběrem rasy.
     */
    private void selectRace() {
        Label label = new Label("Vyber si rasu: ");
        label.setFont(Font.font("Garamond", 70));
        label.setTextFill(Color.WHITE);

        Button elf = new Button("elf");
        elf.setFont(Font.font("Garamond", 50));

        Button darkElf = new Button("temný elf");
        darkElf.setFont(Font.font("Garamond", 50));

        Button barbarian = new Button("barbar");
        barbarian.setFont(Font.font("Garamond", 50));

        Button dwarf = new Button("trpaslík");
        dwarf.setFont(Font.font("Garamond", 50));

        Button human = new Button("člověk");
        human.setFont(Font.font("Garamond", 50));

        Button mage = new Button("mág");
        mage.setFont(Font.font("Garamond", 50));

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

        HBox hBox = new HBox();
        hBox.setPrefWidth(1000.0);
        hBox.setPrefHeight(570.0);
        hBox.getChildren().addAll(label,elf,darkElf,barbarian,dwarf,human,mage);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15.0);
        borderPane.setCenter(hBox);
    }

    /**
     * Metoda pro načtení první zobrazené obrazovky s výběrem pohlaví.
     */
    private void chooseGenderButtons() {
        Label label = new Label("Vyber si pohlaví: ");
        label.setFont(Font.font("Garamond", 70));
        label.setTextFill(Color.WHITE);

        Button female = new Button("Žena");
        female.setFont(Font.font("Garamond", 50));

        Button male = new Button("Muž");
        male.setFont(Font.font("Garamond", 50));

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

        VBox vBox = new VBox();
        vBox.setPrefWidth(1000.0);
        vBox.setPrefHeight(570.0);
        vBox.getChildren().addAll(label, female, male);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15.0);
        borderPane.setCenter(vBox);
    }
    
    @Override
    public void update() {
        loadArea();
    }

    public Node getBorderPane() {
        return borderPane;
    }
}
