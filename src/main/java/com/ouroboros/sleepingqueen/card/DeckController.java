package com.ouroboros.sleepingqueen.card;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardDeck;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

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

    public void initialize() {
        System.out.println("DeckOnBoardController initialized.");
        playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (onDeckButtonClick != null) {
                onDeckButtonClick.run();
            }
        });
        cardDeck = new CardDeck();

        // set back of the deck
        deck.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(cardDeck.peek().getBackImgPath()))));
    }

    public Card drawCard() {
        lastDrawnCard = cardDeck.draw();
        if (lastDrawnCard == null) {
            System.out.println("Deck is empty. Reshuffle");
            cardDeck.reshuffle();
            // try to draw again
            lastDrawnCard = cardDeck.draw();
        }
        return lastDrawnCard;
    }

    public void discardCard(Card card) {
        discarded.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(card.getCardImgPath()))));
        cardDeck.addDiscarded(card);
    }

    public void setPlayNowButtonClick(Runnable onDeckButtonClick) {
        this.onDeckButtonClick = onDeckButtonClick;
    }

    public void setOnMenuButtonClick(Runnable onMenuButtonClick) {
        this.onDeckButtonClick = onMenuButtonClick;
    }

}
