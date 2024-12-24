package com.ouroboros.sleepingqueen.deck;

import com.ouroboros.sleepingqueen.deck.cardcollection.*;

import java.util.Random;

public class CardDeck {
    private final int DECK_SIZE = 67;

    private Card[] deck = new Card[DECK_SIZE];
    private int currentCardIndex;

    public CardDeck() {
        currentCardIndex = 0;
        shuffle();
    }

    private void addCard(Card card) {
        assert this.deck != null;
        this.deck[this.currentCardIndex] = card;
        this.currentCardIndex++;
    }

    public void shuffle() {
        currentCardIndex = 0;
        Random rand = new Random();
        for (int i = DECK_SIZE - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            Card temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }
    }

    public Card peak() {
        if (currentCardIndex < DECK_SIZE) {
            return deck[currentCardIndex];
        } else {
            return null;
        }
    }

    public Card draw() {
        if (currentCardIndex < DECK_SIZE) {
            return deck[currentCardIndex++];
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
