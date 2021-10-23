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
import main.GameBase;
import util.Observer;
import java.util.Set;

public class ExitPanel implements Observer {

    private final VBox vbox = new VBox();
    private final FlowPane exitsPanel = new FlowPane();
    private final TextArea console;
    private Game game;


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

    private void loadCurrentExits() {
        exitsPanel.getChildren().clear();
        Set<Location> locationsSet = game.getGameState().getCurrentLocation().getTargetLocations();

        for (Location location : locationsSet) {
            String name = location.getName();
            ImageView imageView = new ImageView(new Image((GameState.class.getResourceAsStream("/zdroje/" + name + ".jpg")),
                    110,100,false, false));

            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                String command = "jít ";
                console.appendText("\n" + command + "\n");
                String gameAnswer = game.processAction(command + name);
                console.appendText("\n" + gameAnswer + "\n");
            });

            exitsPanel.getChildren().add(imageView);
        }
    }


    @Override
    public void update() {
        loadCurrentExits();
    }

    public Node getPanel() {return vbox; }

}

