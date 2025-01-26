/**
 * OOP Java Project  WiSe 2024/2025
 * file name: QueenCard.java
 * The QueenCard is a subclass of the Card class.
 * It represents a Queen card in the game.
 * Each queen card has a point value.
 * Using nameCard to identify the between queen cards.
 *
 * @author Thanh Phuoc Nguyen - 1584468
 */

package com.ouroboros.sleepingqueen.deck.cardcollection;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardType;

public class QueenCard extends Card {
    private int point;

    public QueenCard() {
    }

    public QueenCard(String cardName, String cardImgPath, String backImgPath, int point, String cardDescription) {
        super(cardName, cardImgPath, backImgPath, cardDescription);
        this.type = CardType.QUEEN;
        this.point = point;
    }

    public int getPoint() {
        return this.point;
    }
}
