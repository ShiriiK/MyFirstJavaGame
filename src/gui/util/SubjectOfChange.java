package gui.util;

/**
 *  Třídy implementující toho rozhraní odesílají informace o změně stavu jiných objektů, které se u nich zaregistrovali.
 *  <p>
 *  Toto rozhraní je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 *  @author Marcel Valový
 *  * @version ZS -2021, 2021-10-15
 */
public interface SubjectOfChange {

    /**
     * Metoda sloužící k zaregistrování observra.
     *
     * @param observer registrovaný observer
     */
    void registerObserver(Observer observer);

    /**
     * Metoda sloužící k odregistrování observra.
     *
     * @param observer
     */
    void unregisterObserver(Observer observer);

    /**
     * Metoda sloužící k upozornění observra v případě, kdy nastane nějaká změna a je jej potřeba o ní informovat.
     */
    void notifyObservers();

}

