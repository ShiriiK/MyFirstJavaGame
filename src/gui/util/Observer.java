package gui.util;

/**
 *  Classes implementing this interface receives information about state changes of other objects.
 *  @author Marcel Valový
 */
public interface Observer {

    /**
     * The method in which the observatory is updated.
     * It is called via the notifyObserver() method from the classes implementing the SubjectOfChange interface.
     */
    void update();
}

