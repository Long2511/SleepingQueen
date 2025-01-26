/**
 * OOP Java Project  WiSe 2024/2025
 * file name: NumberCard.java
 * The NumberCard is a subclass of the Card class.
 * It represents a number card in the game.
 * Each number card has a card number value.
 *
 * @author Thanh Phuoc Nguyen - 1584468
 */

package com.ouroboros.sleepingqueen.deck.cardcollection;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardType;

public class NumberCard extends Card {
    private int cardNumber;

    public NumberCard() {
    }

    public NumberCard(String cardName, String cardImgPath, String backImgPath, String cardDescription) {
        super(cardName, cardImgPath, backImgPath, cardDescription);
        this.cardNumber = Integer.parseInt(cardName.split(" ")[1]);
        this.type = CardType.NUMBER;
    }

    public int GetNumberCardValue() {
        return cardNumber;
    }
}
