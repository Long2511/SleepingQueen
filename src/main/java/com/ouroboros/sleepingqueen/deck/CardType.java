package com.ouroboros.sleepingqueen.deck;

/**
 * Enum class for card types
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
