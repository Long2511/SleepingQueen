package com.ouroboros.sleepingqueen.deck.cardcollection;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardType;

public class PotionCard extends Card {
    public PotionCard() {

    }
    public PotionCard(String cardName, String description, String cardImgPath) {
        super(cardName, description, cardImgPath);
        this.type = CardType.POTION;
    }
}
