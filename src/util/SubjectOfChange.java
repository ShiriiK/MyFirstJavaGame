package util;

/**
 *  Toto rozhraní je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 *  @author Marcel Valový
 *  * @version ZS -2021, 2021-10-15
 */
public interface SubjectOfChange {

    void registerObserver(Observer observer);

    void unregisterObserver(Observer observer);

    void notifyObservers();

}

