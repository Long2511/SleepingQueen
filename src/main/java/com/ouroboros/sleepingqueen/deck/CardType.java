package com.ouroboros.sleepingqueen.deck;

/**
 * Enum class representing different types of cards in the game.
 * There are 8 types of cards:
 * - QUEEN: Represents a Queen card.
 * - KING: Represents a King card.
 * - JESTER: Represents a Jester card.
 * - KNIGHT: Represents a Knight card.
 * - POTION: Represents a Potion card.
 * - WAND: Represents a Wand card.
 * - DRAGON: Represents a Dragon card.
 * - NUMBER: Represents a Number card.
 * <p>
 * This enum is used to determine the type of a card in the game.
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

    /**
     * Constructor for CardType enum.
     *
     * @param type The string representation of the card type.
     */
    CardType(String type) {
        this.type = type;
    }

    /**
     * Returns the string representation of the card type.
     *
     * @return the card type as a string.
     */
    public String getType() {
        return type;
    }
}