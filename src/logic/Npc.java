package logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Instance této třídy představují jednotlivé npc.
 * <p>
 * Toto rozhraní je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version LS-2021, 2021-11-01
 */
public class Npc {
    private String name;
    private boolean friendly;
    private double hp;
    private double str;
    private Collection<Item> npcItems;
    private List<String> talks;
    private int talked;
    private boolean talk;
    private String message;

    /**
     * Kostruktor pro npc
     * @param name jméno npc
     * @param friendly určuje, zda je možné s npc bojovat (true - nejde, false - jde)
     * @param hp životy npc
     * @param str síla npc
     * @param talk určuje, zda je možné s npc mluvit (true - jde, false - nejde)
     * @param talks všechny možné rozhovory s npc
     * @param message zpráva, která je vrácena, když npc nějakým způsobem zabraňuje v přechodu mezi lokacemi
     */
    public Npc(String name, boolean friendly, double hp, double str, boolean talk, List talks, String message) {
        this.name = name;
        this.friendly = friendly;
        this.hp = hp;
        this.str = str;
        this.talk = talk;
        this.talks = talks;
        this.talked = 0;
        this.message = message;
        this.npcItems = new ArrayList<>();
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
    public boolean isFriendly() {
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
     * Metoda pro nastavení str npc
     *
     * @param str
     */
    public void setStr(double str) {
        this.str = str;
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

    /**
     * Metoda pro získání listu všech odpovědí určitého npc.
     * Využívá se pro získání infromace, zda je s npc vůbec možné mluvit.
     *
     * @return list všech odpovědí určitého npc
     */
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
        if (talked == talks.size()) {
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
     * Metoda pro získání zprávy, pokud je vstup do lokace (nebo výtup z ní) nějak omezený.
     *
     * @return zpráva pro hráče
     */
    public String getMessage() {
        return message;
    }
}
