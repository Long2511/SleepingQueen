package com.ouroboros.sleepingqueen.deck.cardcollection;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardType;

public class DragonCard extends Card {
    public DragonCard() {

    }
    public DragonCard(String cardName, String description, String cardImgPath) {
        super(cardName, description, cardImgPath);
        this.type = CardType.DRAGON;
    }
}
