package gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import logic.Game;
import logic.GameState;
import logic.Npc;
import logic.Player;
import util.Observer;

/**
 * ScreenCombat nastavuje ImageView hráče a npc, se kterým bojuje, a buttny pro souboj
 * </p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 *  * @author Alena Kalivodová
 *  * @version ZS-2021, 2021-11-05
 */

public class ScreenCombat implements Observer {

    private Game game;
    private TextArea console;
    private ImageView playerImageView = new ImageView();
    private ImageView npcImageVIew = new ImageView();
    private HBox hBox = new HBox();
    private Button attack1 = new Button("Normální útok");
    private Button attack2 = new Button("Útok z dálky");
    private Button attack3 = new Button();
    private Button charge = new Button();

    //Konstruktor
    public ScreenCombat(Game game, TextArea console) {
        this.game = game;
        this.console = console;
        GameState gameState = game.getGameState();

        gameState.registerObserver(this);

        if (gameState.isInCombat()){
            init();
        }
    }


    private void init(){
        //Nastavení ImageVIew hráče
        Player player = game.getGameState().getPlayer();
        setPlayerImageView(player);

        //Nastavení ImageView npc
        Npc npc = game.getGameState().getAttackedNpc();
        String npcName = npc.getName();
        setNpcImageView(npcName);

        //Nastavení tlačítek na souboj
        SetButtons(player);

        prepareHBox();

        action(player, npcName, attack1, attack2, attack3, charge);
    }

    /**
     * Nastavení ImageView hráče
     * @param player hráč
     */
    private void setPlayerImageView(Player player) {
        Image playerImage;
        if (player.getPlayerGender().equals("female")) {
            playerImage = new Image("/zdroje/"+ player.getRace().getName() +"_žena.jpg",
                    900.0, 470.0, false, false);
        } else {
            playerImage = new Image ("/zdroje/"+ player.getRace().getName() +"_muž.jpg",
                    900.0, 470.0, false, false);
        }
        playerImageView.setImage(playerImage);
    }

    /**
     * Nastavení ImageView npc
     * @param npcName jméno npc
     */
    private void setNpcImageView(String npcName) {
        Image npcImage = new Image
                ("/zdroje/" + npcName + ".jpg", 900.0, 470.0, false, false);

        npcImageVIew.setImage(npcImage);

        npcImageVIew.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //Zaútočí na npc s parťákem
            if (event.getButton() == MouseButton.PRIMARY) {
                console.appendText("\nzaútoč_s_parťákem_na "+ npcName + "\n");
                String gameAnswer = game.processAction("zaútoč_s_parťákem_na "+ npcName);
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
     * Nastavení buttnů
     * @param player hráč
     */
    private void SetButtons(Player player) {
        StyleIt();


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
    }

    private void StyleIt() {
        attack1.setFont(Font.font("Garamond", 50));
        attack2.setFont(Font.font("Garamond", 50));
        attack3.setFont(Font.font("Garamond", 50));
        charge.setFont(Font.font("Garamond", 50));
    }

    private void action(Player player, String npcName, Button attack1, Button attack2, Button attack3, Button charge) {
        attack1.setOnAction(e->{
            console.appendText("\nspeciální_útok "+ player.getRace().getAttack1() + "\n");
            String gameAnswer = game.processAction("speciální_útok "+ player.getRace().getAttack1() + " "+ npcName);
            console.appendText("\n" + gameAnswer + "\n");
        });
        attack2.setOnAction(e->{
            console.appendText("\nspeciální_útok útok_z_dálky\n");
            String gameAnswer = game.processAction("speciální_útok útok_z_dálky "+ npcName);
            console.appendText("\n" + gameAnswer + "\n");
        });
        attack3.setOnAction(e->{
            console.appendText("\nspeciální_útok " + player.getRace().getAttack3() + "\n");
            String gameAnswer = game.processAction("speciální_útok " + player.getRace().getAttack3() + " " + npcName);
            console.appendText("\n" + gameAnswer + "\n");
        });
        charge.setOnAction(e->{
            console.appendText("\nspeciální_útok " + player.getRace().getCharge() + "\n");
            String gameAnswer = game.processAction("speciální_útok " + player.getRace().getCharge() + " " + npcName);
            console.appendText("\n" + gameAnswer + "\n");
        });
    }

    private void prepareHBox() {
        hBox.getChildren().clear();
        hBox.setPrefWidth(1600.0);
        hBox.setPrefHeight(100.0);
        hBox.getChildren().addAll(attack1,attack2,attack3,charge);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15.0);
    }

    public Node getPlayer(){
        return playerImageView;
    }

    public Node getNpc(){
        return npcImageVIew;
    }

    public Node getButtons(){
        return hBox;
    }

    @Override
    public void update() {
        if(game.getGameState().isInCombat()){
            init();
        }
    }
}
