package test;

/**
 * Třída pro uchování výsledků kontrol.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Jan Říha
 * @version LS-2021, 2021-05-23
 */


public class CheckResult {
    private boolean success;
    private String message;

    /**
     * Konstruktor třídy, vytvoří záznam s daným výsledkem a zprávou pro uživatele.
     *
     * @param success příznak, zda kontrola byla úspěšná
     * @param message zpráva pro uživatele
     */
    public CheckResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Metoda vrací příznak, zda kontrola byla úspěšná.
     *
     * @return {@code true}, pokud kontrola byla úspěšná; jinak {@code false}
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Metoda vrací zprávu pro uživatele.
     *
     * @return zpráva pro uživatele
     */
    public String getMessage() {
        return message;
    }
}
