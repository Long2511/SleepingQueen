/**
 * OOP Java Project  WiSe 2024/2025
 * file name: JesterCard.java
 * The JesterCard is a subclass of the Card class.
 * It represents a Jester card in the game.
 *
 * @author Thanh Phuoc Nguyen - 1584468
 */
package com.ouroboros.sleepingqueen.deck.cardcollection;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardType;

public class JesterCard extends Card {
    public JesterCard() {

    }

    public JesterCard(String cardName, String cardImgPath, String backImgPath, String cardDescription) {
        super(cardName, cardImgPath, backImgPath, cardDescription);
        this.type = CardType.JESTER;
    }
}
