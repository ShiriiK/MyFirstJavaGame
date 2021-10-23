package gui;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import logic.Game;
import logic.GameState;
import logic.Location;
import logic.Npc;
import util.Observer;

import java.util.Set;

/**
 * Třída implementující rozhraní Observer.
 * NpcPanel zobrazuje npc nacházející se v aktuální lokaci.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-23
 */

public class NpcPanel implements Observer {

    private Game game;
    private HBox hBox = new HBox();
    private FlowPane npcsPanel = new FlowPane();
    private TextArea console = new TextArea();


    //Konstruktor
    public NpcPanel(Game game, TextArea console) {
        this.game = game;
        this.console = console;
        GameState gameState = game.getGameState();
        Location location = gameState.getCurrentLocation();
        init();
        location.registerObserver(this);
        gameState.registerObserver(this);

    }

    private void init() {
        hBox.setPrefWidth(220.0);
        hBox.getChildren().add(npcsPanel);

        loadNpcs();
    }

    /**
     * Metoda pro nastavení npcPanel.
      */
    private void loadNpcs() {
        npcsPanel.getChildren().clear();
        Set<Npc> npcSet = game.getGameState().getCurrentLocation().getNpcs();

        for (Npc npc : npcSet) {
            String name = npc.getName();
            ImageView imageView = new ImageView(new Image((GameState.class.getResourceAsStream("/zdroje/" + name + ".png")),
                    110.0,100.0, false, false));

            clickOnNpc(name, imageView);

            npcsPanel.getChildren().add(imageView);
        }
    }

    /**
     * Metoda pro zopracování akce, kdy hráč klikne na obrázek npc v lokaci
     * - kliknutí pravým tlačítkem: pokusí se promluvit si s npc
     * - kliknutí levý tlačítkem: pokusí se zaútočit na npc
     *
     * @param name
     * @param imageView
     */
    private void clickOnNpc(String name, ImageView imageView) {
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            String command = " ";
            if (event.getButton() == MouseButton.SECONDARY) {
                command = "promluv_si_s ";
            } else {
                command = "zaútoč_na ";
            }
            console.appendText("\n" + command + name + "\n");
            String gameAnswer = game.processAction(command + name);
            console.appendText("\n" + gameAnswer + "\n");
        });
    }

    @Override
    public void update() {
        loadNpcs();
    }

    public Node getPanel() {
        return hBox; }
}
