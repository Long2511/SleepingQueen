/**
 * @author Hai Long Mac
 * @author Phuoc Thanh Nguyen
 */

package com.ouroboros.sleepingqueen.card;

import com.ouroboros.sleepingqueen.deck.Card;
import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Controls the behavior and rendering of a card in the game.
 * Handles card selection, flipping (faceup/down), effects, and rendering.
 */
public class CardController {

    @FXML
    private ImageView cardHolder;

    private Card card;
    private boolean isFaceup;
    private boolean isIdle;
    private int index;
    private Consumer<Integer> onCardSelected;

    /**
     * Initializes the card controller with default values.
     */
    public void initialize() {
        card = null;  // no data yet
        isFaceup = false;
        isIdle = false;
        index = -1;
    }

    /**
     * Returns the card associated with this controller.
     *
     * @return The card object.
     */
    public Card getCard() {
        return card;
    }

    /**
     * Sets the card for this controller and updates its display.
     *
     * @param card The card object to be assigned.
     */
    public void setCard(Card card) {
        this.card = card;
        render();
    }

    /**
     * Sets whether the card is idle (unselectable).
     *
     * @param isIdle True if the card should be idle, false otherwise.
     */
    public void setIdle(boolean isIdle) {
        this.isIdle = isIdle;
    }

    /**
     * Sets whether the card should be faceup.
     *
     * @param isFaceup True if the card should be displayed faceup, false otherwise.
     */
    public void setFaceup(boolean isFaceup) {
        this.isFaceup = isFaceup;
        render();
    }

    /**
     * Handles card click events.
     *
     * @param e The mouse event triggering the card selection.
     */
    @FXML
    public void clickCard(MouseEvent e) {
        // If idle, do nothing
        if (isIdle) {
            return;
        }
        if (onCardSelected != null) {
            onCardSelected.accept(index);
        }
    }

    /**
     * Returns the index of this card in its collection.
     *
     * @return The card index.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the index of this card in its collection.
     *
     * @param index The card index.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Sets the action to be executed when the card is selected.
     *
     * @param onCardSelected A consumer function accepting the card index.
     */
    public void setOnCardSelected(Consumer<Integer> onCardSelected) {
        this.onCardSelected = onCardSelected;
    }

    /**
     * Applies a visual effect to the card.
     *
     * @param effect The effect to be applied.
     */
    public void setCardEffect(Effect effect) {
        cardHolder.setEffect(effect);
    }

    /**
     * Renders the card image based on its state (faceup or facedown).
     */
    public void render() {
        if (card == null) { // card is empty
            Image cardImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/ouroboros/sleepingqueen/cardImg/empty.jpg")));
            cardHolder.setImage(cardImg);
            return;
        }
        if (isFaceup) {
            Tooltip.install(cardHolder, new Tooltip(card.getCardDescription()));
            Image cardImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream(card.getCardImgPath())));
            cardHolder.setImage(cardImg);
        } else {
            Image cardImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream(card.getBackImgPath())));
            cardHolder.setImage(cardImg);
        }
    }
}
