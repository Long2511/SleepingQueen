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

    public Player(String name) {
        this.name = name;
        this.normalCards = new Card[MAX_NORMAL_CARDS];
        this.queenCards = new Card[MAX_QUEEN_CARDS];
        this.queenIndexes = new int[MAX_QUEEN_CARDS];
    }

    public void setQueenIndex(int position, int index) {
        queenIndexes[position] = index;
    }

    public String getName() {
        return name;
    }

    public Card[] getNormalCards() {
        return normalCards;
    }

    public Card[] getQueenCards() {
        return queenCards;
    }

    public int[] getQueenIndexes() {
        return queenIndexes;
    }

    public boolean ownQueenCardByIndex(int index) {
        for (int i = 0; i < MAX_QUEEN_CARDS; i++) {
            if (queenIndexes[i] == index) {
                return true;
            }
        }
        return false;
    }

    public Card getQueenCardByIndex(int index) {
        for (int i = 0; i < MAX_QUEEN_CARDS; i++) {
            if (queenIndexes[i] == index) {
                return queenCards[i];
            }
        }
        return null;
    }

    public void removeQueenCardByIndex(int index) {
        for (int i = 0; i < MAX_QUEEN_CARDS; i++) {
            if (queenIndexes[i] == index) {
                queenCards[i] = null;
                break;
            }
        }
    }

    public int getMAX_NORMAL_CARDS() {
        return MAX_NORMAL_CARDS;
    }

    public int getMAX_QUEEN_CARDS() {
        return MAX_QUEEN_CARDS;
    }

    public void setQueenCard(int position, Card newCard) {
        queenCards[position] = newCard;
    }

    public void setNormalCard(int position, Card newCard) {
        normalCards[position] = newCard;
    }

    public Card getNormalCard(int position) {
        return normalCards[position];
    }

    public void addQueenCard(Card newCard) {
        for (int i = 0; i < MAX_QUEEN_CARDS; i++) {
            if (queenCards[i] == null) {
                queenCards[i] = newCard;
                break;
            }
        }
    }

    public void removeNormalCard(int position) {
        normalCards[position] = null;
    }

}