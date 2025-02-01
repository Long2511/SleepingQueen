/**
 * OOP Java Project  WiSe 2024/2025
 * file name: Card.java
 * The Card class represents a card in the game.
 * It includes properties such as the card type, name, image path, and description.
 * <p>
 * Functionality:
 * - Stores card details including name, image paths, and description.
 * - Provides getter methods to access card properties.
 * <p>
 * Usage:
 * {@code
 * Card card = new Card("Queen", "path/to/image.png", "path/to/back.png", "A powerful queen card");
 * String name = card.getCardName();
 * String imgPath = card.getCardImgPath();
 * }
 *
 * @author Thanh Phuoc Nguyen - 1584468
 */

package com.ouroboros.sleepingqueen.deck;

public class Card {
    protected CardType type; // Type of the card
    private String cardName; // Name of the card
    private String cardImgPath; // Path to the card image
    private String backImgPath; // Path to the back image of the card
    private String cardDescription; // Description of the card

    /**
     * Default constructor for Card.
     */
    public Card() {
    }

    /**
     * Constructs a Card with specified name, image paths, and description.
     *
     * @param cardName the name of the card
     * @param cardImgPath the path to the card's image
     * @param backImgPath the path to the back image of the card
     * @param cardDescription the description of the card
     */
    public Card(String cardName, String cardImgPath, String backImgPath, String cardDescription) {
        this.cardName = cardName;
        this.cardImgPath = cardImgPath;
        this.backImgPath = backImgPath;
        this.cardDescription = cardDescription;
    }

    /**
     * Constructs a Card with only the image path.
     *
     * @param cardImgPath the path to the card's image
     */
    public Card(String cardImgPath) {
        this.cardImgPath = cardImgPath;
    }

    /**
     * Returns the image path of the card.
     *
     * @return the card image path
     */
    public String getCardImgPath() {
        return cardImgPath;
    }

    /**
     * Returns the back image path of the card.
     *
     * @return the back image path
     */
    public String getBackImgPath() {
        return backImgPath;
    }

    /**
     * Returns the type of the card.
     *
     * @return the card type
     */
    public CardType getType() {
        return this.type;
    }

    /**
     * Returns the name of the card.
     *
     * @return the card name
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * Returns the description of the card.
     *
     * @return the card description
     */
    public String getCardDescription() {
        return cardDescription;
    }
}
