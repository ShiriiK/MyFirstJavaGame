package gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.*;
import util.Observer;

/**
 * Třída implementující rozhraní Observer.
 * GameAreaPanel nastavuje zobrazení mainGameScreen v top borderPane.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Marcel Valový
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-06
 */

public class GameAreaPanel implements Observer {
    private final ItemPanel itemsPanel;
    private final RightPanel rightPanel;
    private final ScreenSelectGender selectGender;
    private final ScreenSelectRace selectRace;
    private final ScreenSelectName selectName;
    private final ScreenCombat combat;
    private final ScreenInteracting interacting;
    private final Game game;
    private final BorderPane gameMainScreen = new BorderPane();


    //Konstruktor
    public GameAreaPanel(Game game, ItemPanel itemsPanel, RightPanel rightPanel, ScreenSelectGender selectGender,
           ScreenSelectRace selectRace, ScreenSelectName selectName, ScreenInteracting interacting,
           ScreenCombat combat) {
        this.game = game;
        this.itemsPanel = itemsPanel;
        this.rightPanel = rightPanel;
        this.selectGender = selectGender;
        this.selectRace = selectRace;
        this.selectName = selectName;
        this.interacting = interacting;
        this.combat = combat;

        gameMainScreen.setStyle(" -fx-background-color: WHITE;");
        gameMainScreen.setStyle(" -fx-padding: 5;");

        loadArea();

        game.getGameState().registerObserver(this);
    }

    /**
     * Metoda pro nastavení gameMainScreen.
     * Existuje 6 různých verzí gameMainScreen:
     *  1) výběr pohlaví
     *  2) výběr rasy
     *  2) výběr jména
     *  3) interakce s npc
     *  4) souboj s npc
     *  5) normální - obrázek lokace uprostřed, obrázky npc/zbraní v pravo, obrázky itemů v levo
     */
    private void loadArea() {
        gameMainScreen.getChildren().clear();
        gameMainScreen.setMaxHeight(570.0);
        if (game.getGameState().getPhase() == 0) {
            gameMainScreen.setCenter(selectGender.getSelectGender());
        } else if (game.getGameState().getPlayer().getRace().getName().equals("nic")) {
            gameMainScreen.setCenter(selectRace.getSelectRace());
        } else if (game.getGameState().getPhase() == 1) {
            gameMainScreen.setCenter(selectName.getSelectName());
        } else if (game.getGameState().isInCombat()){
            gameMainScreen.setLeft(combat.getPlayer());
            gameMainScreen.setRight(combat.getNpc());
            gameMainScreen.setBottom(combat.getButtons());
        } else if (game.getGameState().isInteracting()){
            gameMainScreen.setCenter(interacting.getInteractingScreen());
        } else {
            normalScreen();
        }
    }

    /**
     * Metoda pro nastavení běžné obrazovky.
     */
    private void normalScreen() {
        Location location = game.getGameState().getCurrentLocation();
        String locationName = location.getName();

        Label locationLabel = new Label("Aktuální lokace: " + location.getDisplayName());
        locationLabel.setFont(Font.font("Garamond", 30));
        locationLabel.setTextFill(Color.WHITE);

        Tooltip locationTip = new Tooltip(location.getDescription());
        locationTip.setFont(Font.font("Garamond", 30));
        locationLabel.setTooltip(locationTip);

        HBox hBox = new HBox(locationLabel);
        hBox.setAlignment(Pos.CENTER);

        gameMainScreen.setTop(hBox);

        ImageView center = new ImageView(new Image
                (GameState.class.getResourceAsStream("/zdroje/" + locationName + ".jpg"),
                        1000.0, 570.0, false, false));

        gameMainScreen.setCenter(center);
        gameMainScreen.setLeft(itemsPanel.getPanel());
        gameMainScreen.setRight(rightPanel.getPanel());
    }

    public Node getGameMainScreen() {
        return gameMainScreen;
    }

    @Override
    public void update() {
        loadArea();
    }
}
