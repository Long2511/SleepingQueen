package com.ouroboros.sleepingqueen.deck;

/**
 * Enum class for card types
 * There are 8 types of cards in the game
 * QUEEN, KING, JESTER, KNIGHT, POTION, WAND, DRAGON, NUMBER
 * Used to determine the type of the card
 */

public enum CardType {
    QUEEN("QUEEN"),
    KING("KING"),
    JESTER("JESTER"),
    KNIGHT("KNIGHT"),
    POTION("POTION"),
    WAND("WAND"),
    DRAGON("DRAGON"),
    NUMBER("NUMBER");

    private final String type;

    CardType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
