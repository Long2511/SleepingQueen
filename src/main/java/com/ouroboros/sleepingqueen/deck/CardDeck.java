/**
 * OOP Java Project  WiSe 2024/2025
 * file name: CardDeck.java
 * The CardDeck class represents a deck of cards in the game.
 * Including: the deck of cards, the discarded cards, and the card DAO.
 * The deck of cards is a stack of cards.
 * The discarded cards are a list of cards that is played by players
 * The card DAO is used to get data from the JSON file.
 *
 * @author Thanh Phuoc Nguyen - 1584468
 */

package com.ouroboros.sleepingqueen.deck;

import com.ouroboros.sleepingqueen.dao.JSONCardDAO;

import java.util.*;


public class CardDeck {

    private Stack<Card> deck;
    private List<Card> discarded;
    private JSONCardDAO cardDAO;

    public CardDeck() {
        super();
        this.cardDAO = new JSONCardDAO();
        this.discarded = new ArrayList<>();
        this.deck = new Stack<>();
        this.deck.addAll(cardDAO.getAllNormalCard());
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(deck, new Random());
    }

    public Card peek() {
        return deck.peek();
    }

    public Card draw() {
        if (deck.empty()) return null;

        return deck.pop();
    }

    public void addDiscarded(Card card) {
        discarded.add(card);
    }

    /**
     * When the draw pile is empty, the discard pile is shuffled and becomes the new draw pile.
     */
    public void reshuffle() {
        deck.addAll(discarded);
        discarded.clear();
        shuffle();
    }

    public void reset() {
        deck.clear();
        deck.addAll(cardDAO.getAllNormalCard());
        shuffle();
    }
}
