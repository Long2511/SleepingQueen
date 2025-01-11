package com.ouroboros.sleepingqueen.card;

import com.ouroboros.sleepingqueen.deck.Card;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class QueenController {
    @FXML
    private ImageView card;

    private Card queenCard;
    private boolean isFaceup;

    public void initialize() {
        System.out.println("QueenOnBoardController initialized.");
        queenCard = null;  // no data yet
        isFaceup = false;
    }

    public void setCard(Card card) {
        this.queenCard = card;
    }

    @FXML
    public void flipCard(MouseEvent e) {
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
