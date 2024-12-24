package com.ouroboros.sleepingqueen.deck;

import com.ouroboros.sleepingqueen.deck.cardcollection.*;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardDeck {
    private final int DECK_SIZE = 67;

    private List<Card> deck;
    private List<Card> queens;
    private int currentCardIndex;

    public CardDeck() {
        CardReader cardReader = new CardReader();
        cardReader.Read();
        this.deck = cardReader.getCardNotQueenList();
        this.queens = cardReader.getQueenCardList();
        currentCardIndex = 0;
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(deck, new Random());
    }

    public Card peak() {
        if (currentCardIndex < DECK_SIZE) {
            return deck.get(currentCardIndex);
        } else {
            return null;
        }
    }

    public Card draw() {
        if (currentCardIndex < DECK_SIZE) {
            return deck.get(currentCardIndex++);
        } else {
            return null;
        }
    }

    public void discard() {
    }

    public void reset() {
        this.shuffle();
        this.currentCardIndex = 0;
    }
}
