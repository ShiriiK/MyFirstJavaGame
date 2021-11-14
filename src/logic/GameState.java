package logic;

import util.Observer;
import util.SubjectOfChange;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Třída představující stav hry a implementující rozhraní SubjectOfChange
 * <p>
 * Iniacializuje lokace, itemy, npc, zbraně a exity.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-10
 */

public class GameState implements SubjectOfChange {
    private Location currentLocation;
    private final Inventory inventory;
    private final Set<Race> races;
    private final Player player;
    private final Partner partner;
    private int phase;
    private boolean isInteracting;
    private Npc interactingNpc;
    private boolean inCombat;
    private Npc attackedNpc;
    private int round;
    private double negetedDmg;
    private double bonusDmg;
    private boolean usedAttack3;
    private boolean usedCharge;
    private Location targetLocation;

    private final Set<Observer> observers = new HashSet<>();

    /**
     * Konstruktor - inicializuje hru, vytvoří nového hráče, partnera, inventář, nastaví fázi hry  a pár dalších věcí.
     */
    public GameState() {
        createGame();
        inventory = new Inventory();
        Race playersRace = new Race("nic", null, null);
        player = new Player(null, null, null, 20.0, 0.0, playersRace);
        partner = new Partner(null, null, 20.0, 0.0);
        phase = 0;
        isInteracting = false;
        interactingNpc = new Npc(null,null,false,0.0,0.0,false, null, null);
        inCombat = false;
        attackedNpc = new Npc(null,null,false,0.0,0.0,false, null, null);
        round = 0;
        negetedDmg = 0.0;
        bonusDmg = 0.0;
        usedAttack3 = false;
        usedCharge = false;

        Race elf = new Race("elf","volání_entů", "elfí_běsnění");
        Race dark_elf = new Race("temný_elf","pomatení", "volání_krve");
        Race barbarian = new Race("barbar", "zuřivý_skok", "bojový_tanec");
        Race dwarf = new Race("trpaslík","přivolání_blesků", "runová_bouře");
        Race human = new Race("člověk","meč_spravedlnosti", "modlitba");
        Race mage = new Race("mág","ohnivá_koule", "zaklínání");

        races = Stream.of(elf,dark_elf,barbarian,dwarf,human,mage).collect(Collectors.toSet());
    }

    /**
     * Incializace lokací, itemů, npc, zbraní a exitů
     */
    private void createGame() {
        // Vytvoření jednotlivých lokací
        Location hidden_field = new Location("skrytá_louka", "Skytá louka",
                "Skrytá louka na kterou jste narazili s přáteli během průzkumu lesa před pár měsíci," +
                        "je chráněna silnoumagickou bariérou, takže se na ní žádná monstra z lesa nedostanou. " +
                        "Uprostřed této louky stojí velký, dobře vybavený dům s malou kovárnou opodál.", 2);
        Location home = new Location("dům", "Dům", "Hlavní společenská místnost v domě.",2);
        Location dining_room = new Location("jídelna", "Jídelna","Prostorná jídelna.", 2);
        Location room = new Location("pokoj", "Pokoj","Tvůj pokoj.", 2);
        Location forge = new Location("kovárna","Kovárna",
                "Gormino oblíbené místo. Když ji budeš potřebovat, tak ji najdeš tady.",2);
        Location armory = new Location("zbrojírna", "Zbrojírna","Tady si můžeš vybrat svou zbraň.",2);
        Location forest = new Location("les","Les",
                "Temný nebezpečný les, někde v něm se ztratil jeden z tvých parťáků, Thorfinn." +
                "Bohužel jeho záchrana musí počkat, než dostanete Tue z vězení.",3);
        Location mountain = new Location("hora","Hora",
                "Oblast pod horami, které jste před pár měsíci přesli. Byla to těžká cesta, která měla ještě " +
                "pokračovat. Bohužel zastávka, kterou mělo blízké město představovat, se prodloužila zatím na neurčito.",3);
        Location lake = new Location("jezero","Jezero",
                "Jezero na západ od tábora. Ukázalo se být dobrým zdrojem jídla.",3);
        Location alley = new Location("alej", "Alej","Alej vedoucí k městské bráně. Jediným problémem je, " +
                "že se tu začal potulovat obrovský troll, kterého je třeba se zbavit, než půjdete k bráně.",3);
        Location gate = new Location("brána", "Brána","Přísně střežená brána a jediná cesta, která vede do města." +
                "Stráž: Zastavte prosím a ukažte mi doklady o povolení k vstupu, pokud je nemáte, nemohu vás pustit dovnitř.",3);
        Location city = new Location("město","Město",
                "Město, které zmítá bída a hlad. Rozdíl v životních úrovní lidí je tady astronomický." +
                        "A štěstí se zase otočilo proti tobě, což sis uvědomil ve chvíli, kdy na tebe zakřičel vrchní generál." +
                        "Generál: Vy dva se zastavte. Kdo jste a co tu chcete." +
                        "///Nyní máš dvě možnosti. Dát mu něco, co by mohlo utišit jeho zvědavost, nebo bojovat.///",3);
        Location ghetto = new Location("ghetto","Ghetto",
                "Chudá část města a zároveň ta největší. Není moc důvodů, proč tu zůstávat déle, než je nutné.",3);
        Location street = new Location("ulice", "Ulice","Ulice vedoucí na hlavní nádvoří, kam se někdo jako ty nedostane..",3);
        Location coutyard = new Location("nádvoří", "Nádvoří","",4);
        Location entrence = new Location("vchod", "Vchod","Vchod do pozdemního vězení, kde je Tue držena.",3);
        Location dungeon = new Location("žalář","Žalář",
                "Podzemní žalář. Je tu velká tma, ale pochodeň pomáhá vidět alespoň pár kroků dopředu."
                ,3);
        Location cell1 = new Location("cela_na_levo", "Cela na levo","Malá cela, ve které jsou jen krysy.",3);
        Location cell2 = new Location("cela_uprostřed", "Cela uprostřed","Poměrně velká cela s mnoha tmavými zákoutími",3);
        Location cell3 = new Location("cela_na_pravo","Cela v pravo",
                "Malá nechutná cela, v níž na zemi leží téměř bezvládné tělo Tue.",3);

        //Přiřazení exitu k lokaci
        Exit hiddne_fieldExit = new Exit(hidden_field);
        Exit dining_roomExit = new Exit(dining_room);
        Exit roomExit = new Exit(room);
        Exit alleyExit = new Exit(alley);
        Exit forestExit = new Exit(forest);
        Exit lakeExit = new Exit(lake);
        Exit mountainExit = new Exit(mountain);
        Exit forgeExit = new Exit(forge);
        Exit homeExit = new Exit(home);
        Exit armoryExit = new Exit(armory);
        Exit dungeonExit = new Exit(dungeon);
        Exit ghettoExit = new Exit(ghetto);
        Exit passageExit = new Exit(street);
        Exit entrenceExit = new Exit(entrence);
        Exit gateExit = new Exit(gate);
        Exit cityExit = new Exit(city);
        Exit coutyardExit = new Exit(coutyard);
        Exit cell1Exit = new Exit(cell1);
        Exit cell2Exit = new Exit(cell2);
        Exit cell3Exit = new Exit(cell3);

        //Přiřazení východů z jednotlivých lokací
        alley.addExit(hiddne_fieldExit);
        alley.addExit(gateExit);
        hidden_field.addExit(alleyExit);
        hidden_field.addExit(forestExit);
        hidden_field.addExit(lakeExit);
        hidden_field.addExit(mountainExit);
        hidden_field.addExit(homeExit);
        hidden_field.addExit(forgeExit);
        forge.addExit(hiddne_fieldExit);
        forge.addExit(armoryExit);
        armory.addExit(forgeExit);
        home.addExit(hiddne_fieldExit);
        home.addExit(dining_roomExit);
        home.addExit(roomExit);
        room.addExit(homeExit);
        dining_room.addExit(homeExit);
        cell1.addExit(dungeonExit);
        cell2.addExit(dungeonExit);
        city.addExit(ghettoExit);
        city.addExit(passageExit);
        city.addExit(entrenceExit);
        city.addExit(gateExit);
        gate.addExit(alleyExit);
        gate.addExit(cityExit);
        forest.addExit(hiddne_fieldExit);
        ghetto.addExit(cityExit);
        lake.addExit(hiddne_fieldExit);
        mountain.addExit(hiddne_fieldExit);
        street.addExit(cityExit);
        street.addExit(coutyardExit);
        entrence.addExit(cityExit);
        entrence.addExit(dungeonExit);
        dungeon.addExit(cell1Exit);
        dungeon.addExit(cell2Exit);
        dungeon.addExit(cell3Exit);
        dungeon.addExit(entrenceExit);

        //Nastavení počáteční lokace
        currentLocation = hidden_field;
        targetLocation = forge;

        //Vytvoření npcček
        Npc general = new Npc("generál", "Vrchní generál",false, 150.0, 20.0, true, Arrays.asList(
                "Generl: Kdo jste!",
                "General: Pokud nechcete zatěžovat mou mysl svými odpornými jmény, můžete místo toho zatížit mou peněženku.",
                "General: Zabijte je, už jsem s nimi ztratil víc než dost času."),
                "Králův vrchní generál tě nenechá odejít. Musíte s ním jednat.");
        Npc dungeonGuard = new Npc("stráž", "Strážný vchodu do vězení",false, 100.0, 5.0, true, Arrays.asList(
                "Stráž: Nemáte tu co dělat, vypadněte.",
                "Stráž: Řekl jsem vám, abyste odešeli.",
                "Stráž: Měli jste mě poslouchat, už mám dost..."),
                "Zbavte se strážce a vezmi si pochodeň než půjdeš dovnitř.");
        Npc brutalGuard = new Npc("brutální_stráž", "Brutální stráž",false, 130.0, 20.0, false, null,
                null);
        Npc frog = new Npc("žába", "Žába",false,50.0, 1.0, false, null, null);
        Npc gorm = new Npc("gorm", "Gorm",true, 100.0,100.0, true, Arrays.asList(
                "Gorm: Chudinka Tue, neměli jste se do té popravy plést." +
                        "Ale chápu, že jste se prostě nemohli dívat, jak veřejně popravují malou holčičku." +
                        "Taky bych se nemohla jen dívat.",
                "Gorm: Bude obtížné dostat se do města, ale zřejmě ji drží v podzemím vězení.",
                "Gorm: Ah, právě jsem si vzpomněla, že někde mezi papíry ve stanu by mělo být povolení " +
                        "ke vstupu do města, pokud ho ještě nemáš u sebe, tak si ho běž vzít.",
                "Gorm: Víš, že nejsem k ničemu v boji, takže mezitím budu pokračovat ve vylepšování " +
                        "našeho vybavení.",
                "Gorm: Doufám, že je Tue v pořádku, stejně tak Thorfinn... Už je to dlouho, " +
                        "co jsme ho ztratili a teď i Tue...",
                "Gorm: No jo, tak už běž, naši přátelé tě potřebují, nemáme čas se vykecávat do nekonečna.",
                "Gorm: Ty máš nějakou povídací, co?",
                "Gorm: Ale notak, já tu mám práci"), null);
        Npc gateGuard = new Npc("stráž_brány", "Stráž brány",true, 100.0, 100.0, true, Arrays.asList(
                "Stráž: Co to tedy bude? Máte povolení, nebo ne?",
                "Stráž: No tak, jsou tu další lidé, kteří se chtějí dostat do města.",
                "Stráž: ... Mám zavolat ostatním strážným, aby vás odvedli, nebo co?"),
                "Strážný vás nepustí dovnitř, pokud mu nepředložíte doklady o povolení ke vstupu do města.");
        Npc passageGuard = new Npc("stráž_průchodu", "Strážný průchodu",true, 100.0, 100.0, true, Arrays.asList(
                "Stráž: Stát, dál nesmíte! Počkat... vy jste přátelé té dívky, která zachránila mou malou sestru " +
                        "před popravou. Já jsem Armin a chtěl bych jí přes vás vzkázat mé díky.",
                "Armin: Nemůžu vám moc pomoct s její záchranou, protože teď mou rodinu bedlivě sledují," +
                        " ale můžu vám dát tohle.",
                "Armin: Buďte opatrní, nevím, jestli jste už našli vchod do žaláře, ale hlídá ho nevrlý stařík." +
                        "Nesnažte se s ním moc mluvit, je velmi agresivní, ale když mu dáte nějaké peníze, okamžitě" +
                        " odejde do hospody."),null);
        Npc wolf = new Npc("vlk", "Vlk",false,80.0, 3.0, false, null, null);
        Npc bear = new Npc("medvěd", "Medvěd",false, 80.0, 3.0, false, null, null);
        Npc rat = new Npc("obří_krysa", "Obří krysa",false,90.0, 3.0, false, null, null);
        Npc troll = new Npc("troll", "Troll",false,90, 5, false, null, null);
        Npc trollKing = new Npc("přerostlý_troll", "Přerostlý troll",false, 150, 10, false,null,
                "Musíš se nejdřív zbavit přerostlého trolla.");
        Npc tue = new Npc("tue", "Tue",true, 1.0,1.0, true, Arrays.asList(
                "...",
                "...",
                "........."), null);
        Npc girl = new Npc("malá_holčička", "Malá holčička",true, 100.0,100.0, true, Arrays.asList(
                "...Máma říká, že bych neměla mluvit s cizími lidmi. Ale vy mi nepřipadáte špatný.",
                "Nedávno mi zlobiví kluci z královského dvora vzali plyšovou hračku tomíka. Teď mě v noci už nic nezahřívá.",
                "Je v noci zima."), null);
        Npc beggar = new Npc("žebrák", "Žebrák",true, 100.0, 100.0, true, Arrays.asList(
                "Dobří lidé, dejte něco chudému žebrákovi.",
                "Když mi dáte trochu peněz, nebo třeba i chleba, dám vám nějaké informace.",
                "Žebráků si nikdo nevšímá, proto toho tolik víme."), null);

        //vložení npcček do lokací
        alley.addNpc(trollKing);
        forge.addNpc(gorm);
        cell1.addNpc(rat);
        cell2.addNpc(brutalGuard);
        cell3.addNpc(tue);
        city.addNpc(general);
        city.addNpc(beggar);
        gate.addNpc(gateGuard);
        forest.addNpc(troll);
        lake.addNpc(frog);
        mountain.addNpc(wolf);
        mountain.addNpc(bear);
        street.addNpc(passageGuard);
        ghetto.addNpc(girl);
        entrence.addNpc(dungeonGuard);

        //vložení npcček do exitů
        gateExit.insertNpc(trollKing);
        cityExit.insertNpc(gateGuard);
        ghettoExit.insertNpc(general);
        entrenceExit.insertNpc(general);
        passageExit.insertNpc(general);
        gateExit.insertNpc(general);
        dungeonExit.insertNpc(dungeonGuard);

        //vytvoření itemů
        Item bigRock = new Item("velký_kámen", "Velký kámen",false, "Jediná věc, která zde vyčnívá.");
        Item shinyRock = new Item("svítící_kámen", "Svítící kámen",true, "Modrý svítící kámen, " +
                " Můžeš to zkusit dát Grom, třeba to k něčemu využije..");
        Item torch = new Item("pochodeň", "Pochodeň",true, "Pochodeň, kterou jste našli před vchodem.");
        Item hugeTree = new Item("velký_strom", "Velký strom",false, "Velký divně rostlý strom. " +
                "Pod jeho kořeny je vidět truhla. A ještě hloubš pod ní je vidět i něco jiného. " +
                "Zkus to ještě trochu prozkoumat.");
        Item bag = new Item("taška", "Taška",true, "Taška plná zlaťáků.");
        Item deadBody = new Item("mrtvola", "Mrtvola",false, "Mrtvola nějakého muže. Fuj.");
        Item key = new Item("klíč", "Klíč",true, "Rezavě vypadající klíč, co asi tak otevírá?");
        Item bush = new Item("keř", "Keř",false, "Keř vedle stanu. " +
                "Gorm si z něj ráda trhá borůvky.");
        Item hammer = new Item("kladivo", "Kladivo",false, "Gormino oblíbené kladivo, raději na něj nesahej.");
        Item oldAnvil = new Item("stará_kovadlina", "Stará kovadlina",false, "Stará kovadlina, kterou Gorm odmítá vyměnit za novou");
        Item tools = new Item("nářadí", "Nářadí",false, "Gromno kovářské nářadí. " +
                "Jedeno z nejlepších v zemi, alespoň to tvrdí Gorm.");
        Item furnace = new Item("pec", "Pec",false, "Kovářská pec.");
        Item beds = new Item("postele", "Postele",false, "Měkké teplé postele.");
        Item pillow = new Item("polštář", "Polštář",true, "Tvůj oblíbený chlupatý polštář, v noci hezky hřeje.");
        Item fireplace = new Item("ohniště", "Ohniště",false, "Malé ohniště uvnitř domu.");
        Item equipment = new Item("výzbroj", "Výzbroj",false, "Staré zbraně a brnění.");
        Item pot = new Item("hrnec", "Hrnec",false, "Hrnec na vaření.");
        Item carpet = new Item("koberec", "Koberec",false, "Chlupatý koberec");
        Item jug = new Item("džbán", "Džbán",false, "Džbán s vodou.");
        Item leftovers = new Item("zbytky", "Zbytky",false, "Zbytky po předvčerejším obědě.");
        Item dummy = new Item("panák", "Panák",false, "Panák na cvičení sbouboje zblízka.");
        Item bucket = new Item("kbelík", "Kbelík",false, "Děravý kbelík.");
        Item rock = new Item("kámen", "Kámen",true, "Prostě kámen.");
        Item permit = new Item("propustka", "Propustka",true, "Propustka do města.");
        Item book = new Item("kniha", "Kniha",false, "Hromada papírů.");
        Item masterKey = new Item("univerzální_klíč", "Univerzální klíč",true, "Klíč který otvírá takřka všechno.");
        Item chest = new Item("truhla", "truhla",false, "");
        Item bag2 = new Item("taška","taška", true, "Další taška plná peněz.");
        Item stick = new Item("klacek", "klacek",true, "Klacek od malé holčičky.");
        Item bread = new Item("chleba", "Chleba",true, "Plesnivý chleba.");
        Item garbage = new Item("odpadky", "Odpadky",false, "Odporně páchnoucí odpadky.");
        Item coin = new Item("peníz", "Peníz",true, "Drobák za který si nic moc nekoupíš.");

        //Vložení itemů do lokací
        hidden_field.addItem(rock);
        hidden_field.addItem(bush);
        hidden_field.addItem(dummy);
        hidden_field.addItem(bucket);
        forge.addItem(hammer);
        forge.addItem(oldAnvil);
        forge.addItem(tools);
        forge.addItem(furnace);
        home.addItem(fireplace);
        home.addItem(equipment);
        home.addItem(carpet);
        dining_room.addItem(jug);
        dining_room.addItem(pot);
        dining_room.addItem(leftovers);
        room.addItem(beds);
        room.addItem(book);
        book.insertItem(permit);
        beds.insertItem(pillow);
        leftovers.insertItem(bread);
        mountain.addItem(hugeTree);
        hugeTree.insertItem(bag);
        hugeTree.insertItem(chest);
        chest.insertItem(bag2);
        lake.addItem(bigRock);
        bigRock.insertItem(shinyRock);
        ghetto.addItem(deadBody);
        ghetto.addItem(garbage);
        deadBody.insertItem(key);
        garbage.insertItem(coin);
        girl.insertItem(stick);
        entrence.addItem(torch);
        passageGuard.insertItem(masterKey);

        //Vytvoření zbraní a umístění zbraní v action name
    }

    /**
     * Metoda pro získání rasy podle jejího jména
     * @param name jméno rasy
     * @return rasa
     */
    public Race getRace(String name){
        Race race = null;
        for (Race current : races){
            if(current.getName().equals(name)){
                race = current;
                break;
            }
        }
        return race;
    }

    /**
     * Metoda pro získání odkazu na aktuální lokaci.
     * @return odkaz na aktuální lokaci
     */
    public Location getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Metoda pro nastavení aktuální lokace.
     * @param currentLocation lokace, která bude nastavena jako nová aktuální lokace
     */
    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
        setInteracting(false);
        setInteractingNpc(null);
        notifyObservers();
    }

    /**
     * Metoda pro získání odkazu na partnera.
     * @return odkaz na partnera
     */
    public Partner getPartner() {
        return partner;
    }

    /**
     * Metoda pro získání odkazu na hráče.
     * @return odkaz na hráče
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Metoda pro získání odkazu na inventář.
     * @return odkaz na inventář
     */
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Metoda pro získání v jaké fázi hra je.
     * Hra je rozdělená na fáze:
     * 0 - není nastavené pohlaví;
     * 1 - není nastavené jméno;
     * 2 - hráč u sebe nemá zbraň;
     * 3 - běžná hra;
     * 4 - nedosačitelná fáze;
     *
     * @return číslo fáze
     */
    public int getPhase() {
        return phase;
    }

    /**
     * Metoda pro nastavení fáze.
     * @param phase 0,1,2 nebo 3
     */
    public void setPhase(int phase) {
        this.phase = phase;
        notifyObservers();
    }

    /**
     * Metoda, podle jejíž odpovědi se určuje,jestli hráč bojuje.
     * @param inCombat true - hráč bojuje, false - hráč nebojuje
     */
    public void setInCombat(boolean inCombat) {
        this.inCombat = inCombat;
        notifyObservers();
    }

    /**
     * Metoda pro nastavení, zda je hráč v souboji.
     * @return true - je, false - není
     */
    public boolean isInCombat() {
        return inCombat;
    }

    /**
     * Metoda pro získání informaace o tok, kolik kol souboje již proběhlo
     * @return počet kol
     */
    public int getRound() {
        return round;
    }

    /**
     * Metoda pro nastavení počtu kol souboje
     * @param round počet kol
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * Metoda pro nastavení npc, se kterým hráč bojuje.
     * @param attackedNpcName jméno npc
     */
    public void setAttackedNpc(String attackedNpcName) {
        this.attackedNpc = getCurrentLocation().getNpc(attackedNpcName);
        notifyObservers();
    }

    /**
     * Metoda pro vrácení npc, se kterým hráč bojuje
     * @return odkaz na npc
     */
    public Npc getAttackedNpc(){
        return attackedNpc;
    }

    /**
     * Metoda pro vrácení negetedDmg.
     * @return negetedDmg
     */
    public double getNegetedDmg() {
        return negetedDmg;
    }

    /**
     * Metoda pro nastavení negetedDmg
     * @param negetedDmg dmg, který hráč vyblokuje
     */
    public void setNegetedDmg(double negetedDmg) {
        this.negetedDmg = negetedDmg;
    }

    /**
     * Metoda pro vrácení bonusDmg
     * @return bonusDmg
     */
    public double getBonusDmg() {
        return bonusDmg;
    }

    /**
     * Metoda pro nastavení bonusDmg
     * @param bonusDmg dmg, který dá vrác navíc
     */
    public void setBonusDmg(double bonusDmg) {
        this.bonusDmg = bonusDmg;
    }

    /**
     * Metoda pro získání informace, zda již hráč použil speciální útok
     * @return true - použil, false - nepoužil
     */
    public boolean isUsedAttack3() {
        return usedAttack3;
    }

    /**
     * Metoda pro nastavení stavu použití speciálního útoku
     * @param usedAttack3 stav použití speciálního útoku
     */
    public void setUsedAttack3(boolean usedAttack3) {
        this.usedAttack3 = usedAttack3;
    }

    /**
     * Metoda pro získání informace, zda již hráč použit charge
     * @return true - použil, false - nepoužil
     */
    public boolean isUsedCharge() {
        return usedCharge;
    }

    /**
     * Metoda pro nastavení stavu použití charge
     * @param usedCharge stav použití charg
     */
    public void setUsedCharge(boolean usedCharge) {
        this.usedCharge = usedCharge;
    }

    /**
     * Metoda pro nastavení, zda hráč komunikuje.
     * @param isInteracting true - jo, false - ne
     */
    public void setInteracting(boolean isInteracting) {
        this.isInteracting = isInteracting;
        notifyObservers();
    }

    /**
     * Metoda zjištění, zda hráč komunikuje.
     * @return true - jo, false - ne
     */
    public boolean isInteracting() {
        return isInteracting;
    }

    /**
     * Metoda pro nastavení npc, se kterým hráč komunikuje.
     * @param interactingNpc npc
     */
    public void setInteractingNpc(String interactingNpc) {
        this.interactingNpc = getCurrentLocation().getNpc(interactingNpc);
        notifyObservers();
    }

    /**
     * Metoda pro vrácení odkazu na npc, se kterým má hráč komunikovat.
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
    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }
}
