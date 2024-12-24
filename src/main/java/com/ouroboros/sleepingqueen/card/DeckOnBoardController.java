package com.ouroboros.sleepingqueen.card;

import com.ouroboros.sleepingqueen.deck.*;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.Objects;

public class DeckOnBoardController {
    @FXML
    private ImageView deck;
    @FXML
    private ImageView discarded;

    private CardDeck cardDeck;
    private TranslateTransition drawCardAnimation;
    private Card lastDrawnCard;

    public void initialize() {
        System.out.println("DeckOnBoardController initialized.");
        cardDeck = new CardDeck();

        // Set draw deck animation
        drawCardAnimation = new TranslateTransition();
        drawCardAnimation.setNode(deck);
        drawCardAnimation.setDuration(Duration.millis(20));
//        drawCardAnimation.setCycleCount(1);
        drawCardAnimation.setInterpolator(Interpolator.LINEAR);
        drawCardAnimation.setByY(-(179+171));
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
}
