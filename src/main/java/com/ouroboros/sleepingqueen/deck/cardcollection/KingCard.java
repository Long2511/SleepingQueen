package com.ouroboros.sleepingqueen.deck.cardcollection;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardType;

public class KingCard extends Card {
    public KingCard() {}
    public KingCard(String cardName, String cardImgPath, String backImgPath) {
        super(cardName, cardImgPath, backImgPath);
        this.type = CardType.KING;
    }
}
