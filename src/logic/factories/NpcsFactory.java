package logic.factories;

import logic.blueprints.Npc;

import java.util.Arrays;

/**
 * Factory for creating npcs
 */
public class NpcsFactory {

    public static final Npc general = new Npc("general", "General",false, 150, 20, true, Arrays.asList(
            "General: Who are you!",
            "General: If you don't want to burden my mind with your nasty names, you can burden my wallet instead.",
            "General: Kill them, I've wasted more than enough time with them."),
            "The king's chief general won't let you leave. You must deal with him.");
    public static final Npc dungeonGuard = new Npc("guard", "Guard",false, 100, 5, true, Arrays.asList(
            "Guard: You have nothing to do here, get out.",
            "Guard: I told you to leave.",
            "Guard: You should have listened to me, I've had enough..."),
            "Get rid of the guard and take the torch before you go in.");
    public static final Npc brutalGuard = new Npc("brutal_guard", "Brutal guard",false, 130, 20, false, null,
            null);
    public static final Npc frog = new Npc("frog", "Frog",false,50, 1, false, null, null);
    public static final Npc gorm = new Npc("gorm", "Gorm",true, 100,100, true, Arrays.asList(
            "Gorm: Poor Tue, you shouldn't have interfered with the execution." +
                    "But I understand you just couldn't watch a little girl being publicly executed." +
                    "I wouldn't be able to just watch either.",
            "Gorm: It'll be difficult to get into the city, but they're probably keeping her in an underground prison.",
            "Gorm: Ah, I just remembered that somewhere among the papers in the house there should be a permit " +
                    "to enter the city, if you don't already have it, go get it.",
            "Gorm: You know I'm no good in a fight, so in the meantime I'll continue to upgrade " +
                    "our equipment.",
            "Gorm: I hope Tue is okay, and so is Thorfinn... It's been a long time, " +
                    "since we lost him and now even Tue...",
            "Gorm: Yeah, go on, our friends need you, we don't have time to talk endlessly.",
            "Gorm: You've got some chatty mood, haven't you?",
            "Gorm: Oh, come on, I'm busy here."), null);
    public static final Npc gateGuard = new Npc("gate_guard", "Gate guard",true, 100, 100, true, Arrays.asList(
            "Guard: What will it be then? Do you have permission or not?",
            "Guard: Come on, there are other people who want to get into town.",
            "Guard: ... Should I call the other guards to take you away or what?"),
            "The guard won't let you in unless you show him your permit to enter the city.");
    public static final Npc passageGuard = new Npc("passage_guard", "Passage guard",true, 100, 100, true, Arrays.asList(
            "Guard: Stop, you mustn't go on! Wait... you're friends with the girl who saved my little sister " +
                    "from execution. I'm Armin and I'd like to say thank you to her through you.",
            "Armin: I can't help you much with her rescue because my family is being watched closely now, ",
            "but I can give you this.",
            "Armin: Be careful, I don't know if you've found the entrance to the dungeon yet, but it's guarded by a grumpy old man." +
                    "Don't try to talk to him too much, he's very aggressive, but if you give him some money, " +
                    "he'll go to the pub immediately."),null);
    public static final Npc wolf = new Npc("wolf", "Wolf",false,80, 3, false, null, null);
    public static final Npc bear = new Npc("bear", "Bear",false, 80, 3, false, null, null);
    public static final Npc rat = new Npc("rat", "Rat",false,90, 3, false, null, null);
    public static final Npc troll = new Npc("troll", "Troll",false,90, 5, false, null, null);
    public static final Npc trollKing = new Npc("troll_king", "Troll king",false, 150, 10, false,null,
            "You need to get rid of the troll king first.");
    public static final Npc tue = new Npc("tue", "Tue",true, 1,1, true, Arrays.asList(
            "...",
            "...",
            "........."), null);
    public static final Npc girl = new Npc("girl", "Girl",true, 100,100, true, Arrays.asList(
            "Girl: Mom says I shouldn't talk to strangers. But you don't seem bad to me.",
            "Girl: The other day the naughty boys from the royal court took my stuffed toy. Now nothing keeps me warm at night.",
            "It's cold during night."), null);
    public static final Npc beggar = new Npc("beggar", "Beggar",true, 100, 100, true, Arrays.asList(
            "Beggar: Good people, give something to a poor beggar",
            "Beggar: If you give me some money, or even some bread, I'll give you some information.",
            "Beggar: Nobody pays attention to beggars, that's why we know so much."), null);
}
