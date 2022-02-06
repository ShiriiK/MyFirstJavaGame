package gui.ui;

import logic.Game;
import java.util.Scanner;

/**
 * Class representing the user interface.
 * Creates an instance of the Game class that represents the game logic.
 * Reads input from the user and passes it to the game.
 * @author Jan Říha
 * @author Alena Kalivodová
 */

public class TextInterface {
    private Game game;

    public TextInterface(Game game) {
        this.game = game;
    }

    /**
     * Method of ensuring game play. It first prints the introductory text. Then, in a loop
     * retrieves the specified commands from the console, passes them to the game for processing, and prints
     * the game's response. This is repeated continuously until the game, via the method
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
     * Method to read input from the player
     * @return what the player writes
     */
    private String readString() {
        Scanner scanner = new Scanner((System.in));
        System.out.print("> ");
        return scanner.nextLine();
    }
}
