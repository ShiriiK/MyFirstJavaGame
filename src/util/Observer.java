package util;

/**
 *  Třídy implementující toho rozhraní příjmají informace o změně stavu jiných objektů.
 *  <p>
 *  Toto rozhraní je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 *  @author Marcel Valový
 *  * @version ZS -2021, 2021-10-15
 */
public interface Observer {

    /**
     * Reakce na pozorovanou změnu.
     */
    void update();
}

