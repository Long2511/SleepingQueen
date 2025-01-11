package com.ouroboros.sleepingqueen.card;

import com.ouroboros.sleepingqueen.CardController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class QueenOnBoardController {
    @FXML
    private ImageView card;

    private boolean flipped = false;

    public void initialize() {
        System.out.println("QueenOnBoardController initialized.");
    }

    @FXML
    public void flipCard(MouseEvent e) {
        System.out.println("Queen flipped");
        if (flipped) {
            Image cardImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/ouroboros/sleepingqueen/cardImg/king-1.jpg")));
            card.setImage(cardImg);
            flipped = false;
        } else {
            Image cardImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/ouroboros/sleepingqueen/cardImg/dragon.jpg")));
            card.setImage(cardImg);
            flipped = true;
        }
    }

    public void render() {
        Image cardImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/ouroboros/sleepingqueen/cardImg/king-1.jpg")));
        card.setImage(cardImg);
        flipped = false;
    }
}
