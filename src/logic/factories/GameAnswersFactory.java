package logic.factories;

import gui.util.Constants;
import logic.blueprints.Location;

public class GameAnswersFactory {

    public static final String beginning =
            "\nWelcome to the game Saving Tue.\n" +
            "You will soon start playing and find out what your goal is, if you are not sure what to do, just type a hint " +
            "and you'll see the commands you can use. " + Constants.d1 +
            "First, choose whether you want to play as a man or a woman." +
            Constants.d2;

    public static final String happyEnd =
            "\nTue was rescued!!!!!!! \n" +
            Constants.d1 + "Congratulations on the successful completion of the game!" + Constants.d2;

    public static final String badEnd =
            Constants.d1 + "The game is over. You lost.." + Constants.d2;

    public static final String race =
                    "You slowly open your eyes and see the blurred silhouette of a giantess in front of you." +
                    "As you struggle to sit up, she turns to you and rushes to help you.\n" +
                    "???: Good morning. How are you feeling? Looks like Tue's been imprisoned. What on earth happened in that town?" +
                    " Can you hear me? Do you even remember your name?\n"
                    + Constants.d1 + "Choose your name." + Constants.d2;

    public static final String name(String name, String partnerName, Location currentLocation) {
                return
                name + ": Ehhh? I'm " + name + ", right? Is that you, Gorm?\n" +
                "Gorm: Yeah, it's me..\n" +
                name + ": Where is " + partnerName + " a Tue?\n" +
                partnerName + ": They've dragged her into some kind of underground prison where they'll keep her until they execute her." +
                "This place really brings us nothing but misery. First Thorfinn and now Tue..." +
                "If she wasn't so soft-hearted, nothing would have happened to her.\n" +
                "Gorm: Slow down a little. You're both doing pretty badly so far. Recover slowly, " +
                "get your weapons and then go help her. I won't be of any use to you in a fight, " +
                "but in the room behind the forge, you can get a weapon that suits you, " +
                "except for the ones I'm working on so far..\n" +
                partnerName + ": I'll join you in a minute, I'm just gonna rest for a while. Then go do your chores. " +
                "I'll join you as soon as you leave the field.\n" +
                currentLocation.longDescription();
    }

}
