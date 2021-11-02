package logic;


import util.Observer;
import util.SubjectOfChange;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Třída představující stav hry a implementující rozhraní SubjectOfChange
 * <p>
 * Iniacializuje lokace, itemy, npc, zbraně a exity.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-11-01
 */

public class GameState implements SubjectOfChange {
    private Location currentLocation;
    private Inventory inventory;
    private Player player;
    private Partner partner;
    private int phase;
    private boolean inCombat;
    private Npc attackedNpc;
    private boolean isInteracting;
    private Npc comunicatingNpc;


    private Set<Observer> observers = new HashSet<>();

    /**
     * Konstruktor - inicializuje hru, vytvoří nového hráče, partnera, inventář a nastaví fázi hry.
     */
    public GameState() {
        createGame();
        inventory = new Inventory();
        player = new Player(null, null, null, 20.0, 0.0, 0.0, "nic");
        partner = new Partner(null, null, 20.0, 0.0,0.0);
        phase = 0;
        inCombat = false;
        isInteracting = false;
        attackedNpc = new Npc(null,false,0.0,0.0,false, null, null);
        comunicatingNpc = new Npc(null,false,0.0,0.0,false, null, null);
    }

    /**
     * Incializace lokací, itemů, npc, zbraní a exitů
     */
    private void createGame() {
        // Vytvoření jednotlivých lokací
        Location hidden_field = new Location("skrytá_louka",
                "Skrytá louka na kterou jste narazili s přáteli během průzkumu lesa, je chráněna silnou " +
                        "\nmagickou bariérou, takže žádné příšery se sem nedostanou.", 2);
        Location home = new Location("dům", "Je tu všechno, co jen můžeš potřebovat.",2);
        Location dining_room = new Location("jídelna", "Dobře vybavená jídelna.", 2);
        Location room = new Location("pokoj", "Teplý pokoj.", 2);
        Location forge = new Location("kovárna", "Gormovo oblíbené místo. " +
                "Když ho budeš potřebovat, tak ho najdeš tady.",2);
        Location armory = new Location("zbrojírna", "Tady si můžeš vybrat svou zbraň.",2);
        Location forest = new Location("les", "Temný nebezpečný les, nic dobrého tu nenajdeš.",3);
        Location mountain = new Location("hora", "Je tu poměrně velká hora. " +
                "Není důvod jít nahoru, ale tady dole ještě může být něco užitečného.\n",3);
        Location lake = new Location("jezero",
                "Jezero na západní straně tábora. Strávili jste tu spoustu nocí.",3);
        Location alley = new Location("alej", "Alej vedoucí k městské bráně. \n" +
                "Jediným problémem je, že se tu potuluje obrovský troll, kterého je třeba se zbavit, než půjdete k bráně.\n",3);
        Location gate = new Location("brána", "Přísně střežená brána a jediná cesta, která vede do města. \n" +
                "Stráž: Zastavte prosím a ukažte mi doklady o povolení k vstupu, pokud je nemáte, nemohu vás pustit dovnitř.",3);
        Location city = new Location("město", "Odporné místo plné špíny. \n" +
                "Není tu jediná věc, která by se ti na tomhle místě líbila. \n" +
                "A aby to bylo ještě lepší, narazili jste s parťákem na králova vrchního generála.\n" +
                "Generál: Vy dva se zastavte. Kdo jste a co tu chcete, připadáte mi dost povědomí, ale ne v dobrém slova smyslu.\n" +
                "///Nyní máš dvě možnosti. Dát mu něco, co by mohlo utišit jeho zvědavost, nebo bojovat.///",3);
        Location ghetto = new Location("ghetto", "Chudá část města a zároveň jeho největší část. " +
                "Není moc důvodů, proč tu zůstávat déle, než je nutné.\n",3);
        Location street = new Location("ulice", "Ulice vedoucí na hlavní nádvoří. " +
                "Na nádvoří se nedostaneš ať by ses snažil/a sebevíc. Prostě mi s tímhle věř.\n.",3);
        Location coutyard = new Location("nádvoří", "",4);
        Location entrence = new Location("vchod", "Vchod do pozdemí, který je podezdřele málo střežený.",3);
        Location dungeon = new Location("žalář",
                "Podzemní žalář. Je tu velká tma, ale pochodeň pomáhá vidět alespoň pár kroků dopředu.\n" +
                " Také to vypadá, že je tu jen jedna cesta dovnitř a ven, když nepočítám vchody do tří cel.",3);
        Location cell1 = new Location("cela_na_levo", "Malá cela, ve které jsou jen krysy.",3);
        Location cell2 = new Location("cela_uprostřed", "Poměrně velká cela s mnoha tmavými zákoutími. " +
                "Raději buďte opatrný/á",3);
        Location cell3 = new Location("cela_na_pravo",
                "Malá nechutná cela, v níž na zemi leží téměř bezvládné tělo tvého kamaráda.",3);

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

        //Vytvoření npcček
        Npc general = new Npc("generál", false, 30.0, 20.0, true, Arrays.asList(
                "Generl: Kdo jste!",
                "General: Pokud nechcete zatěžovat mou mysl svými odpornými jmény, můžete místo toho zatížit mou peněženku.",
                "General: Zabijte je, už jsem s nimi ztratil víc než dost času."),
                "Králův vrchní generál tě nenechá odejít. Musíte s ním jednat.");
        Npc dungeonGuard = new Npc("stráž", false, 20.0, 5.0, true, Arrays.asList(
                "Stráž: Nemáte tu co dělat, vypadněte.",
                "Stráž: Řekl jsem vám, abyste odešeli, tady je malé varování.",
                "Stráž: Měli jste mě poslouchat, už mám dost..."),
                "Zbavte se strážce a vezměte si pochodeň, než půjdete dovnitř..");
        Npc brutalGuard = new Npc("brutal_guard", false, 20.0, 20.0, false, null,
                null);
        Npc frog = new Npc("žába", false,1.0, 1.0, false, null, null);
        Npc gorm = new Npc("gorm", true, 100.0,100.0, true, Arrays.asList(
                "Gorm: Chudák Tue, neměli jste se do té popravy plést...\n" +
                        "Ale chápu, že jste se prostě nemohli dívat, jak veřejně popravují malou holčičku...",
                "Gorm: Bude obtížné dostat se do města, ale zřejmě ho drží ve skrytém podzemím\n" +
                        "žaláři, buďte obzvlášť opatrní, nevím, co bych dělal, kdybych vás ztratil všechny ...",
                "Gorm: Ah, právě jsem si vzpomněl, že někde mezi papíry ve stanu by mělo být povolení ke vstupu do města,\n" +
                        " pokud jste si ho ještě nevzali, tak to udělejte. Jinak se tam pravděpodobně vůbec nedostanete.",
                "Gorm: No jo, tak už běž, Tue tě potřebuje.",
                "Gorm: Ty máš nějakou povídací, co?",
                "Gorm: Ale notak, já tu mám práci"), null);
        Npc gateGuard = new Npc("stráž_brány", true, 100.0, 100.0, true, Arrays.asList(
                "Stráž: Co to tedy bude? Máte povolení, nebo ne?",
                "Stráž: No tak, jsou tu další lidé, kteří se chtějí dostat do města.",
                "Stráž: ... Mám zavolat ostatním strážným, aby vás odvedli, nebo co?"),
                "Strážný vás nepustí dovnitř, pokud mu nepředložíte doklady o povolení ke vstupu do města.");
        Npc passageGuard = new Npc("stráž_průchodu", true, 100.0, 100.0, true, Arrays.asList(
                "Stráž: Nejste přátelé toho chlapce, kterého chtějí pověsit? Jo, to jste to vy. Počkejte, nenahlásím vás.\n" +
                        "Ta holčička, kterou zachránil před popravou... to byla moje mladší sestra. \n" +
                        "Mimochodem, jmenuju se Armin.",
                "Armin: Nemůžu vám moc pomoct, už tak neši rodinu bedlivě sledují, ale můžu vám dát tohle.",
                "Armin: Buďte opatrní, nevím, jestli jste už našli vchod do žaláře, ale hlídá ho nevrlý stařík.\n" +
                        "Nesnažte se s ním moc mluvit, je velmi agresivní, ale když mu dáte nějaké peníze, okamžitě\n" +
                        " odejde do hospody"),null);
        Npc wolf = new Npc("vlk", false,10.0, 3.0, false, null, null);
        Npc bear = new Npc("medvěd", false, 10.0, 3.0, false, null, null);
        Npc rat = new Npc("obří_krysa", false,10.0, 3.0, false, null, null);
        Npc troll = new Npc("troll", false,20, 5, false, null, null);
        Npc trollKing = new Npc("přerostlý_troll", false, 30, 10, false,null,
                "Musíš se nejdřív zbavit přerostlého trolla.");
        Npc tue = new Npc("tue", true, 1.0,1.0, true, Arrays.asList(
                "...",
                "...J--st-se to vy ka-a-ma-rá--di?",
                "........."), null);
        Npc girl = new Npc("malá_holčička", true, 100.0,100.0, true, Arrays.asList(
                "...Máma říká, že bych neměla mluvit s cizími lidmi. Ale vy mi nepřipadáte špatný.",
                "Nedávno mi zlobiví kluci z královského dvora vzali plyšovou hračku tomíka. Teď se nemám v noci s čím mazlit.",
                "Bez něj mi tu je velmi smutno."), null);
        Npc beggar = new Npc("žebrák", true, 100.0, 100.0, true, Arrays.asList(
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
        Item bigRock = new Item("velký_kámen", false, "Jediná věc, která zde vyčnívá.");
        Item shinyRock = new Item("svítící_kámen", true, "Modrý svítící kámen, " +
                "nikdy si nic takového neviděl/a.\n Můžeš to zkusit dát Gromovi, třeba to k něčemu využije..");
        Item torch = new Item("pochodeň", true, "Pochodeň, kterou jste našli před vchodem.");
        Item hugeTree = new Item("velký_strom", false, "Velký divně rostlý strom. " +
                "Pod jejími kořeny je vidět truhla. A ještě více pod ní je vidět i něco jiného. " +
                "Zkus to ještě trochu prozkoumat.");
        Item bag = new Item("taška", true, "Taška plná zlaťáků.");
        Item deadBody = new Item("mrtvola", false, "Mrtvola nějakého muže. Fuj.");
        Item key = new Item("klíč", true, "Rezavě vypadající klíč, co asi tak otevírá?");
        Item bush = new Item("keř", false, "Keř vedle stanu. " +
                "Gorm si z něj rád trhá borůvky.");
        Item hammer = new Item("kladivo", false, "Gormovo oblíbené kladivo, raději na něj nesahej.");
        Item oldAnvil = new Item("stará_kovadlina", false, "Stará kovadlina, kteoru Gorm odmítá vyměnit za novou");
        Item tools = new Item("nářadí", false, "Gromovo kovářské náředí. " +
                "Jedeno z nejlepších v zemi, alespoň to tvrdí Gorm.");
        Item furnace = new Item("pec", false, "Provizorní kovářská pec pro Gorma.");
        Item beds = new Item("postele", false, "Nejoblíbenější nábytek snad všech, kdo tu s tebou žijí.");
        Item pillow = new Item("polštář", true, "Tvůj oblíbený chlupatý polštář.");
        Item fireplace = new Item("ohniště", false, "Malé ohniště uvnitř stanu. " +
                "Větším by se akorát riskovalo, že to tu všechno shoří.");
        Item equipment = new Item("výzbroj", false, "Staré zbraně a brnění");
        Item pot = new Item("hrnec", false, "Hrnec na vaření, který se překvapivě používá na vaření.");
        Item carpet = new Item("koberec", false, "Chlupatý koberec");
        Item jug = new Item("džbán", false, "Džbán s vodou.");
        Item leftovers = new Item("zbytky", false, "Zbytky po předvčerejším obědě.");
        Item dummy = new Item("panák", false, "Panák na cvičení sbouboje zblízka.");
        Item bucket = new Item("kbelík", false, "Děravý kbelík. Ten opravdu nepotřebuješ.");
        Item rock = new Item("kámen", true, "Prostě kámen.");
        Item permit = new Item("propustka", true, "Propustka do města.");
        Item book = new Item("kniha", false, "Hromada papírů");
        Item masterKey = new Item("univerzální_klíč", true, "Klíč který otvírá takřka všechno");
        Item chest = new Item("truhla", false, "");
        Item bag2 = new Item("taška", true, "Další taška plná peněz.");
        Item stick = new Item("klacek", true, "Klacek od malé holčičky.");
        Item bread = new Item("chleba", true, "Plesnivý chleba.");
        Item garbage = new Item("odpadky", false, "Odporně páchnoucí odpadky.");
        Item coin = new Item("peníz", true, "Drobák za který si nic moc nekoupíš.");

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


        //Vytvoření zbraní
        Weapon axe = new Weapon("sekera", 1.5, false);
        Weapon sword = new Weapon("meč", 1.5, false);
        Weapon kyj = new Weapon("kyj", 1.3, false);
        Weapon halberd = new Weapon("halberda", 2, true);
        Weapon greatsword = new Weapon("greatsword", 2.2, true);
        Weapon dagger = new Weapon("dýka", 1.8, true);
        Weapon spear = new Weapon("kopí", 2, true);

        //Umístění zbraní
        armory.addWeapon(axe);
        armory.addWeapon(sword);
        armory.addWeapon(kyj);
        armory.addWeapon(halberd);
        armory.addWeapon(greatsword);
        armory.addWeapon(dagger);
        armory.addWeapon(spear);
    }

    /**
     * Metoda pro získání odkazu na aktuální lokaci.
     *
     * @return odkaz na aktuální lokaci
     */
    public Location getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Metoda pro nastavení aktuální lokace.
     * Při změně aktuální lokace upozorní observery.
     *
     * @param currentLocation lokace, která bude nastavena jako nová aktuální lokace
     */
    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
        notifyObservers();
    }

    /**
     * Metoda pro získání odkazu na partnera.
     *
     * @return odkaz na partnera
     */
    public Partner getPartner() {
        return partner;
    }

    /**
     * Metoda pro získání odkazu na hráče.
     *
     * @return odkaz na hráče
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Metoda pro získání odkazu na inventář.
     *
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
     *
     * @param phase 0,1,2 nebo 3
     */
    public void setPhase(int phase) {
        this.phase = phase;
        notifyObservers();
    }

    /**
     * Metoda, která je zavolána, když hráč začne bojovat a hnedka soubouj nebokončí a pomocí níž je nastavena hodnota
     * parametru inCombat na true.
     *
     * Nebo je zalována, když hráč zabije npc, se kterým bojoval a hodnota parametru je nastavena na false.
     *
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
     * @param comunicatingNpc npc
     */
    public void setComunicatingNpc(String comunicatingNpc) {
        this.comunicatingNpc = getCurrentLocation().getNpc(comunicatingNpc);
        notifyObservers();
    }

    /**
     * Metoda pro vrácení odkazu na npc, se kterým má hráč komunikovat.
     * @return npc
     */
    public Npc getComunicatingNpc(){
        return comunicatingNpc;
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
