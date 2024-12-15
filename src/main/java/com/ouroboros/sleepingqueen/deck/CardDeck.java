package com.ouroboros.sleepingqueen.deck;

import com.ouroboros.sleepingqueen.deck.cardcollection.*;

import java.util.Random;

public class CardDeck {
    private final int DECK_SIZE = 67;

    private Card[] deck = new Card[DECK_SIZE];
    private int currentCardIndex;

    public CardDeck() {
        currentCardIndex = 0;
        for (int i = 0; i < 8; i++) {
            addCard(new KingCard("/com/ouroboros/sleepingqueen/cardImg/king.jpeg"));
        }
        for (int i = 0; i < 5; i++) {
            addCard(new JesterCard("/com/ouroboros/sleepingqueen/cardImg/jester.jpeg"));
        }
        for (int i = 0; i < 4; i++) {
            addCard(new KnightCard("/com/ouroboros/sleepingqueen/cardImg/knight.jpeg"));
        }
        for (int i = 0; i < 4; i++) {
            addCard(new Poison("/com/ouroboros/sleepingqueen/cardImg/poison.jpeg"));
        }
        for (int i = 0; i < 3; i++) {
            addCard(new WandCard("/com/ouroboros/sleepingqueen/cardImg/wand.jpeg"));
        }
        for (int i = 0; i < 3; i++) {
            addCard(new DragonCard("/com/ouroboros/sleepingqueen/cardImg/dragon.jpeg"));
        }
        for (int cardNumber = 1; cardNumber <= 10; cardNumber++) {
            for (int time = 0; time < 4; time++) {
                addCard(new NumberCard(cardNumber, "/com/ouroboros/sleepingqueen/cardImg/" + cardNumber + ".jpeg"));
            }
        }
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
