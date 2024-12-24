package com.ouroboros.sleepingqueen.deck.cardcollection;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardType;

public class KnightCard extends Card {
    public KnightCard() {

    }
    public KnightCard(String cardName, String description, String cardImgPath) {
        super(cardName, description, cardImgPath);
        this.type = CardType.KNIGHT;
    }}
