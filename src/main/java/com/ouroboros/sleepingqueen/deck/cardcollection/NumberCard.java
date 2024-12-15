package com.ouroboros.sleepingqueen.deck.cardcollection;

import com.ouroboros.sleepingqueen.deck.Card;

public class NumberCard extends Card {
    private int cardNumber;
    public NumberCard() {

    }
    public NumberCard(int cardNumber) {
        this.cardNumber = cardNumber;
    }
    public NumberCard(int cardNumber, String cardImgPath) {
        super(cardImgPath);
        this.cardNumber = cardNumber;
    }
}
