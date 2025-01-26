/**
 * OOP Java Project  WiSe 2024/2025
 * file name: WandCard.java
 * The WandCard is a subclass of the Card class.
 * It represents a Wand card in the game.
 *
 * @author Thanh Phuoc Nguyen - 1584468
 */

package com.ouroboros.sleepingqueen.deck.cardcollection;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardType;

public class WandCard extends Card {
    public WandCard() {

    }

    public WandCard(String cardName, String cardImgPath, String backImgPath) {
        super(cardName, cardImgPath, backImgPath);
        this.type = CardType.WAND;
    }
}
