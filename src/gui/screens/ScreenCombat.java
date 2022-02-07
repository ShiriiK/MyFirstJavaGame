package gui.screens;

import gui.util.*;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import logic.GameState;
import logic.blueprints.Npc;
import logic.blueprints.Player;
import logic.factories.AttackFactory;
import logic.factories.RaceFactory;
import logic.factories.ToolTipFactory;
import saving_tue.Main;

import java.util.Objects;

/**
 * Class implementing the Observer interface.
 * ScreenCombat sets the display of combatScreen in the top borderPane when displaying combat with npc.
 * @author Alena Kalivodová
 */

public class ScreenCombat implements Observer {

    private final HBox buttons = new HBox();

    private final Button basic = new Button("Basic attack");
    private final Button dodge = new Button("Dodge attack");
    private final Button specialAttack = new Button();
    private final Button charge = new Button();

    private final HBox hBox = new HBox();
    private final BorderPane combatScreen = new BorderPane();

    public ScreenCombat() {
        GameState gameState = Main.game.getGameState();
        gameState.registerObserver(this);
        buttons.getStyleClass().add("combat_buttons");

        if (gameState.isInCombat()){
            init();
        }
    }

    /**
     * Method for setting up combatScreen.
     */
    private void init(){
        buttons.getChildren().clear();
        hBox.getChildren().clear();
        combatScreen.getChildren().clear();

        // Setting ImageVIew of Player
        Player player = Main.game.getGameState().getPlayer();
        ImageView playerImageView = PlayerImageSetting.setPlayersView(2);

        //Setting ImageView of Npc
        Npc npc = Main.game.getGameState().getAttackedNpc();
        ImageView npcImageView = NpcImageSetting.setNpcView(npc.getName(), 2);
        putActionOnImage(Objects.requireNonNull(npcImageView), npc.getName());

        //Setting buttons for combat
        setButtons(player);

        buttons.getChildren().clear();
        buttons.getChildren().addAll(basic, dodge, specialAttack,charge);

        Label roundLabel = new Label("Round: " + Main.game.getGameState().getPlayer().getRound());
        hBox.getChildren().add(roundLabel);
        hBox.setAlignment(Pos.CENTER);
        hBox.setTranslateZ(0);

        action(player, npc.getName(), basic, dodge, specialAttack, charge, playerImageView, npcImageView);

        // Setting combatScreen
        combatScreen.setTop(hBox);
        combatScreen.setLeft(playerImageView);
        combatScreen.setRight(npcImageView);
        combatScreen.setBottom(buttons);
    }

    /**
     * Method for puttin action on image of npc
     * @param npcName jméno npc
     */
    private void putActionOnImage(ImageView npcImageView, String npcName) {

        npcImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            // Attack with partner
            if (event.getButton() == MouseButton.PRIMARY) {
                Main.console.appendText("\npartner_attack "+ npcName + "\n");
                String gameAnswer = Main.game.processAction("partner_attack "+ npcName);
                Main.console.appendText("\n" + gameAnswer + "\n");
            }
            // Special power of partner
            else if (event.getButton() == MouseButton.SECONDARY){
                Main.console.appendText(Main.game.getGameState().getPartner().getPartnerName()
                        + "raises a mighty storm\n");
                Main.game.getGameState().getPlayer().setBonusDmg(30);
            }});
    }

    /**
     * Method for setting buttons.
     */
    private void setButtons(Player player) {
        Tooltip basicTip = new Tooltip("A basic attack that will deal " + Main.game.getGameState().getPlayer().getStr() +
                " damage.\n" + "Plus bonus as a bonus " + Main.game.getGameState().getPlayer().getBonusDmg()  + " damage");
        Tooltip dodgeTip = new Tooltip("Dodge that will block out 20 damage");

        Tooltip.install(basic, basicTip);
        Tooltip.install(dodge, dodgeTip);


        specialAttack.setText(AttackFactory.specialAttacks.get(RaceFactory.getRace(player.getRace().getName()).getName()).getName());
        charge.setText(AttackFactory.chargeAttacks.get(RaceFactory.getRace(player.getRace().getName()).getName()).getName());

        Tooltip.install(specialAttack, ToolTipFactory.specialAttacksTips.get(RaceFactory.getRace(player.getRace().getName())));
        Tooltip.install(charge, ToolTipFactory.chargeTips.get(RaceFactory.getRace(player.getRace().getName())));

    }

    /**
     * A method for handling an action where the player clicks a button during a duel.
     * @param player player
     * @param npcName npc name
     * @param basic button for normal attack
     * @param dodge button for ranged attack
     * @param special button for special attack
     * @param charge button for charged attack
     */
    private void action(Player player, String npcName, Button basic, Button dodge, Button special, Button charge,
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

        basic.setOnAction(e->{
            sequentialTransition.play();
            Main.console.appendText("\nBasic attack\n");
            String gameAnswer = Main.game.processAction("special_attack basic_attack "+ npcName);
            Main.console.appendText("\n" + gameAnswer + "\n");
        });
        dodge.setOnAction(e->{
            sequentialTransition.play();
            Main.console.appendText("\nDodge\n");
            String gameAnswer = Main.game.processAction("special_attack dodge "+ npcName);
            Main.console.appendText("\n" + gameAnswer + "\n");
        });
        special.setOnAction(e->{
            sequentialTransition.play();
            Main.console.appendText("\n" + AttackFactory.specialAttacks.get(RaceFactory.getRace(player.getRace().getName()).getName()).getName() + "\n");
            String gameAnswer = Main.game.processAction("special_attack special_attack " + npcName);
            Main.console.appendText("\n" + gameAnswer + "\n");
        });
        charge.setOnAction(e->{
            sequentialTransition.play();
            Main.console.appendText("\n" + AttackFactory.chargeAttacks.get(RaceFactory.getRace(player.getRace().getName()).getName()).getName() + "\n");
            String gameAnswer = Main.game.processAction("special_attack charge_attack " + npcName);
            Main.console.appendText("\n" + gameAnswer + "\n");
        });
    }

    /**
     * Updates the combat screen (only when the player is in combat)
     */
    @Override
    public void update() {
        if(Main.game.getGameState().isInCombat()){
            init();
        }
    }


    public Node getCombatScreen() {
        return  combatScreen;
    }
}
