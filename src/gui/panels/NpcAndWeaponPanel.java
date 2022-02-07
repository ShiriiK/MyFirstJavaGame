package gui.panels;

import gui.util.Constants;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import gui.util.Observer;
import logic.blueprints.Npc;
import logic.blueprints.Weapon;
import saving_tue.Main;

/**
 * Class implementing the Observer interface.
 * NpcAndWeaponPanel sets the display of the npcAndWeaponPanel in the top borderPane when the normal screen is displayed.
 * @author Alena Kalivodová
 */

public class NpcAndWeaponPanel implements Observer {

    private final HBox rightPanel = new HBox();
    private final FlowPane flowPane = new FlowPane();

    public NpcAndWeaponPanel() {
        Main.game.getGameState().registerObserver(this);
        Main.game.getGameState().getCurrentLocation().registerObserver(this);

        init();
    }

    /**
     * Method for setting up rightPanel.
     */
    private void init() {
        rightPanel.getChildren().clear();
        rightPanel.getStyleClass().add("top_panels");
        rightPanel.getChildren().add(flowPane);

        loadRightPanel();
    }

    /**
     * Method for setting either npc images in a location or weapon images in a location.
      */
    private void loadRightPanel() {
        flowPane.getChildren().clear();
            // Armory display
            if (Main.game.getGameState().getCurrentLocation().getName().equals("armory")) {
                for (Weapon weapon : Main.game.getGameState().getCurrentLocation().getWeapons()) {

                    // Finding image
                    ImageView imageView = new ImageView(new Image("/weapons/" + weapon.getName() + ".jpg",
                            Constants.TOP_PICS_WIDTH, Constants.TOP_PICS_HEIGHT, false, false, true));

                    // Installing tip
                    Tooltip tip = new Tooltip(weapon.getDisplayName() +
                            "\nMultiplier: " + weapon.getMultiplicator() +
                            "\nAttack Bonus: " + weapon.getBonusDmg() +
                            "\nBlock Bonus: " + weapon.getBonusBlock() +
                            "\nSpecial Attack Bonus: " + weapon.getBonusSpecialAttack() +
                            "\nCharge Attack Bonus: " + weapon.getBonusCharge());
                    Tooltip.install(imageView, tip);

                    // Putting action on image of weapon
                    clickOnWeapon(weapon.getName(), imageView);

                    flowPane.getChildren().add(imageView);
                }
            // Every other location
            } else {
                for (Npc npc : Main.game.getGameState().getCurrentLocation().getNpcs()) {
                    ImageView npcView;

                    // For Tue
                    if(npc.getName().equals("tue")) {
                        npcView = new ImageView(new Image("/npcs/" + npc.getName() + ".jpg",
                                Constants.NPCS_WIDTH, Constants.NPCS_HEIGHT, false, false, true));

                        // If it's Tue put action for rescue
                        npcView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                            Main.console.appendText("rescue_tue");
                            String gameAnswer = Main.game.processAction("rescue_tue");
                            Main.console.appendText("\n" + gameAnswer + "\n");
                        });
                    // For evey other npc
                    } else {
                        npcView = new ImageView(new Image("/npcs/" + npc.getName() + ".jpg",
                                Constants.NPCS_WIDTH, Constants.NPCS_HEIGHT, false, false, true));

                        // Put actions on image of npc
                        clickOnNpc(npc.getName(), npcView, npc.isFriendly(), npc.getTalk());
                    }

                    // Installing tip
                    Tooltip tip = new Tooltip(npc.getDisplayName());
                    Tooltip.install(npcView, tip);

                    flowPane.getChildren().add(npcView);
                }
            }
        }

    /**
     * A method for processing an action when player clicks on an image of weapon in a location.
     */
    private void clickOnWeapon(String name, ImageView weaponImageView) {
        weaponImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            // Initial weapon setup
            if (Main.game.getGameState().getPlayer().getPlayerWeapon() == null) {
                Main.console.appendText("take_weapon " + name);
                String gameAnswer = Main.game.processAction("take_weapon " + name);
                Main.console.appendText(gameAnswer);
            // Swapping a weapons
            } else {
                Main.console.appendText("drop_weapon");
                String gameAnswer1 = Main.game.processAction("drop_weapon");
                Main.console.appendText(gameAnswer1);

                Main.console.appendText("take_weapon " + name);
                String gameAnswer = Main.game.processAction("take_weapon " + name);
                Main.console.appendText(gameAnswer);
            }
        });
    }

    /**
     * A method for processing an action when player clicks on an image of npc in a location.
     */
    private void clickOnNpc(String name, ImageView imageView, Boolean friendly, Boolean talk) {
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            // Interacting settings
            if (event.getButton() == MouseButton.SECONDARY) {
                if (!talk) {
                    Main.console.appendText("\nYou can't interact with this npc.");
                }
                Main.game.getGameState().setInteractingNpc(name);
                Main.game.getGameState().setInteracting(true);
                Main.console.appendText("\nYou started interacting with " + name + "\n");
            // Combat settings
            } else {
                if (friendly) {
                    Main.console.appendText("\nNo point in starting a fight with this npc.");
                } else {
                    Main.game.getGameState().setAttackedNpc(name);
                    Main.game.getGameState().getPlayer().setRound(1);
                    Main.game.getGameState().setInCombat(true);
                    Main.console.appendText("\nYou started a fight with " + name + "\n");
                }
            }
        });
    }

    /**
     * Updates images of the npcs (or weapons) in the location
     */
    @Override
    public void update() {
        loadRightPanel();
    }

    public Node getPanel() {
        return rightPanel;
    }
}
