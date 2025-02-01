/**
 * @author Hai Long Mac
 * @author Phuoc Thanh Nguyen
 */

package com.ouroboros.sleepingqueen.card;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardDeck;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

/**
 * Controls the deck functionality in the game.
 * Handles drawing, discarding cards, and deck interactions.
 */
public class DeckController {

    @FXML
    private ImageView deck;
    @FXML
    private ImageView discarded;
    @FXML
    private Button playButton;

    private Runnable onDeckButtonClick;
    private CardDeck cardDeck;
    private Card lastDrawnCard;

    /**
     * Initializes the deck, sets up event handlers, and loads the deck's back image.
     */
    public void initialize() {
        playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (onDeckButtonClick != null) {
                onDeckButtonClick.run();
            }
        });
        cardDeck = new CardDeck();

        // Set the back of the deck
        deck.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(cardDeck.peek().getBackImgPath()))));
    }

    /**
     * Draws a card from the deck.
     * If the deck is empty, it reshuffles and tries drawing again.
     *
     * @return The drawn card.
     */
    public Card drawCard() {
        lastDrawnCard = cardDeck.draw();
        if (lastDrawnCard == null) {
            cardDeck.reshuffle();
            lastDrawnCard = cardDeck.draw();
        }
        return lastDrawnCard;
    }

    /**
     * Discards a card and updates the discard pile image.
     *
     * @param card The card to be discarded.
     */
    public void discardCard(Card card) {
        discarded.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(card.getCardImgPath()))));
        cardDeck.addDiscarded(card);
    }

    /**
     * Sets the action to be executed when the play button is clicked.
     *
     * @param onDeckButtonClick The action to execute.
     */
    public void setPlayNowButtonClick(Runnable onDeckButtonClick) {
        this.onDeckButtonClick = onDeckButtonClick;
    }

    /**
     * Sets the action to be executed when the menu button is clicked.
     *
     * @param onMenuButtonClick The action to execute.
     */
    public void setOnMenuButtonClick(Runnable onMenuButtonClick) {
        this.onDeckButtonClick = onMenuButtonClick;
    }
}
