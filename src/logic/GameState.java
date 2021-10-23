package logic;


import util.Observer;
import util.SubjectOfChange;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Třída představující stav hry a implementující rozhraní SubjectOfChange
 * <p>
 * Iniacializuje lokace, itemy, npc, zbraně a exity.
 * <p>
 * Tato třída je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Alena Kalivodová
 * @version ZS-2021, 2021-10-26
 */

public class GameState implements SubjectOfChange {
    private Location currentLocation;
    private Inventory inventory;
    private Player player;
    private Partner partner;
    private int phase;

    private Set<Observer> observerSet = new HashSet<>();

    /**
     * Konstruktor - inicializuje hru, vytvoří nového hráče, partnera, inventář a nastaví fázi hry.
     */
    public GameState() {
        createGame();
        inventory = new Inventory();
        player = new Player(null, null, null, 20, 0);
        partner = new Partner(null, null, 20, 0);
        phase = 0;
    }

    /**
     * Incializace lokací, itemů, npc, zbraní a exitů
     */
    private void createGame() {
        // Vytvoření jednotlivých lokací
        Location camp = new Location("kemp", "Kemp, který jste založili se svými přítale," +
                " je chráněn silnou magickou bariérou,\ntakže žádný příšery se sem nedostanou.", 2);
        Location tent = new Location("stan", "Je tu všechno, co jen můžeš potřebovat. " +
                "Zvláště pohodlná postel, do které můžete vstoupit, jakmile zachráníte Tua.",2);
        Location forge = new Location("kovárna", "Gormovo oblíbené místo. " +
                "Když ho budeš potřebovat, tak ho najdeš tady.",2);
        Location room = new Location("zbrojírna", "Tady si můžeš vybrat svou zbraň.",2);
        Location forest = new Location("les", "Temný nebezpečný les, nic dobrého tu nenajdeš.",3);
        Location mountain = new Location("hora", "Je tu poměrně velká hora. " +
                "Není důvod jít nahoru, ale tady dole ještě může být něco užitečného.\n" +
                "Ale teď bys měl/a něco udělat s králíky, kteří na tebe z ničeho nic zaútočili.\n" +
                "Schovávají se, ale pokud se vrátíš, pravděpodobně zaútočí znovu.\n",3);
        Location lake = new Location("jezero", "Jezero na západní straně tábora. Strávili jste tu spoustu nocí.",3);
        Location alley = new Location("alej", "Alej vedoucí k městské bráně. " +
                "Jediným problémem je, že se tu potuluje obrovský troll, kterého je třeba se zbavit, než půjdete k bráně.\n",3);
        Location gate = new Location("brána", "Přísně střežená brána a jediná cesta, která vede do města. \n" +
                "Stráž: Zastavte prosím a ukažte mi doklady o povolení k vstupu, pokud je nemáte, nemohu vás pustit dovnitř.",3);
        Location city = new Location("město", "Odporné místo plné špíny. " +
                "Není tu jediná věc, která by se vám na tomhle místě líbila. A aby to bylo ještě lepší, narazili jste na " +
                "králova vrchního generála.\n" +
                "Generál: Vy dva se zastavte. Kdo jste a co tu chcete, připadáte mi dost povědomí, ale ne v dobrém slova smyslu.\n" +
                "///Nyní máš dvě možnosti. Dát mu něco, co by mohlo utišit jeho zvědavost, nebo bojovat.///",3);
        Location ghetto = new Location("ghetto", "Chudá část města a zároveň jeho největší část. " +
                "Není moc důvodů, proč tu zůstávat déle, než je nutné.\n",3);
        Location passage = new Location("průchod", "Průchod na hlavní nádvoří. " +
                "Na nádvoří se nedostaneš ať by ses snažil/a sebevíc. Prostě mi s tímhle věř\n.",3);
        Location coutyard = new Location("nádvoří", "",4);
        Location entrence = new Location("vchod", "Vchod do pozdemí, který je podezdřele málo střežený.",3);
        Location dungeon = new Location("žalář", "Podzemní žalář. Je tu velká tma, ale pochodeň pomáhá vidět " +
                "alespoň pár kroků dopředu.\n" +
                " Také to vypadá, že je tu jen jedna cesta dovnitř a ven, když nepočítám vchody do tří cel.",3);
        Location cell1 = new Location("cela1", "Malá cela, ve které jsou jen krysy.",3);
        Location cell2 = new Location("cela2", "Poměrně velká cela s mnoha tmavými zákoutími. " +
                "Raději buďte opatrný/á",3);
        Location cell3 = new Location("cela3", "Malá nechutná cela, na zemi leží téměř bezvládné tělo " +
                "tvého kamaráda.",3);

        //Přiřazení exitu k lokaci
        Exit campExit = new Exit(camp);
        Exit alleyExit = new Exit(alley);
        Exit forestExit = new Exit(forest);
        Exit lakeExit = new Exit(lake);
        Exit mountainExit = new Exit(mountain);
        Exit forgeExit = new Exit(forge);
        Exit tentExit = new Exit(tent);
        Exit roomExit = new Exit(room);
        Exit dungeonExit = new Exit(dungeon);
        Exit ghettoExit = new Exit(ghetto);
        Exit passageExit = new Exit(passage);
        Exit entrenceExit = new Exit(entrence);
        Exit gateExit = new Exit(gate);
        Exit cityExit = new Exit(city);
        Exit coutyardExit = new Exit(coutyard);
        Exit cell1Exit = new Exit(cell1);
        Exit cell2Exit = new Exit(cell2);
        Exit cell3Exit = new Exit(cell3);

        //Přiřazení východů z jednotlivých lokací
        alley.addExit(campExit);
        alley.addExit(gateExit);
        camp.addExit(alleyExit);
        camp.addExit(forestExit);
        camp.addExit(lakeExit);
        camp.addExit(mountainExit);
        camp.addExit(tentExit);
        camp.addExit(forgeExit);
        forge.addExit(campExit);
        forge.addExit(roomExit);
        room.addExit(forgeExit);
        tent.addExit(campExit);
        cell1.addExit(dungeonExit);
        cell2.addExit(dungeonExit);
        city.addExit(ghettoExit);
        city.addExit(passageExit);
        city.addExit(entrenceExit);
        city.addExit(gateExit);
        gate.addExit(alleyExit);
        gate.addExit(cityExit);
        forest.addExit(campExit);
        ghetto.addExit(cityExit);
        lake.addExit(campExit);
        mountain.addExit(campExit);
        passage.addExit(cityExit);
        passage.addExit(coutyardExit);
        entrence.addExit(cityExit);
        entrence.addExit(dungeonExit);
        dungeon.addExit(cell1Exit);
        dungeon.addExit(cell2Exit);
        dungeon.addExit(cell3Exit);
        dungeon.addExit(entrenceExit);

        //Nastavení počáteční lokace
        currentLocation = camp;

        //Vytvoření npcček
        Npc general = new Npc("generál", 30, 20, Arrays.asList("Generl: Kdo jste!",
                "General: Pokud nechcete zatěžovat mou mysl svými odpornými jmény, můžete místo toho zatížit mou peněženku.",
                "General: Zabijte je, už jsem s nimi ztratil víc než dost času."),"Králův vrchní generál tě nenechá odejít. Musíte s ním jednat.");
        Npc dungeonGuard = new Npc("stráž", 20, 5, Arrays.asList("Stráž: Nemáte tu co dělat, vypadněte.",
                "Stráž: Řekl jsem vám, abyste odešeli, tady je malé varování.",
                "Stráž: Měli jste mě poslouchat, už mám dost..."),
                "Zbavte se strážce a vezměte si pochodeň, než půjdete dovnitř..");
        Npc brutalGuard = new Npc("brutal_guard", 20, 20);
        Npc frog = new Npc("žába", 1, 1);
        Npc gorm = new Npc("gorm", Arrays.asList(
                "Gorm: Chudák Tue, neměli jste se do té popravy plést...\n" +
                        "Ale chápu, že jste se prostě nemohli dívat, jak veřejně popravují malou holčičku...",
                "Gorm: Bude obtížné dostat se do města, ale zřejmě ho drží ve skrytém podzemím\n" +
                        "žaláři, buďte obzvlášť opatrní, nevím, co bych dělal, kdybych vás ztratil všechny ...",
                "Gorm: Ah, právě jsem si vzpomněl, že někde mezi papíry ve stanu by mělo být povolení ke vstupu do města,\n" +
                        " pokud jste si ho ještě nevzali, tak to udělejte. Jinak se tam pravděpodobně vůbec nedostanete.",
                "Gorm: No jo, tak už běž, Tue tě potřebuje.",
                "Gorm: Ty máš nějakou povídací, co?",
                "Gorm: Ale notak, já tu mám práci"));
        Npc gateGuard = new Npc("stráž_brány", Arrays.asList(
                "Stráž: Co to tedy bude? Máte povolení, nebo ne?",
                "Stráž: No tak, jsou tu další lidé, kteří se chtějí dostat do města.",
                "Stráž: ... Mám zavolat ostatním strážným, aby vás odvedli, nebo co?"),
                "Strážný vás nepustí dovnitř, pokud mu nepředložíte doklady o povolení ke vstupu do města.");
        Npc passageGuard = new Npc("stráž_průchodu", Arrays.asList(
                "Stráž: Nejste přátelé toho chlapce, kterého chtějí pověsit? Jo, to jste to vy. Počkejte, nenahlásím vás.\n" +
                        "Ta holčička, kterou zachránil před popravou... to byla moje mladší sestra. Mimochodem, jmenuju se Armin.",
                "Armin: Nemůžu vám moc pomoct, už tak neši rodinu bedlivě sledují, ale můžu vám dát tohle.",
                "Armin: Buďte opatrní, nevím, jestli jste už našli vchod do žaláře, ale hlídá ho nevrlý stařík.\n" +
                        "Nesnažte se s ním moc mluvit, je velmi agresivní, ale když mu dáte nějaké peníze, okamžitě odejde do hospody"));
        Npc grayRabbit = new Npc("šedý_zajíc", 2, 1);
        Npc rabbit = new Npc("zajíc", 2, 1);
        Npc whiteRabbit = new Npc("bílý_zajíc", 2, 1);
        Npc rat = new Npc("krysa", 1, 1);
        Npc rat2 = new Npc("krysa", 1, 1);
        Npc troll = new Npc("troll", 20, 5);
        Npc trollKing = new Npc("přerostlý_troll", 30, 10, "Musíš se nejdřív zbavit přerostlého trolla.");
        Npc tue = new Npc("tue", Arrays.asList(
                "...",
                "...J--st-se to vy ka-a-ma-rá--di?",
                "........."));
        Npc girl = new Npc("malá_holčička", Arrays.asList(
                "...Máma říká, že bych neměla mluvit s cizími lidmi. Ale vy mi nepřipadáte špatný.",
                "Nedávno mi zlobiví kluci z královského dvora vzali plyšovou hračku tomíka. Teď se nemám v noci s čím mazlit.",
                "Bez něj mi tu je velmi smutno."));
        Npc beggar = new Npc("žebrák", Arrays.asList(
                "Dobří lidé, dejte něco chudému žebrákovi.",
                "Když mi dáte trochu peněz, nebo třeba i chleba, dám vám nějaké informace.",
                "Žebráků si nikdo nevšímá, proto toho tolik víme."));

        //vložení npcček do lokací
        alley.addNpc(trollKing);
        forge.addNpc(gorm);
        cell1.addNpc(rat2);
        cell2.addNpc(brutalGuard);
        cell3.addNpc(tue);
        city.addNpc(general);
        city.addNpc(beggar);
        gate.addNpc(gateGuard);
        forest.addNpc(troll);
        ghetto.addNpc(rat);
        lake.addNpc(frog);
        mountain.addNpc(grayRabbit);
        mountain.addNpc(rabbit);
        mountain.addNpc(whiteRabbit);
        passage.addNpc(passageGuard);
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
        Item deadBody = new Item("mrtvé_tělo", false, "Mrtvola nějakého muže. Fuj.");
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
        Item rock = new Item("kámen", true, "kámen");
        Item permit = new Item("propustka", true, "Propustka do města.");
        Item papers = new Item("papíry", false, "Hromada papírů");
        Item masterKey = new Item("univerzální_klíč", true, "Klíč který otvírá takřka všechno");
        Item chest = new Item("truhla", false, "");
        Item bag2 = new Item("taška", true, "Další taška plná peněz.");
        Item stick = new Item("klacek", true, "Klacek od malé holčičky.");
        Item bread = new Item("chleba", true, "Plesnivý chleba.");
        Item garbage = new Item("odpadky", false, "Odporně páchnoucí odpadky.");
        Item coin = new Item("peníz", true, "Drobák za který si nic moc nekoupíš.");

        //Vložení itemů do lokací
        camp.addItem(rock);
        camp.addItem(bush);
        camp.addItem(dummy);
        camp.addItem(bucket);
        forge.addItem(hammer);
        forge.addItem(oldAnvil);
        forge.addItem(tools);
        forge.addItem(furnace);
        tent.addItem(beds);
        tent.addItem(fireplace);
        tent.addItem(equipment);
        tent.addItem(pot);
        tent.addItem(carpet);
        tent.addItem(jug);
        tent.addItem(leftovers);
        tent.addItem(papers);
        papers.insertItem(permit);
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
        Weapon knife = new Weapon("nůž", 1.3, false);
        Weapon broadsword = new Weapon("broadsword", 2, true);
        Weapon greatsword = new Weapon("greatsword", 2.2, true);
        Weapon dagger = new Weapon("dýka", 1.8, true);
        Weapon spear = new Weapon("kopí", 2, true);

        //Umístění zbraní
        room.addWeapon(axe);
        room.addWeapon(sword);
        room.addWeapon(knife);
        room.addWeapon(broadsword);
        room.addWeapon(greatsword);
        room.addWeapon(dagger);
        room.addWeapon(spear);
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
    }

    @Override
    public void registerObserver(Observer observer) {
        observerSet.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        observerSet.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observerSet) {
            o.update();
        }
    }
}
