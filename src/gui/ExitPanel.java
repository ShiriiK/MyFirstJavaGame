package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import logic.Exit;
import logic.GameState;
import logic.Location;
import util.Observer;

public class ExitPanel implements Observer {

    GameState gameState;
    ListView<String> listView = new ListView<>();
    ObservableList<String> exits = FXCollections.observableArrayList();

    public ExitPanel(GameState gameState) {
        this.gameState = gameState;

        init();

        gameState.registerObserver(this);
    }

    private void init() {
        loadCurrentExits();

        listView.setItems(exits);
        listView.setPrefWidth(100.0);
    }

    public Node getListView() {return listView; }

    @Override
    public void update() {
        loadCurrentExits();
    }

    private void loadCurrentExits() {
        Location currentLocation = gameState.getCurrentLocation();
        this.exits.clear();
        for (Exit exit : currentLocation.getExits()) {
            exits.add(exit.getTargetLocation().getName());
        }
    }
}

