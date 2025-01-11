package com.ouroboros.sleepingqueen.deck.cardcollection;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardType;

public class JesterCard extends Card {
    public JesterCard() {

    }
    public JesterCard(String cardName, String description, String cardImgPath, String backImgPath) {
        super(cardName, description, cardImgPath, backImgPath);
        this.type = CardType.JESTER;
    }
}
