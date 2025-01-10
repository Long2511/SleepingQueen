package com.ouroboros.sleepingqueen.deck.cardcollection;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardType;

public class NumberCard extends Card {
    private int cardNumber;
    public NumberCard() {

    }
    public NumberCard(String cardName, String description, String cardImgPath, String backImgPath) {
        super(cardName, description, cardImgPath, backImgPath);
        this.cardNumber = Integer.parseInt(cardName.split(" ")[1]);
        System.out.println(this.cardNumber);
        this.type = CardType.NUMBER;
    }
}
