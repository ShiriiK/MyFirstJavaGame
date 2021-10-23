package gui;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import logic.GameState;
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

    private GameState gameState;
    private HBox hBox = new HBox();
    private FlowPane npcsPanel = new FlowPane();

    //Konstruktor
    public NpcPanel(GameState gameState) {
        this.gameState = gameState;
        init();
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
        Set<Npc> npcSet = gameState.getCurrentLocation().getNpcs();

        for (Npc npc : npcSet) {
            String name = npc.getName();
            ImageView imageView = new ImageView(new Image((GameState.class.getResourceAsStream("/zdroje/" + name + ".png")),
                    110.0,100.0, false, false));
            npcsPanel.getChildren().add(imageView);
        }
    }

    @Override
    public void update() {
        loadNpcs();
    }

    public Node getPanel() {
        return hBox; }
}
