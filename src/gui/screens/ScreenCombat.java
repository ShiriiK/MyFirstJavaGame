package gui.screens;

import gui.util.Constants;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import logic.Game;
import logic.GameState;
import logic.blueprints.Npc;
import logic.blueprints.Player;
import gui.util.Observer;
import saving_tue.Main;

/**
 * Třída implementující rozhraní Observer.
 * ScreenCombat nastavuje zobrazení combatScreen v top borderPane při zobrazení souboje s npc.
 * @author Alena Kalivodová
 */

public class ScreenCombat implements Observer {

    private final HBox buttons = new HBox();
    private final Button melee = new Button("Normální útok");
    private final Button ranged = new Button("Útok s úskokem");
    private final Button specialAttacck = new Button();
    private final Button charge = new Button();
    private final HBox hBox = new HBox();
    private final BorderPane combatScreen = new BorderPane();

    //Konstruktor
    public ScreenCombat() {

        GameState gameState = Main.game.getGameState();
        gameState.registerObserver(this);

        if (gameState.isInCombat()){
            init();
        }
    }

    /**
     * Metoda pro nastavení combatScreen.
     */
    private void init(){
        buttons.getChildren().clear();
        hBox.getChildren().clear();
        combatScreen.getChildren().clear();

        //Nastavení ImageVIew hráče
        Player player = Main.game.getGameState().getPlayer();
        ImageView playerImageView = setPlayerImageView(player);

        //Nastavení ImageView npc
        Npc npc = Main.game.getGameState().getAttackedNpc();
        String npcName = npc.getName();
        ImageView npcImageView = setNpcImageView(npcName);

        //Nastavení tlačítek na souboj
        setButtons(player);

        buttons.getChildren().clear();
        buttons.getStyleClass().add("combat_buttons");

        buttons.getChildren().addAll(melee, ranged, specialAttacck,charge);

        Label roundLabel = new Label("Kolo číslo: " + Main.game.getGameState().getPlayer().getRound());
        hBox.getChildren().add(roundLabel);
        hBox.setAlignment(Pos.CENTER);
        hBox.setTranslateZ(0);

        action(player, npcName, melee, ranged, specialAttacck, charge, playerImageView, npcImageView);

        combatScreen.setTop(hBox);
        combatScreen.setLeft(playerImageView);
        combatScreen.setRight(npcImageView);
        combatScreen.setBottom(buttons);
    }

    /**
     * Metoda pro nastavení ImageView hráče.
     * @param player hráč
     */
    private ImageView setPlayerImageView(Player player) {
        ImageView playerImageView;
        String race = player.getRace().getName();
        if (player.getPlayerGender().equals("žena")) {
            playerImageView = new ImageView(new Image("/pics/" + race +"_žena.jpg",
                    Constants.COMBAT_PICS_WIDTH, Constants.COMBAT_PICS_HEIGHT, false, false));
        } else {
            playerImageView = new ImageView(new Image("/pics/" + race +"_muž.jpg",
                    Constants.COMBAT_PICS_WIDTH, Constants.COMBAT_PICS_HEIGHT, false, false));
        }
        return playerImageView;
    }

    /**
     * Metoda pro nastavení ImageView npc.
     * @param npcName jméno npc
     * @return
     */
    private ImageView setNpcImageView(String npcName) {
        ImageView npcImageView = new ImageView(new Image("/pics/" + npcName + ".jpg",
                Constants.COMBAT_PICS_WIDTH, Constants.COMBAT_PICS_HEIGHT, false, false));

        npcImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //Zaútočí na npc s parťákem
            if (event.getButton() == MouseButton.PRIMARY) {
                Main.console.appendText("\nzaútoč_s_parťákem_na "+ npcName + "\n");
                String gameAnswer = Main.game.processAction("zaútoč_s_parťákem_na "+ npcName);
                Main.console.appendText("\n" + gameAnswer + "\n");
            }
            //Parťák použije speciální sílu
            else if (event.getButton() == MouseButton.SECONDARY){
                Main.console.appendText(Main.game.getGameState().getPartner().getPartnerName()
                        + "vyvolává mocnou bouři\n");
                Main.game.getGameState().getPlayer().setBonusDmg(30.0);
            }});
        return npcImageView;
    }

    /**
     * Metoda pro nastavení buttnů.
     * @param player hráč
     */
    private void setButtons(Player player) {
        Tooltip meleeTip = new Tooltip("Útok z blízka, který dá " + player.getStr() + " poškození \n" +
                "plus bonusové poškození, pokud tedy nějaké máš.");
        Tooltip.install(melee, meleeTip);
        Tooltip rangedTip = new Tooltip("Útok z blízka, který dá " + (player.getStr()/2) + " poškození \n" +
                "plus bonusové poškození, pokud tedy nějaké máš, a nastaví blok na 20 poškození.");
        Tooltip.install(ranged,rangedTip);


        Tooltip specialTip = new Tooltip("");
        Tooltip chargeTip = new Tooltip("");

        String race = player.getRace().getName();
        switch (race){
            case "elf":
                specialAttacck.setText("Volání entů");
                specialTip.setText("Dá v základu 35 poškození.");
                charge.setText("Elfí běsnění");
                chargeTip.setText("Zvýší blok o 50.");
                break;
            case "temný_elf":
                specialAttacck.setText("Pomatení");
                specialTip.setText("Zvýší blok o 50.");
                charge.setText("Volání krve");
                chargeTip.setText("Nastaví bonusové poškození na 40.");
                break;
            case "barbar":
                specialAttacck.setText("Zuřivý skok");
                specialTip.setText("Dá v základu 50 poškození.");
                charge.setText("Bojový tanec");
                chargeTip.setText("Nastaví bonusové poškození na 70 a zvýší příští poškození, které utrpíš o 10.");
                break;
            case "trpaslík":
                specialAttacck.setText("Přivolání blesků");
                specialTip.setText("Dá v základu 40 poškození.");
                charge.setText("Runová bouře");
                chargeTip.setText("Dá v základu 80 poškození a zvýší příští poškození, které utrpíš o 10.");
                break;
            case "člověk":
                specialAttacck.setText("Meč spravedlnosti");
                specialTip.setText("Dá v základu 40 poškození.");
                charge.setText("Modlitba");
                chargeTip.setText("Nastaví bonusové poškození na 40 a zvýší blok o 40.");
                break;
            case "mág":
                specialAttacck.setText("Ohnivá koule");
                specialTip.setText("Dá v základu 60 poškození.");
                charge.setText("Zaklínání");
                chargeTip.setText("Nastaví bonusové poškození na 30 a zvýší blok o 30.");
                break;
        }
        Tooltip.install(specialAttacck, specialTip);
        Tooltip.install(charge, chargeTip);
    }

    /**
     * Metoda pro zpracování akce, kdy hráč klikne na button během souboje.
     * @param player hráč
     * @param npcName jméno npc
     * @param attack1 button na normální útok
     * @param attack2 button na útok z dálky
     * @param attack3 button na speciální útok
     * @param charge button na charged útok
     */
    private void action(Player player, String npcName, Button attack1, Button attack2, Button attack3, Button charge,
                        ImageView playerImageView, ImageView npcImageVIew) {
        TranslateTransition playerTransition = new TranslateTransition();
        playerTransition.setDuration(Duration.seconds(0.5));
        playerTransition.setByX(500);
        playerTransition.setByY(0);
        playerTransition.setAutoReverse(true);
        playerTransition.setCycleCount(2);
        playerTransition.setNode(playerImageView);

        TranslateTransition npcTransition = new TranslateTransition();
        npcTransition.setDuration(Duration.seconds(0.5));
        npcTransition.setByX(-500);
        npcTransition.setByY(0);
        npcTransition.setAutoReverse(true);
        npcTransition.setCycleCount(2);
        npcTransition.setNode(npcImageVIew);

        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.seconds(0.5));
        rotateTransition.setCycleCount(2);
        rotateTransition.setNode(hBox);


        SequentialTransition sequentialTransition = new SequentialTransition(rotateTransition,playerTransition,npcTransition);

        attack1.setOnAction(e->{
            sequentialTransition.play();
            Main.console.appendText("\nÚtok z blízka\n");
            String gameAnswer = Main.game.processAction("speciální_útok útok_z_blízka "+ npcName);
            Main.console.appendText("\n" + gameAnswer + "\n");
        });
        attack2.setOnAction(e->{
            sequentialTransition.play();
            Main.console.appendText("\nÚtok s úskokem\n");
            String gameAnswer = Main.game.processAction("speciální_útok útok_s_úskokem "+ npcName);
            Main.console.appendText("\n" + gameAnswer + "\n");
        });
        attack3.setOnAction(e->{
            sequentialTransition.play();
            Main.console.appendText("\nspeciální_útok " + player.getRace().getSpecialAttack() + "\n");
            String gameAnswer = Main.game.processAction("speciální_útok " + player.getRace().getSpecialAttack() + " " + npcName);
            Main.console.appendText("\n" + gameAnswer + "\n");
        });
        charge.setOnAction(e->{
            sequentialTransition.play();
            Main.console.appendText("\nspeciální_útok " + player.getRace().getCharge() + "\n");
            String gameAnswer = Main.game.processAction("speciální_útok " + player.getRace().getCharge() + " " + npcName);
            Main.console.appendText("\n" + gameAnswer + "\n");
        });
    }

    /**
     * @return combatScreen
     */
    public Node getCombatScreen() {
        return  combatScreen;
    }

    /**
     * Aktualizuje soubojovou obrazovku (jen pokud je hráč v souboji)
     */
    @Override
    public void update() {
        if(Main.game.getGameState().isInCombat()){
            init();
        }
    }
}