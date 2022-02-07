package logic;

import gui.util.Observer;
import gui.util.SubjectOfChange;
import logic.blueprints.*;
import logic.factories.LocationFactory;
import logic.factories.matching.InsertingItems;
import logic.factories.matching.InsertingNpcs;
import logic.factories.matching.MatchingExitsAndLocations;
import java.util.HashSet;
import java.util.Set;


/**
 * Class representing the game state and implementing the SubjectOfChange interface
 * Iniacializes locations, items, npcs, weapons and exits.
 *
 * @author Alena Kalivodová
 */

public class GameState implements SubjectOfChange {
    private Location currentLocation;
    private final Inventory inventory;
    private final Player player;
    private final Partner partner;
    private int phase;
    private boolean isInteracting;
    private Npc interactingNpc;
    private boolean inCombat;
    private Npc attackedNpc;

    private final Set<Observer> observers = new HashSet<>();

    /**
     * Constructor - initializes the game, creates a new player, partner, inventory, sets the game phase and a few other things.
     */
    public GameState() {
        createGame();
        inventory = new Inventory();
        //Race playersRace = new Race("none", null, null);

        player = new Player(null, null, null, 1, 0, null);
        partner = new Partner(null, null, 1, 0);

        phase = 0;

        isInteracting = false;
        interactingNpc = new Npc(null,null,false,0,0,false, null, null);

        inCombat = false;
        attackedNpc = new Npc(null,null,false,0,0,false, null, null);
    }

    /**
     * Initialization of locations, items, npcs, weapons and exits
     */
    private void createGame() {
        InsertingItems.insertItems();
        InsertingNpcs.insertNpcs();
        MatchingExitsAndLocations.matchExitsAndLocations();

        setCurrentLocation(LocationFactory.hidden_field);
    }

    /**
     * Method to get a link to the current location.
     * @return link to current location
     */
    public Location getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Method for setting the current location.
     * @param currentLocation location that will be set as the new current location
     */
    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
        setInteracting(false);
        setInteractingNpc(null);
        notifyObservers();
    }

    /**
     * Method for getting a link to a partner.
     * @return link to partner
     */
    public Partner getPartner() {
        return partner;
    }

    /**
     * Method for getting a link to a player.
     * @return link to player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Method for getting a reference to the inventory.
     * @return link to inventory
     */
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * A method for getting what phase the game is in.
     * The game is divided into phases:
     * 0 - no gender set;
     * 1 - no race set;
     * 2 - no name set;
     * 3 - player does not have a weapon;
     * 4 - normal playing;
     * 5 - unavailable phase;
     * @return phase number
     */
    public int getPhase() {
        return phase;
    }

    /**
     * Method for setting the phase.
     * @param phase 0,1,2 or 3
     */
    public void setPhase(int phase) {
        this.phase = phase;
        notifyObservers();
    }

    /**
     * Method for setting whether the player is fighting.
     * @param inCombat true - player fights, false - player does not fight
     */
    public void setInCombat(boolean inCombat) {
        this.inCombat = inCombat;
        notifyObservers();
    }

    /**
     * A method for determining if a player is in contention.
     * @return true - is, false - is not
     */
    public boolean isInCombat() {
        return inCombat;
    }

    /**
     * Method for setting the npc the player is fighting.
     * @param attackedNpcName npc name
     */
    public void setAttackedNpc(String attackedNpcName) {
        this.attackedNpc = getCurrentLocation().getNpc(attackedNpcName);
    }

    /**
     * Method for returning npc's that the player is struggling with
     * @return link to npc
     */
    public Npc getAttackedNpc(){
        return attackedNpc;
    }

    /**
     * Method for setting whether the player interacting.
     * @param isInteracting true - yes, false - no
     */
    public void setInteracting(boolean isInteracting) {
        this.isInteracting = isInteracting;
        notifyObservers();
    }

    /**
     * A method of determining whether a player is interacting.
     * @return true - yes, false - no
     */
    public boolean isInteracting() {
        return isInteracting;
    }

    /**
     * Method for setting the npc the player is communicating with.
     * @param interactingNpc npc
     */
    public void setInteractingNpc(String interactingNpc) {
        this.interactingNpc = getCurrentLocation().getNpc(interactingNpc);
    }

    /**
     * Method for returning a link to the npc with which the player should communicate.
     * @return npc
     */
    public Npc getInteractingNpc(){
        return interactingNpc;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }
}
