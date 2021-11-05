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
 * @version ZS-2021, 2021-11-02
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
            //Pokud je hráč ve zbrojírně, tak se nezobrazjí v rightPanel npc, ale braně
            if (game.getGameState().getCurrentLocation().getName().equals("zbrojírna")) {
                Set<Weapon> weaponSet = game.getGameState().getCurrentLocation().getWeapons();

                for (Weapon weapon : weaponSet) {
                    String name = weapon.getName();
                    ImageView imageView = new ImageView(new Image((GameState.class.getResourceAsStream("/zdroje/" + name + ".jpg")),
                        200.0, 125.0, false, false));

                    clickOnWeapon(name, imageView);

                    rightPanel.getChildren().add(imageView);
                }
            //Klasické zobrazení npc
            } else {
                Set<Npc> npcSet = game.getGameState().getCurrentLocation().getNpcs();

                for (Npc npc : npcSet) {
                    String name = npc.getName();
                    Boolean friendly = npc.isFriendly();
                    Boolean talk = npc.getTalk();
                    ImageView imageView = new ImageView(new Image((GameState.class.getResourceAsStream("/zdroje/" + name + ".jpg")),
                            200.0, 100.0, false, false));

                    clickOnNpc(name, imageView, friendly, talk);

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
            //Prvotní nastavení zbraně
            if (game.getGameState().getPlayer().getPlayerWeapon() == null) {
                console.appendText("vzemi_si_zbraň " + name);
                String gameAnswer = game.processAction("vzemi_si_zbraň " + name);
                console.appendText(gameAnswer);
            //Výměna zbraně za jinou
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
     * - kliknutí pravým tlačítkem: nastaví interacting na true
     * - kliknutí levý tlačítkem: nastaví combat na true
     *
     * @param name - jméno npc
     * @param imageView - obrázek npc
     */
    private void clickOnNpc(String name, ImageView imageView, Boolean friendly, Boolean talk) {
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //Nastavení interacting
            if (event.getButton() == MouseButton.SECONDARY) {
                if (!talk) {
                    console.appendText("\nS tímto npc toho moc nevykomunikuješ.");
                }
                game.getGameState().setInteractingNpc(name);
                game.getGameState().setInteracting(true);
                console.appendText("\nZačal si komunikovat s " + name + "\n");
            //Nastavení combat
            } else {
                if (friendly) {
                    console.appendText("\nNemá smysl začínat s tímto npc souboj");
                } else {
                    game.getGameState().setAttackedNpc(name);
                    game.getGameState().setInCombat(true);
                    console.appendText("\nZačal si souboj s " + name + "\n");
                }
            }
        });
    }

    @Override
    public void update() {
        loadNpcs();
    }

    public Node getPanel() {
        return hBox; }
}
