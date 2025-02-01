/**
 * OOP Java Project  WiSe 2024/2025
 * file name: CardDeck.java
 * The CardDeck class represents a deck of cards in the game.
 * It manages the deck of cards, the discarded cards, and interacts with the card DAO.
 * <p>
 * Functionality:
 * - Initializes the deck with cards from a JSON file using JSONCardDAO.
 * - Allows drawing, peeking, shuffling, and discarding of cards.
 * - Supports reshuffling when the draw pile is empty.
 * - Resets the deck to its original state.
 * <p>
 * Usage:
 * {@code
 * CardDeck deck = new CardDeck();
 * Card drawnCard = deck.draw();
 * deck.addDiscarded(drawnCard);
 * deck.reshuffle();
 * }
 *
 * @author Thanh Phuoc Nguyen - 1584468
 */

package com.ouroboros.sleepingqueen.deck;

import com.ouroboros.sleepingqueen.dao.JSONCardDAO;

import java.util.*;

public class CardDeck {

    private Stack<Card> deck; // Stack representing the deck of cards
    private List<Card> discarded; // List of discarded cards
    private JSONCardDAO cardDAO; // DAO to load cards from JSON file

    /**
     * Constructs a CardDeck by initializing the deck with normal cards from the JSON DAO.
     * The deck is shuffled after loading the cards.
     */
    public CardDeck() {
        super();
        this.cardDAO = new JSONCardDAO();
        this.discarded = new ArrayList<>();
        this.deck = new Stack<>();
        this.deck.addAll(cardDAO.getAllNormalCard());
        shuffle();
    }

    /**
     * Shuffles the deck using a random seed.
     */
    public void shuffle() {
        Collections.shuffle(deck, new Random());
    }

    /**
     * Returns the top card of the deck without removing it.
     *
     * @return the top card of the deck
     */
    public Card peek() {
        return deck.peek();
    }

    /**
     * Draws (removes and returns) the top card from the deck.
     *
     * @return the drawn card, or null if the deck is empty
     */
    public Card draw() {
        if (deck.empty()) return null;
        return deck.pop();
    }

    /**
     * Adds a card to the discard pile.
     *
     * @param card the card to be added to the discard pile
     */
    public void addDiscarded(Card card) {
        discarded.add(card);
    }

    /**
     * When the deck is empty, the discard pile is shuffled and used as the new deck.
     */
    public void reshuffle() {
        deck.addAll(discarded);
        discarded.clear();
        shuffle();
    }

    /**
     * Resets the deck to its initial state by reloading the cards and shuffling them.
     */
    public void reset() {
        deck.clear();
        deck.addAll(cardDAO.getAllNormalCard());
        shuffle();
    }
}
