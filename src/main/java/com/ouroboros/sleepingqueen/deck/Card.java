/**
 * OOP Java Project  WiSe 2024/2025
 * file name: Card.java
 * The Card class represents a card in the game.
 * Including: the card type, card name, card image path, and back image path.
 *
 * @author Thanh Phuoc Nguyen - 1584468
 */

package com.ouroboros.sleepingqueen.deck;

public class Card {
    protected CardType type;
    private String cardName;
    private String cardImgPath;
    private String backImgPath;

    public Card() {
    }

    public Card(String cardName, String cardImgPath, String backImgPath) {
        this.cardName = cardName;
        this.cardImgPath = cardImgPath;
        this.backImgPath = backImgPath;
    }

    public Card(String cardImgPath) {
        this.cardImgPath = cardImgPath;
    }

    public String getCardImgPath() {
        return cardImgPath;
    }

    public String getBackImgPath() {
        return backImgPath;
    }

    public CardType getType() {
        return this.type;
    }

    public String getCardName() {
        return cardName;
    }
}
