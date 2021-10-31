package gui;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import logic.*;
import util.Observer;
import java.util.Set;

/**
 * Třída implementující rozhraní Observer.
 * NpcPanel zobrazuje npc nacházející se v aktuální lokaci.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-23
 */

public class RightPanel implements Observer {

    private Game game;
    private HBox hBox = new HBox();
    private FlowPane rightPanel = new FlowPane();
    private TextArea console;


    //Konstruktor
    public RightPanel(Game game, TextArea console) {
        this.game = game;
        this.console = console;
        GameState gameState = game.getGameState();
        Location location = gameState.getCurrentLocation();
        init();
        location.registerObserver(this);
        gameState.registerObserver(this);

    }

    private void init() {
        hBox.setPrefWidth(450.0);
        hBox.setPrefHeight(570.0);
        hBox.getChildren().add(rightPanel);

        loadNpcs();
    }

    /**
     * Metoda pro nastavení rightPanel.
      */
    private void loadNpcs() {
        rightPanel.getChildren().clear();

            if (game.getGameState().getCurrentLocation().getName().equals("zbrojírna")) {
                Set<Weapon> weaponSet = game.getGameState().getCurrentLocation().getWeapons();

                for (Weapon weapon : weaponSet) {
                    String name = weapon.getName();
                    ImageView imageView = new ImageView(new Image((GameState.class.getResourceAsStream("/zdroje/" + name + ".jpg")),
                        200.0, 125.0, false, false));

                    clickOnWeapon(name, imageView);

                    rightPanel.getChildren().add(imageView);
                }
            } else {
                Set<Npc> npcSet = game.getGameState().getCurrentLocation().getNpcs();

                for (Npc npc : npcSet) {
                    String name = npc.getName();
                    ImageView imageView = new ImageView(new Image((GameState.class.getResourceAsStream("/zdroje/" + name + ".jpg")),
                            200.0, 100.0, false, false));

                    clickOnNpc(name, imageView);

                    rightPanel.getChildren().add(imageView);
                }
            }
        }

    /**
     * Metoda pro zpracování akce, kdy hráč klikne na obrázek zbraně v lokaci
     * - kliknutí levý tlačítkem: vybere zbraň a když má už hráč zbraň, tak nejdřív tu svou položí a až pak si ji vezme
     *
     * @param name - jméno zbraně
     * @param imageView - obrázek zbraně
     */
    private void clickOnWeapon(String name, ImageView imageView) {
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (game.getGameState().getPlayer().getPlayerWeapon() == null) {
                console.appendText("vzemi_si_zbraň " + name);
                String gameAnswer = game.processAction("vzemi_si_zbraň " + name);
                console.appendText(gameAnswer);
            } else {
                console.appendText("odlož_zbraň");
                String gameAnswer1 = game.processAction("odlož_zbraň");
                console.appendText(gameAnswer1);

                console.appendText("vzemi_si_zbraň " + name);
                String gameAnswer = game.processAction("vzemi_si_zbraň " + name);
                console.appendText(gameAnswer);
            }
        });
    }

    /**
     * Metoda pro zpracování akce, kdy hráč klikne na obrázek npc v lokaci
     * - kliknutí pravým tlačítkem: pokusí se promluvit si s npc
     * - kliknutí levý tlačítkem: pokusí se zaútočit na npc
     *
     * @param name - jméno npc
     * @param imageView - obrázek npc
     */
    private void clickOnNpc(String name, ImageView imageView) {
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            String command = " ";
            if (event.getButton() == MouseButton.SECONDARY) {
                command = "promluv_si_s ";
            } else {
                command = "zaútoč_na ";
            }
            console.appendText("\n" + command + name + "\n");
            String gameAnswer = game.processAction(command + name);
            console.appendText("\n" + gameAnswer + "\n");
        });
    }

    @Override
    public void update() {
        loadNpcs();
    }

    public Node getPanel() {
        return hBox; }
}
