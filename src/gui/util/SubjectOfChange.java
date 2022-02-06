package gui.util;

/**
 * Classes implementing this interface send state change information to other objects that have registered with them.
 *  @author Marcel Valový
 */
public interface SubjectOfChange {

    /**
     * Method used to register the observatory.
     * @param observer registered observer
     */
    void registerObserver(Observer observer);

    /**
     * A method used to alert the observer when a change occurs and it needs to be informed.
     */
    void notifyObservers();

}

