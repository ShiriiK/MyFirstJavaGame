package gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import logic.Game;
import logic.GameState;
import logic.Location;
import util.Observer;
import java.util.Set;

/**
 * Třída implementující rozhraní Observer.
 * ExitPanel zobrazuje obsah sousední lokace.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Marcel Valový
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-23
 */

public class ExitPanel implements Observer {

    private final VBox vbox = new VBox();
    private final FlowPane exitsPanel = new FlowPane();
    private final TextArea console;
    private Game game;

    //kostruktor
    public ExitPanel(Game game, TextArea console) {
        this.game = game;
        this.console = console;
        GameState gameState = game.getGameState();
        init();
        gameState.registerObserver(this);
    }

    private void init() {
        vbox.setPrefWidth(220);
        Label label = new Label("Sousední lokace: ");
        vbox.getChildren().addAll(label, exitsPanel);

        loadCurrentExits();
    }

    /**
     * Metoda pro nastavení exitsPanel.
     */
    private void loadCurrentExits() {
        exitsPanel.getChildren().clear();
        Set<Location> locationsSet = game.getGameState().getCurrentLocation().getTargetLocations();

        for (Location location : locationsSet) {
            String name = location.getName();
            ImageView imageView = new ImageView(new Image((GameState.class.getResourceAsStream("/zdroje/" + name + ".jpg")),
                    110,100,false, false));

            clickOnExit(name, imageView);

            exitsPanel.getChildren().add(imageView);
        }
    }

    /**
     * Metoda pro zpracování akce, kdy hráč klikne na obrázek sousední lokace
     *
     * @param name jméno lokace
     * @param imageView obrázek lokace
     */
    private void clickOnExit(String name, ImageView imageView) {
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            String command = "jít ";
            console.appendText("\n" + command + "\n");
            String gameAnswer = game.processAction(command + name);
            console.appendText("\n" + gameAnswer + "\n");
        });
    }


    @Override
    public void update() {
        loadCurrentExits();
    }

    public Node getPanel() {return vbox; }

}
