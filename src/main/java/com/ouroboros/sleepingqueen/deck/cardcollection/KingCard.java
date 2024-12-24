package com.ouroboros.sleepingqueen.deck.cardcollection;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardType;

public class KingCard extends Card {
    public KingCard() {}
    public KingCard(String cardName, String description, String cardImgPath) {
        super(cardName, description, cardImgPath);
        this.type = CardType.KING;
    }
}
