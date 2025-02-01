/**
 * Player class represents a player in the game. Each player has a name, a score, and a set of cards.
 * The player can have up to 5 normal cards and 4 queen cards at a time. The player can also own queen cards by index.
 *
 * @author Hai Long Mac
 * @author Phuoc Thanh Nguyen
 */

package com.ouroboros.sleepingqueen.player;

import com.ouroboros.sleepingqueen.deck.Card;

public class Player {
    // Each player can have up to 5 normal cards and 4 queen cards at a time.
    private final int MAX_NORMAL_CARDS = 5;
    private final int MAX_QUEEN_CARDS = 4;

    private String name;
    private Card[] normalCards;
    private Card[] queenCards;
    private int[] queenIndexes;

    /**
     * Constructs a Player with a specified name.
     *
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.normalCards = new Card[MAX_NORMAL_CARDS];
        this.queenCards = new Card[MAX_QUEEN_CARDS];
        this.queenIndexes = new int[MAX_QUEEN_CARDS];
    }

    /**
     * Sets the index for a queen card at the given position.
     *
     * @param position The position in the queen card array.
     * @param index    The index value to set.
     */
    public void setQueenIndex(int position, int index) {
        queenIndexes[position] = index;
    }

    /**
     * Gets the name of the player.
     *
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the player's normal cards.
     *
     * @return An array of normal cards.
     */
    public Card[] getNormalCards() {
        return normalCards;
    }

    /**
     * Gets the player's queen cards.
     *
     * @return An array of queen cards.
     */
    public Card[] getQueenCards() {
        return queenCards;
    }

    /**
     * Gets the indexes of the queen cards owned by the player.
     *
     * @return An array of queen card indexes.
     */
    public int[] getQueenIndexes() {
        return queenIndexes;
    }

    /**
     * Checks if the player owns a queen card by its index.
     *
     * @param index The index to check.
     * @return True if the player owns the queen, otherwise false.
     */
    public boolean ownQueenCardByIndex(int index) {
        for (int i = 0; i < MAX_QUEEN_CARDS; i++) {
            if (queenIndexes[i] == index) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a queen card owned by the player by its index.
     *
     * @param index The index to search for.
     * @return The Queen card if found, otherwise null.
     */
    public Card getQueenCardByIndex(int index) {
        for (int i = 0; i < MAX_QUEEN_CARDS; i++) {
            if (queenIndexes[i] == index) {
                return queenCards[i];
            }
        }
        return null;
    }

    /**
     * Removes a queen card owned by the player by its index.
     *
     * @param index The index of the queen card to remove.
     */
    public void removeQueenCardByIndex(int index) {
        for (int i = 0; i < MAX_QUEEN_CARDS; i++) {
            if (queenIndexes[i] == index) {
                queenCards[i] = null;
                break;
            }
        }
    }

    /**
     * Gets the maximum number of normal cards a player can hold.
     *
     * @return The maximum normal card count.
     */
    public int getMAX_NORMAL_CARDS() {
        return MAX_NORMAL_CARDS;
    }

    /**
     * Gets the maximum number of queen cards a player can hold.
     *
     * @return The maximum queen card count.
     */
    public int getMAX_QUEEN_CARDS() {
        return MAX_QUEEN_CARDS;
    }

    /**
     * Sets a queen card at a specified position.
     *
     * @param position The position to set the card.
     * @param newCard  The queen card to be set.
     */
    public void setQueenCard(int position, Card newCard) {
        queenCards[position] = newCard;
    }

    /**
     * Sets a normal card at a specified position.
     *
     * @param position The position to set the card.
     * @param newCard  The normal card to be set.
     */
    public void setNormalCard(int position, Card newCard) {
        normalCards[position] = newCard;
    }

    /**
     * Gets a normal card from a specified position.
     *
     * @param position The position of the normal card.
     * @return The normal card at the specified position.
     */
    public Card getNormalCard(int position) {
        return normalCards[position];
    }

    /**
     * Adds a queen card to the player's collection.
     *
     * @param newCard The queen card to add.
     */
    public void addQueenCard(Card newCard) {
        for (int i = 0; i < MAX_QUEEN_CARDS; i++) {
            if (queenCards[i] == null) {
                queenCards[i] = newCard;
                break;
            }
        }
    }

    /**
     * Removes a normal card from the specified position.
     *
     * @param position The position of the card to remove.
     */
    public void removeNormalCard(int position) {
        normalCards[position] = null;
    }
}
