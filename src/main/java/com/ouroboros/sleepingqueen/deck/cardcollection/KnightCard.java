/**
 * OOP Java Project  WiSe 2024/2025
 * file name: KnightCard.java
 * The KnightCard is a subclass of the Card class.
 * It represents a Knight card in the game.
 *
 * @author Thanh Phuoc Nguyen - 1584468
 */

package com.ouroboros.sleepingqueen.deck.cardcollection;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardType;

public class KnightCard extends Card {
    public KnightCard() {

    }

    public KnightCard(String cardName, String cardImgPath, String backImgPath, String cardDescription) {
        super(cardName, cardImgPath, backImgPath, cardDescription);
        this.type = CardType.KNIGHT;
    }
}
