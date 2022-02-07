package gui.panels;

import gui.screens.BaseScreen;
import gui.util.Constants;
import gui.util.Observer;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.blueprints.Location;
import saving_tue.Main;

/**
 * Class implementing the Observer interface.
 * ExitPanel sets up displayed exits in the right borderPane.
 * @author Marcel Valový
 * @author Alena Kalivodová
 */

public class ExitPanel implements Observer {

    private final VBox exitsPanel = new VBox();
    private final FlowPane flowPane = new FlowPane();
    private final BaseScreen baseScreen;
    private final BorderPane borderPane;
    private double opacity = 1;

    public ExitPanel(BorderPane borderPane, BaseScreen baseScreen) {
        this.borderPane = borderPane;
        this.baseScreen = baseScreen;

        Main.game.getGameState().registerObserver(this);

        init();
    }

    /**
     * Method for setting up exitsPanel.
     */
    private void init() {
        Label label = new Label("Nearby locations: ");

        flowPane.setVgap(5);
        flowPane.setHgap(5);

        exitsPanel.getChildren().addAll(label, flowPane);
        exitsPanel.getStyleClass().add("bottom_panels");

        loadCurrentExits();
    }

    /**
     * Method for putting images of nearby locations into exitsPanel.
     */
    private void loadCurrentExits() {
        if (Main.game.getGameState().getPhase() >= 3) {
            flowPane.getChildren().clear();

            for (Location location : Main.game.getGameState().getCurrentLocation().getTargetLocations()) {

                // Finding image
                ImageView imageView = new ImageView(new Image("/locations/" + location.getName() + ".jpg",
                        Constants.BOTTOM_PICS_WIDTH, Constants.BOTTOM_PICS_HEIGHT, false, false, true));

                // Installing tip
                Tooltip tip = new Tooltip(location.getDisplayName());
                Tooltip.install(imageView, tip);

                // Putting action on location image
                putActionOnExit(location.getName(), imageView);

                flowPane.getChildren().add(imageView);
            }
        }
    }

    /**
     * A method for processing an action when player clicks on a picture of a location.
     */
    private void putActionOnExit(String locationName, ImageView locationImageView) {
        // Gif that appears while changing location
        AnimationTimer animationTimer = exitAnimation();

        locationImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            animationTimer.start();
            Main.console.appendText("\ngo " + locationName + "\n");
            String gameAnswer = Main.game.processAction("go " + locationName);
            Main.console.appendText("\n" + gameAnswer + "\n");
        });
    }

    /**
     * Method for setting loading screen animation when changing locations
     */
    private AnimationTimer exitAnimation() {
        ImageView loading = new ImageView(new Image("/other/loading.gif", Constants.GIF_WIDTH, Constants.GIF_HEIGHT,
                        false, false, true));

        HBox loadingBox = new HBox(loading);
        loadingBox.setAlignment(Pos.CENTER);

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                doHandle();
            }

            private void doHandle() {
                borderPane.setTop(loadingBox);
                opacity -= 0.03;
                loading.opacityProperty().set(opacity);

                if (opacity <= 0) {
                    opacity = 1;
                    stop();
                    borderPane.setTop(baseScreen.getGameMainScreen());
                }
            }
        };
        return animationTimer;
    }

    /**
     * Method for updating location exits
     */
    @Override
    public void update() {
        loadCurrentExits();
    }

    public Node getPanel() {
        return exitsPanel;
    }
}

