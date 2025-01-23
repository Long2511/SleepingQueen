package com.ouroboros.sleepingqueen.card;

import com.ouroboros.sleepingqueen.deck.Card;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class CardController {
    @FXML
    private ImageView card;

    private Card queenCard;
    private boolean isFaceup;
    private boolean isIdle;

    public void initialize() {
        System.out.println("QueenOnBoardController initialized.");
        queenCard = null;  // no data yet
        isFaceup = false;
        isIdle = false;
    }

    public void setCard(Card card) {
        this.queenCard = card;
    }

    public void setIdle(boolean isIdle) {
        this.isIdle = isIdle;
    }

    public void setFaceup(boolean isFaceup) {
        this.isFaceup = isFaceup;
    }

    @FXML
    public void flipCard(MouseEvent e) {
        // If idle, cannot be flipped
        if (isIdle) {
            System.out.println("This card is idle.");
            return;
        }
        if (queenCard == null) {
            System.out.println("No card data yet.");
            return;
        }
        System.out.println("Queen flipped");
        isFaceup = !isFaceup;
        render();
    }

    public void render() {
        if (queenCard == null) {
            System.out.println("No card data yet.");
            return;
        }
        if (isFaceup) {
            Image cardImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream(queenCard.getCardImgPath())));
            card.setImage(cardImg);
        } else {
            Image cardImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream(queenCard.getBackImgPath())));
            card.setImage(cardImg);
        }
    }
}
