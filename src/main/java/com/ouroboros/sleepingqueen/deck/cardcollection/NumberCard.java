package com.ouroboros.sleepingqueen.deck.cardcollection;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardType;

public class NumberCard extends Card {
    private int cardNumber;
    public NumberCard() {

    }
    public NumberCard(String cardName, String cardImgPath, String backImgPath) {
        super(cardName, cardImgPath, backImgPath);
        this.cardNumber = Integer.parseInt(cardName.split(" ")[1]);
        this.type = CardType.NUMBER;
    }
}
