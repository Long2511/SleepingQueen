package com.ouroboros.sleepingqueen.card;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.deck.CardDeck;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

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
    private TranslateTransition drawCardAnimation;
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

        // Set draw deck animation
        drawCardAnimation = new TranslateTransition();
        drawCardAnimation.setNode(deck);
        drawCardAnimation.setDuration(Duration.millis(20));
//        drawCardAnimation.setCycleCount(1);
        drawCardAnimation.setInterpolator(Interpolator.LINEAR);
        drawCardAnimation.setByY(-(179 + 171));
        drawCardAnimation.setCycleCount(2);
        drawCardAnimation.setAutoReverse(true);
        drawCardAnimation.setOnFinished(e -> {
            Image drawnCardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(lastDrawnCard.getCardImgPath())));
//            deck.setImage(drawnCardImage);
//            deck.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/ouroboros/sleepingqueen/cardImg/facedown.jpeg"))));
            discarded.setImage(drawnCardImage);

        });
    }

    public void drawCard(MouseEvent e) throws InterruptedException {
        lastDrawnCard = cardDeck.draw();
        if (lastDrawnCard == null) {
            System.out.println("Deck is empty. Reshuffle");
            cardDeck.reshuffle();
        }

        drawCardAnimation.play();
    }

    public void setPlayNowButtonClick(Runnable onDeckButtonClick) {
        this.onDeckButtonClick = onDeckButtonClick;
    }

}
