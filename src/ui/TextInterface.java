package ui;

import logic.Game;
import java.util.Scanner;

/**
 * Třída představující uživatelského rozhraní.
 * <p>
 * Vytváří instance třídy Game, která představuje logiku hry.
 * Čte vstup od uživatele a předává ho hře.
 * <p>
 * Toto rozhraní je součástí jednoduché textové adventury s grafickým rozhraním.
 *
 * @author Jan Říha
 * @author Alena Kalivodová
 * @version LS-2021, 2021-05-23
 */

public class TextInterface {
    private Game game;

    /**
     * Konstruktor třídy, vytvoří uživatelské rozhraní pro danou hru.
     *
     * @param game hra
     */
    public TextInterface(Game game) {
        this.game = game;

    }

    /**
     * Metoda zajišťující hraní hry. Nejprve vypíše úvodní text. Poté v cyklu
     * načítá zadané příkazy z konzole, předává je hře ke zpracování a vypisuje
     * reakce hry. To se neustále opakuje, dokud hra prostřednictvím metody
     * {@link Game#theEnd() theEnd} neoznámí, že skončila.
     */
    public void play() {
        System.out.println(game.theBeginning());
        while(!game.theEnd()) {
            String line = readString();
            System.out.println(game.processAction(line));
        }
        System.out.println(game.epilog());
    }

    /**
     * Metoda pro přečtení inputu od hráče
     * @return to co hráč napíše
     */
    private String readString() {
        Scanner scanner = new Scanner((System.in));
        System.out.print("> ");
        return scanner.nextLine();
    }
}
