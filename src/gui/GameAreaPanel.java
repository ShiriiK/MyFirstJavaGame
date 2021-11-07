package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
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
        gameMainScreen.setTop(prepareMenu());
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

    private MenuBar prepareMenu() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("Soubor");
        Menu helpMenu = new Menu("Nápověda");

        ImageView icon = new ImageView(new Image(GameAreaPanel.class.getResourceAsStream("/zdroje/icon.jpg"),
                40.0,25.0,false, true));

        MenuItem newGame = new MenuItem("Nová hra", icon);
        newGame.setAccelerator(KeyCombination.keyCombination("CTRL+N"));
        MenuItem end = new MenuItem("Konec");
        MenuItem about = new MenuItem("O aplikace");
        MenuItem help = new MenuItem("Nápověda");

        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
        fileMenu.getItems().addAll(newGame,separatorMenuItem,end);
        helpMenu.getItems().addAll(about,help);

        menuBar.getMenus().addAll(fileMenu,helpMenu);

        return  menuBar;
    }

    /**
     * Metoda pro nastavení běžné obrazovky.
     */
    private void normalScreen() {
        Location location = game.getGameState().getCurrentLocation();
        String locationName = location.getName();

        Label locationLabel = new Label("Aktuální lokace: " + location.getDisplayName());
        locationLabel.setStyle("-fx-font-size: 30.0");

        Tooltip locationTip = new Tooltip(location.getDescription());
        locationLabel.setTooltip(locationTip);

        HBox hBox = new HBox(locationLabel);
        hBox.setAlignment(Pos.CENTER);

        ImageView center = new ImageView(new Image
                (GameState.class.getResourceAsStream("/zdroje/" + locationName + ".jpg"),
                        1000.0, 570.0, false, false));

        VBox vBox = new VBox(hBox, center);
        gameMainScreen.setCenter(vBox);
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
