package com.ouroboros.sleepingqueen.deck.cardcollection;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardType;

public class PotionCard extends Card {
    public PotionCard() {

    }
    public PotionCard(String cardName, String cardImgPath, String backImgPath) {
        super(cardName, cardImgPath, backImgPath);
        this.type = CardType.POTION;
    }
}
