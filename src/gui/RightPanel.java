package gui;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
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
 * RightPanel nastavuje zobrazení rightPanel v top borderPane při zobrazení normální obrazovky.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-10
 */

public class RightPanel implements Observer {

    private final Game game;
    private final TextArea console;
    private final HBox rightPanel = new HBox();
    private final FlowPane flowPane = new FlowPane();

    //Konstruktor
    public RightPanel(Game game, TextArea console) {
        this.game = game;
        this.console = console;

        init();

        game.getGameState().registerObserver(this);
        game.getGameState().getCurrentLocation().registerObserver(this);

    }

    /**
     * Metoda pro nastavení rightPanel.
     */
    private void init() {
        rightPanel.getChildren().clear();
        rightPanel.setPrefWidth(450.0);
        rightPanel.setPrefHeight(570.0);
        rightPanel.getChildren().add(flowPane);

        loadRightPanel();
    }

    /**
     * Metoda pro nastavení buďto obrázků npc v lokaci nebo obrázků zbraní v lokaci.
      */
    private void loadRightPanel() {
        flowPane.getChildren().clear();
            //Pokud je hráč ve zbrojírně, tak se nezobrazjí v rightPanel npc, ale braně
            if (game.getGameState().getCurrentLocation().getName().equals("zbrojírna")) {
                Set<Weapon> weaponSet = game.getGameState().getCurrentLocation().getWeapons();

                for (Weapon weapon : weaponSet) {
                    String name = weapon.getName();
                    ImageView imageView = new ImageView(new Image((GameState.class.getResourceAsStream("/zdroje/" + name + ".jpg")),
                        200.0, 125.0, false, false));

                    clickOnWeapon(name, imageView);

                    Tooltip tip = new Tooltip(weapon.getDisplayName() + "\nMultiplikátor: " + weapon.getMultiplicator() +
                            "\nBonus k útoku: " + weapon.getBonusDmg() + "\nBonus k bloku: " + weapon.getBonusBlock() +
                            "\nBonus k speciálnímu útoku: " + weapon.getBonusSpecialAttack() +
                            "\nBonus k charge útoku: " + weapon.getBonusCharge());
                    Tooltip.install(imageView, tip);

                    flowPane.getChildren().add(imageView);
                }
            //Klasické zobrazení npc
            } else {

                Set<Npc> npcSet = game.getGameState().getCurrentLocation().getNpcs();

                for (Npc npc : npcSet) {
                    String name = npc.getName();
                    if(name.equals("tue")) {
                        ImageView imageView = new ImageView(new Image((GameState.class.getResourceAsStream("/zdroje/" + name + ".jpg")),
                                450.0, 250.0, false, false));

                        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                            console.appendText("zachraň_tue");
                            String gameAnswer = game.processAction("zachraň_tue");
                            console.appendText("\n" + gameAnswer + "\n");
                        });

                        Tooltip tip = new Tooltip(npc.getDisplayName());
                        Tooltip.install(imageView, tip);

                        flowPane.getChildren().add(imageView);
                        break;
                    }
                    Boolean friendly = npc.isFriendly();
                    Boolean talk = npc.getTalk();
                    ImageView imageView = new ImageView(new Image((GameState.class.getResourceAsStream("/zdroje/" + name + ".jpg")),
                            450.0, 250.0, false, false));

                    clickOnNpc(name, imageView, friendly, talk);

                    Tooltip tip = new Tooltip(npc.getDisplayName());
                    Tooltip.install(imageView, tip);

                    flowPane.getChildren().add(imageView);
                }
            }
        }

    /**
     * Metoda pro zpracování akce, kdy hráč klikne na obrázek zbraně v lokaci.
     * @param name jméno zbraně
     * @param imageView obrázek zbraně
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
     * Metoda pro zpracování akce, kdy hráč klikne na obrázek npc v lokaci.
     * @param name jméno npc
     * @param imageView obrázek npc
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
                    game.getGameState().setRound(1);
                    game.getGameState().setInCombat(true);
                    console.appendText("\nZačal si souboj s " + name + "\n");
                }
            }
        });
    }

    /**
     * @return rightPanel
     */
    public Node getPanel() {
        return rightPanel; }

    /**
     * Aktualizuje obrázky noc(resp. zbraní) v lokaci
     */
    @Override
    public void update() {
        loadRightPanel();
    }

}
