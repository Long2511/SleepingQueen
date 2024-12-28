package com.ouroboros.sleepingqueen.deck;

import com.ouroboros.sleepingqueen.dao.JSONCardDAO;
import com.ouroboros.sleepingqueen.deck.cardcollection.*;

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
        this.deck.addAll(cardDAO.getAllCardNotQueen());
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
        deck.addAll(cardDAO.getAllCardNotQueen());
        shuffle();
    }
}
