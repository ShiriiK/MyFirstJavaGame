package logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Instance této třídy představují jednotlivé npc.
 * <p>
 * Tato třída je součástí jednoduché textové adventury.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-26
 */
public class Npc {
    private String name;
    private boolean friendly;
    private double hp;
    private double str;
    private Collection<Item> npcItems;
    private List<String> talks = new ArrayList<>();
    private int talked;
    private boolean talk;
    private String message;


    public Npc(String name, double hp, double str, List talks, String message) {
        this.name = name;
        this.friendly = false;
        this.hp = hp;
        this.str = str;
        this.npcItems = new ArrayList<>();
        this.talks = talks;
        this.talked = 0;
        this.talk = true;
        this.message = message;
    }

    public Npc(String name, List talks, String message) {
        this.name = name;
        this.friendly = true;
        this.npcItems = new ArrayList<>();
        this.talks = talks;
        this.talked = 0;
        this.talk = true;
        this.message = message;
    }

    public Npc(String name, List talks) {
        this.name = name;
        this.friendly = true;
        this.npcItems = new ArrayList<>();
        this.talks = talks;
        this.talked = 0;
        this.talk = true;
    }

    // Jiná verze konstuktoru
    public Npc(String name, double hp, double str, String message) {
        this.name = name;
        this.friendly = false;
        this.hp = hp;
        this.str = str;
        this.npcItems = new ArrayList<>();
        this.talk = false;
        this.message = message;
    }

    public Npc(String name, double hp, double str) {
        this.name = name;
        this.friendly = false;
        this.hp = hp;
        this.str = str;
        this.npcItems = new ArrayList<>();
        this.talk = false;
    }

    /**
     * Metoda pro získání jména npc.
     *
     * @return jméno npc
     */
    public String getName() {
        return name;
    }

    /**
     * Metoda pro získání postvení npc vůči hráči.
     *
     * @return true pokud je to přátelské npc a false, pokud je nepřátelské
     */
    public boolean getFriendly() {
        return friendly;
    }

    /**
     * Metoda pro záskání hp npc.
     *
     * @return hp npc
     */
    public double getHp() {
        return hp;
    }

    /**
     * Metoda pro nastavení hp npc.
     *
     * @param hp npc
     */
    public void setHp(double hp) {
        this.hp = hp;
    }

    /**
     * Metoda pro získání str npc.
     *
     * @return str npc.
     */
    public double getStr() {
        return str;
    }

    /**
     * Metoda pro vložení itemu do npc.
     *
     * @param item který má být do npc přidán
     */
    public void insertItem(Item item) {
        npcItems.add(item);
    }

    /**
     * Metoda pro získání odkazu na item v npc.
     *
     * @param name jméno itemu
     * @return odkaz na item a pokud žádný nemá, tak null
     */
    public Item getItemInNpc(String name) {
        Item item = null;
        for (Item current : npcItems) {
            if (current.getName().equals(name)) {
                item = current;
                break;
            }
        }
        return item;
    }

    /**
     * Metoda pro odstranění itemu z npc.
     *
     * @param name jméno itemu, který má npc
     */
    public void removeItemInNpc(String name) {
        for (Item current : npcItems) {
            if (current.getName().equals(name)) {
                npcItems.remove(current);
                break;
            }
        }
    }

    /**
     * Metoda vrací informaci o tom, zda lze s npc mluvit.
     *
     * @return true pokud lze a false když nelze
     */
    public boolean getTalk() {
        return talk;
    }

    public List<String> getTalks() {
        return talks;
    }

    /**
     * Metoda pro získání části rozhovoru od npc.
     *
     * @return vrací rozhovor podle toho, kolikrát již hráč s npc mluvit
     */
    public String getChat(Npc npc) {
        int i = npc.getTalked();
        for (String talk : talks)
        if (talks.indexOf(talk) == talks.size()-1) {
            return "Kecání už bylo dost";
        }
        talked ++;
        return talks.get(i);
    }


    /**
     * Metoda pro získání informace o tom, kolikrát již hovor s npc proběhl.
     *
     * @return číslo kolikrát se s npc již hovořilo
     */
    public int getTalked() {
        return talked;
    }

    /**
     * Metoda pro natavení hodnoty,kolikrát npc mluvilo.
     *
     * @param talked kolikrát npc mluvilo
     */
    public void setTalked(int talked) {
        this.talked = talked;
    }

    /**
     * Metoda pro získání zprávy, pokud je vstup do lokace nějak omezený.
     *
     * @return zpráva pro hráče
     */
    public String getMessage() {
        return message;
    }
}
