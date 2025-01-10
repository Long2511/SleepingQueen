package com.ouroboros.sleepingqueen.deck.cardcollection;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardType;

public class QueenCard extends Card {
    private int point;

    public QueenCard() {}
    public QueenCard(String cardName, String description, String cardImgPath, String backImgPath, int point) {
        super(cardName, description, cardImgPath, backImgPath);
        this.type = CardType.QUEEN;
        this.point = point;
    }

    public int getPoint() {
        return this.point;
    }
}
