package gui.screens;

import gui.panels.ItemPanel;
import gui.panels.MenuPanel;
import gui.panels.NpcAndWeaponPanel;
import gui.panels.PlayerPanel;
import gui.util.Constants;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import gui.util.Observer;
import logic.blueprints.Location;
import logic.blueprints.Partner;
import saving_tue.Main;

/**
 * Class implementing the Observer interface.
 * GameAreaPanel sets the display of the mainGameScreen in the top borderPane.
 * @author Alena Kalivodová
 */

public class BaseScreen implements Observer {

    private final ItemPanel itemsPanel;
    private final NpcAndWeaponPanel npcAndWeaponPanel;
    private final ScreenSelectGender selectGender;
    private final ScreenSelectRace selectRace;
    private final ScreenSelectName selectName;
    private final ScreenCombat combat;
    private final ScreenInteracting interacting;
    private final MenuPanel menuBar;
    private final BorderPane gameMainScreen = new BorderPane();

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

        loadArea();

        Main.game.getGameState().registerObserver(this);
    }

    /**
     * Method for setting gameMainScreen.
     * There are 5 different versions of gameMainScreen:
     * 1) gender selection
     * 2) race selection
     * 3) name selection
     * 4) interacting with npc
     * 5) duel with npc
     */
    private void loadArea() {
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

        Tooltip locationTip = new Tooltip(location.getDescription());
        locationLabel.setTooltip(locationTip);

        // Player stats button settings
        Button playerButton = PlayerPanel.getPlayerButton();
        // Setting the button to display partner's stats
        Button partnerButton = getPartnerButton();

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
     * Method for setting the button for the partner
     * @return button for partner
     */
    private Button getPartnerButton() {
        Partner partner = Main.game.getGameState().getPartner();
        Button partnerButton = new Button(partner.getPartnerName());
        partnerButton.getStyleClass().add("bbutton");

        // New stage with stats and partner's picture (+ button to close it)
        partnerButton.setOnAction(e->{
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Partner");

            Label label = new Label(partner.getPartner());
            //label.setFont(Font.font("Garamond",30.0));
            //label.setStyle("-fx-text-fill: WHITE");
            Button close = new Button("Close");
            //close.setFont(Font.font("Garamond",30.0));

            close.setOnAction(event->{
                stage.close();
            });

            BorderPane inPane = new BorderPane();
            inPane.setStyle(" -fx-background-color: BLACK;");
            inPane.setTop(label);
            inPane.setBottom(close);

            ImageView playerImageView = new ImageView(new Image("/npcs/" + partner.getPartnerName() + ".jpg",
                    900.0, 470.0, false, false));

            BorderPane playerPane = new BorderPane();
            playerPane.setStyle("-fx-background-color: BLACK");
            playerPane.setLeft(playerImageView);
            playerPane.setCenter(inPane);

            Scene scene = new Scene(playerPane);
            scene.getStylesheets().add("style.css");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();

        });
        return partnerButton;
    }



    /**
     * Updates gameMainScreen
     */
    @Override
    public void update() {
        loadArea();
    }

    public Node getGameMainScreen() {
        return gameMainScreen;
    }
}
