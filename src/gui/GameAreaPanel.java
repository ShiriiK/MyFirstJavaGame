package gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
 * GameAreaPanel zobrazuje top borderPane.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Marcel Valový
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-01
 */

public class GameAreaPanel implements Observer {
    private ItemPanel itemsPanel;
    private RightPanel rightPanel;
    private ScreenSelectGender selectGender;
    private ScreenSelectRace selectRace;
    private ScreenSelectName selectName;
    private ScreenCombat combat;
    private ScreenInteracting interacting;
    private Game game;
    private BorderPane borderPane = new BorderPane();
    private final TextArea console;

    public GameAreaPanel(Game game, ItemPanel itemsPanel, RightPanel rightPanel, ScreenSelectGender selectGender,
                         ScreenSelectRace selectRace, ScreenSelectName selectName, ScreenCombat combat,
                         ScreenInteracting interacting, TextArea console) {
        this.game = game;
        this.itemsPanel = itemsPanel;
        this.rightPanel = rightPanel;
        this.selectGender = selectGender;
        this.selectRace = selectRace;
        this.selectName = selectName;
        this.combat = combat;
        this.interacting = interacting;
        this.console = console;
        loadArea();
        GameState gameState = game.getGameState();
        gameState.registerObserver(this);

        borderPane.setStyle(" -fx-background-color: WHITE;");
        borderPane.setStyle(" -fx-padding: 5;");
    }

    /**
     * Metoda pro nastavení top borderPane v GameBase.
     * Existuje 6 různých obrazovek, které mohou být v top borderPane.
     *  1) výběr pohlaví - kliknutím na tlačítko muž nebo žena si hráč vybere pohlaví
     *  2) výběr rasy - kliknutím na tlačítko temný_elf, barbar, elf, člověk, trpaslík, mág
     *  2) výběr jména - zobrazí se textfield, kam může hráč zadat jméno
     *  3) zobrazení aktuální lokace společně s itemy a npc/zbraněmi v ní
     *  4) souboj s npc
     *  5) komunikace s npc
     */
    private void loadArea() {
        borderPane.getChildren().clear();
        borderPane.setMaxHeight(570.0);
        if (game.getGameState().getPhase() == 0) {
            borderPane.setCenter(selectGender.getSelectGender());
        } else if (game.getGameState().getPlayer().getRace().getName().equals("nic")) {
            borderPane.setCenter(selectRace.getSelectRace());
        } else if (game.getGameState().getPhase() == 1) {
            borderPane.setCenter(selectName.getSelectName());
        } else if (game.getGameState().isInCombat()){
            borderPane.setLeft(combat.getPlayer());
            borderPane.setRight(combat.getNpc());
            borderPane.setBottom(combat.getButtons());
        } else if (game.getGameState().isInteracting()){
            borderPane.setLeft(interacting.getPlayer());
            borderPane.setRight(interacting.getNpc());
        } else {
            normalScreen();
        }
    }

    /**
     * Metoda pro nastavení běžné obrazovky - zahrnuje obrázek aktuální lokace uprostřed, label s jejm názvem nahoře,
     * v pravo jsou pak zobrazeny itemy v aktuální lokaci a v levo npc nebo zbraně v aktální lokaci
     */
    private void normalScreen() {
        Location location = game.getGameState().getCurrentLocation();
        String locationName = location.getName();

        Label locationLabel = new Label("Aktuální lokace: " + locationName);
        locationLabel.setFont(Font.font("Garamond", 30));
        locationLabel.setTextFill(Color.WHITE);

        Tooltip locationTip = new Tooltip(location.getDescription());
        locationTip.setFont(Font.font("Garamond", 30));
        locationLabel.setTooltip(locationTip);

        HBox hBox = new HBox(locationLabel);
        hBox.setAlignment(Pos.CENTER);

        borderPane.setTop(hBox);

        ImageView center = new ImageView(new Image
                (GameState.class.getResourceAsStream("/zdroje/" + locationName + ".jpg"),
                        1000.0, 570.0, false, false));

        borderPane.setCenter(center);
        borderPane.setLeft(itemsPanel.getPanel());
        borderPane.setRight(rightPanel.getPanel());
    }

    @Override
    public void update() {
        loadArea();
    }

    public Node getBorderPane() {
        return borderPane;
    }
}
