package com.ouroboros.sleepingqueen.deck.cardcollection;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardType;

public class JesterCard extends Card {
    public JesterCard() {

    }
    public JesterCard(String cardName, String cardImgPath, String backImgPath) {
        super(cardName, cardImgPath, backImgPath);
        this.type = CardType.JESTER;
    }
}
