package gui.screens;

import gui.panels.*;
import gui.util.Constants;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import gui.util.Observer;
import logic.blueprints.Location;
import saving_tue.Main;

/**
 * Class implementing the Observer interface.
 * GameAreaPanel sets the display of the mainGameScreen in the top borderPane.
 * @author Alena Kalivodová
 */

public class BaseScreen implements Observer {

    private final BorderPane gameMainScreen = new BorderPane();
    private final ItemPanel itemsPanel;
    private final NpcAndWeaponPanel npcAndWeaponPanel;
    private final ScreenSelectGender selectGender;
    private final ScreenSelectRace selectRace;
    private final ScreenSelectName selectName;
    private final ScreenCombat combat;
    private final ScreenInteracting interacting;
    private final MenuPanel menuBar;

    public BaseScreen(ItemPanel itemsPanel, NpcAndWeaponPanel npcAndWeaponPanel, ScreenSelectGender selectGender,
                      ScreenSelectRace selectRace, ScreenSelectName selectName, ScreenInteracting interacting,
                      ScreenCombat combat, MenuPanel menuBar) {
        this.itemsPanel = itemsPanel;
        this.npcAndWeaponPanel = npcAndWeaponPanel;
        this.selectGender = selectGender;
        this.selectRace = selectRace;
        this.selectName = selectName;
        this.interacting = interacting;
        this.combat = combat;
        this.menuBar = menuBar;

        setUpTopBoarderPane();

        Main.game.getGameState().registerObserver(this);
    }

    /**
     * Method for setting gameMainScreen.
     * There are 6 different versions of gameMainScreen:
     * 1) gender selection
     * 2) race selection
     * 3) name selection
     * 4) interacting with npc
     * 5) combat with npc
     * 6) normal game
     */
    private void setUpTopBoarderPane() {
        gameMainScreen.getChildren().clear();
        gameMainScreen.setTop(menuBar.getMenuBar());
        gameMainScreen.setMaxHeight(570.0);
        // Gender selection
        if (Main.game.getGameState().getPhase() == 0) {
            gameMainScreen.setCenter(selectGender.getSelectGender());
        }
        // Race selection
        else if (Main.game.getGameState().getPhase() == 1) {
            gameMainScreen.setCenter(selectRace.getSelectRace());
        }
        // Name selection
        else if (Main.game.getGameState().getPhase() == 2) {
            gameMainScreen.setCenter(selectName.getSelectName());
        }
        // Combat
        else if (Main.game.getGameState().isInCombat()){
            gameMainScreen.setCenter(combat.getCombatScreen());
        }
        // Interacting
        else if (Main.game.getGameState().isInteracting()){
            gameMainScreen.setCenter(interacting.getInteractingScreen());
        }
        // Normal screen
        else {
            normalScreen();
        }
    }

    /**
     * A method for setting up a normal screen.
     */
    private void normalScreen() {
        Location location = Main.game.getGameState().getCurrentLocation();

        // Setting the name of the current location
        Label locationLabel = new Label("Current location: " + location.getDisplayName());
        locationLabel.setStyle("-fx-font-size: 30.0");

        // Setting tooptip on location name
        Tooltip locationTip = new Tooltip(location.getDescription());
        locationLabel.setTooltip(locationTip);

        // Button for displaying stage with player stats and image
        Button playerButton = PlayerStatsPanel.getPlayerButton();

        // Button for displaying stage with partners stats and image
        Button partnerButton = PartnerStatsPanel.getPartnerButton();

        // Setting the topPane layout
        BorderPane topPane = new BorderPane();
        topPane.setLeft(playerButton);
        topPane.setCenter(locationLabel);
        topPane.setRight(partnerButton);

        // Setting the image of the current location
        ImageView locationImageView = new ImageView(new Image("/locations/" + location.getName() + ".jpg",
                Constants.CURRENT_LOCATION_WIDTH, Constants.CURRENT_LOCATION_HEIGHT, false, false, true));

        VBox vBox = new VBox(topPane, locationImageView);
        gameMainScreen.setCenter(vBox);
        gameMainScreen.setLeft(itemsPanel.getPanel());
        gameMainScreen.setRight(npcAndWeaponPanel.getPanel());
    }

    /**
     * Updates gameMainScreen
     */
    @Override
    public void update() {
        setUpTopBoarderPane();
    }

    public Node getGameMainScreen() {
        return gameMainScreen;
    }
}
