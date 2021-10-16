package test;

/**
 * Třída představující krok testovacího scénáře.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Jan Říha
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-23
 */

public class Step {
    private String action;
    private String message;
    private String location;
    private String[] exits;
    private String[] invenotry;
    private String[] items;
    private String[] npcs;
    private String[] weapons;
    private String player;
    private String partner;
    private String ending;

    /**
     * Metoda vrátí příkaz, který hra v rámci tohoto kroku provede.
     *
     * @return herní příkaz včetně parametrů
     */
    public String getAction() {
        return action;
    }

    /**
     * Metoda nastaví příkaz, který hra v rámci tohoto kroku provede,
     * a následně celý krok vrátí.
     *
     * @param action herní příkaz včetně parametrů
     * @return tento krok testovacího scénáře
     */
    public Step setAction(String action) {
        this.action = action;
        return this;
    }

    /**
     * Metoda vrátí název lokace, ve které by se měl hráč nacházet po provedení
     * příkazu.
     *
     * @return název aktuální lokace po provedení příkazu
     */
    public String getLocation() {
        return location;
    }

    /**
     * Metoda nastaví název lokace, ve které by se měl hráč nacházet po provedení
     * příkazu, a následně vrátí celý tento krok.
     *
     * @param location název aktuální lokace po provedení příkazu
     * @return tento krok testovacího scénáře
     */
    public Step setLocation(String location) {
        this.location = location;
        return this;
    }

    /**
     * Metoda vrátí zprávu, kterou by hra měla vypsat po provedení příkazu.
     *
     * @return zpráva vypsaná po provedení příkazu
     */
    public String getMessage() {
        return message;
    }

    /**
     * Metoda nastaví zprávu, kterou by hra měla vypsat po provedení příkazu,
     * a následně vrátí celý tento krok.
     *
     * @param message zpráva vypsaná po provedení příkazu
     * @return tento krok testovacího scénáře
     */
    public Step setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Metoda vrátí pole názvů sousedních lokací, do kterých hráč po provedení
     * příkazu může z aktuální lokace odejít.
     *
     * @return pole názvů sousedních lokací
     */
    public String[] getExits() {
        if (exits == null) {
            return null;
        }
        return exits.clone();
    }

    /**
     * Metoda nastaví pole názvů sousedních lokací, do kterých hráč po provedení
     * příkazu může z aktuální lokace odejít, a následně vrátí celý tento krok.
     *
     * @param exits pole názvů sousedních lokací
     * @return tento krok testovacího scénáře
     */
    public Step setExits(String... exits) {
        this.exits = exits.clone();
        return this;
    }

    /**
     * Metoda vrátí pole názvů itemů, které má hráč v inventářit.
     *
     * @return pole názvů itemů
     */
    public String[] getInvenotry() {
        if (invenotry == null) {
            return null;
        }
        return invenotry.clone();
    }

    /**
     * Metoda nastaví pole názvů itemů, které má hráč v inventářit.
     *
     * @param invenotry pole názvů itemů
     * @return tento krok testovacího scénáře
     */
    public Step setInvenotry(String... invenotry) {
        this.invenotry = invenotry.clone();
        return this;
    }

    /**
     * Metoda vrátí pole názvů itemů, které jsou v aktuání lokaci.
     *
     * @return pole názvů itemů
     */
    public String[] getItems() {
        if (items == null) {
            return null;
        }
        return items.clone();
    }

    /**
     * Metoda nastaví pole názvů itemů, které jsou v aktuání lokaci.
     *
     * @param items pole názvů itemů
     * @return tento krok testovacího scénáře
     */
    public Step setItems(String... items) {
        this.items = items.clone();
        return this;
    }

    /**
     * Metoda vrátí pole názvů npc, které jsou v aktuání lokaci.
     *
     * @return pole názvů npc
     */
    public String[] getNpcs() {
        if (npcs == null) {
            return null;
        }
        return npcs.clone();
    }

    /**
     * Metoda nastaví pole názvů npc, které jsou v aktuání lokaci.
     *
     * @param npcs pole názvů itemů
     * @return tento krok testovacího scénáře
     */
    public Step setNpcs(String... npcs) {
        this.npcs = npcs.clone();
        return this;
    }

    /**
     * Metoda vrátí pole názvů zbraní, které jsou v aktuání lokaci.
     *
     * @return pole názvů zbraní
     */
    public String[] getWeapons() {
        if (weapons == null) {
            return null;
        }
        return weapons.clone();
    }

    /**
     * Metoda nastaví pole názvů zbraní, které jsou v aktuání lokaci.
     *
     * @param weapons pole názvů zbraní
     * @return tento krok testovacího scénáře
     */
    public Step setWeapons(String... weapons) {
        this.weapons = weapons.clone();
        return this;
    }

    /**
     * Metoda vrátí informace o aktuálních statech hráče.
     *
     * @return informace o aktuálních statech hráče
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Metoda nastaví informace o aktuálních statech hráče.
     *
     * @param player informace o aktuálních statech hráče
     * @return tento krok testovacího scénáře
     */
    public Step setPlayer(String player) {
        this.player = player;
        return this;
    }

    /**
     * Metoda vrátí informace o aktuálních statech partnera.
     *
     * @return informace o aktuálních statech partnera
     */
    public String getPartner() {
        return partner;
    }

    /**
     * Metoda nastaví informace o aktuálních statech partnera.
     *
     * @param partner informace o aktuálních statech partnera.
     * @return tento krok testovacího scénáře
     */
    public Step setPartner(String partner) {
        this.partner = partner;
        return this;
    }

    /**
     * Metoda vrátí zprávu "Game in progress" pokud hra stále běží a na konci hry zprávu z epilopu,
     * podle toho, jestli hráč vyhrál nebo prohrál.
     *
     * @return zpráva o průběhu hry nebo epilog
     */
    public String getEnding() {
        return ending;
    }

    /**
     * Metoda nastaví informace o aktuálním průběhu hry.
     *
     * @param ending zpráva o průběhu hry nebo epilog
     * @return tento krok testovacího scénáře
     */
    public Step setEnding(String ending) {
        this.ending = ending;
        return this;
    }

    /**
     * Metoda vypíše popis kroku.
     *
     * @return popis kroku.
     */

    public String toStirng() {
        return action +
                "\n" + message +
                "\n\nLocation: " + location +
                "\n\nExits: " + java.util.Arrays.toString(exits) +
                "\n\nInventory: " + java.util.Arrays.toString(invenotry) +
                "\n\nItems: " + java.util.Arrays.toString(items) +
                "\n\nNpcs: " + java.util.Arrays.toString(npcs) +
                "\n\nWeapons: " + java.util.Arrays.toString(weapons) +
                "\n\nPlayer: " + player +
                "\n\nPartner: " + partner +
                "\n\nEnd: " + ending +
                "\n\n\n";
    }
}

