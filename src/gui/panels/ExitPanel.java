package gui.panels;

import gui.screens.BaseScreen;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.Game;
import logic.blueprints.Location;
import gui.util.Observer;
import saving_tue.Main;

import java.util.Set;

/**
 * Třída implementující rozhraní Observer.
 * ExitPanel nastavuje zobrazení exitsPanel v right borderPane.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Marcel Valový
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-10
 */

public class ExitPanel implements Observer {

    private final Game game = Main.game;
    private final TextArea console = Main.console;
    private final VBox exitsPanel = new VBox();
    private final FlowPane flowPane = new FlowPane();
    private final BaseScreen baseScreen;
    private final BorderPane borderPane;
    private double opacity = 1;

    //kostruktor
    public ExitPanel(BorderPane borderPane, BaseScreen baseScreen) {
        this.borderPane = borderPane;
        this.baseScreen = baseScreen;

        init();

        game.getGameState().registerObserver(this);
    }

    /**
     * Metoda pro nastavení exitsPanel.
     */
    private void init() {
        Label label = new Label("Sousední lokace: ");

        exitsPanel.getChildren().addAll(label, flowPane);
        exitsPanel.setPrefWidth(500.0);
        exitsPanel.setPrefHeight(400.0);
        exitsPanel.setAlignment(Pos.TOP_CENTER);

        loadCurrentExits();
    }

    /**
     * Metoda pro nastavení obrázků sousedních lokací.
     */
    private void loadCurrentExits() {
        if (game.getGameState().getPhase() >= 2) {
            flowPane.getChildren().clear();
            Set<Location> locationsSet = game.getGameState().getCurrentLocation().getTargetLocations();

            for (Location location : locationsSet) {
                String locationName = location.getName();
                ImageView imageView = new ImageView(new Image("/pics/" + locationName + ".jpg",
                        250, 100, false, false,true));

                clickOnExit(locationName, imageView);

                Tooltip tip = new Tooltip(location.getDisplayName());
                Tooltip.install(imageView, tip);

                flowPane.getChildren().add(imageView);
            }
        }
    }

    /**
     * Metoda pro zpracování akce, kdy hráč klikne na obrázek sousední lokace.
     * @param locationName jméno lokace
     * @param imageView obrázek lokace
     */
    private void clickOnExit(String locationName, ImageView imageView) {
        AnimationTimer animationTimer = getAnimationTimer();

        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            animationTimer.start();
            console.appendText("\njdi " + locationName + "\n");
            String gameAnswer = game.processAction("jdi " + locationName);
            console.appendText("\n" + gameAnswer + "\n");
        });
    }

    /**
     * Metoda pro nastavení animace loading screen při přechodu mezi lokacemi
     * @return
     */
    private AnimationTimer getAnimationTimer() {
        ImageView loading = new ImageView(new Image
                ("/pics/loading.gif", 1000.0,600.0,
                        false, false,true));

        HBox loadingBox = new HBox(loading);
        loadingBox.setAlignment(Pos.CENTER);

        AnimationTimer animationTimer = new AnimationTimer()
        {
            @Override
            public void handle(long now) {
                doHandle();
            }

            private void doHandle() {
                borderPane.setTop(loadingBox);
                opacity -= 0.03;
                loading.opacityProperty().set(opacity);

                if (opacity <= 0){
                    opacity = 1;
                    stop();
                    borderPane.setTop(baseScreen.getGameMainScreen());
                }
            }
        };
        return animationTimer;
    }

    /**
     * @return exitsPanel
     */
    public Node getPanel() {return exitsPanel; }

    /**
     * Metoda pro aktualizaci východů z lokace
     */
    @Override
    public void update() {
        loadCurrentExits();
    }
}

