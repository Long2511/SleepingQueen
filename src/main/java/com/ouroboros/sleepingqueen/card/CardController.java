package com.ouroboros.sleepingqueen.card;

import com.ouroboros.sleepingqueen.deck.Card;
import javafx.fxml.FXML;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;
import java.util.function.Consumer;

public class CardController {
    @FXML
    private ImageView cardHolder;

    private Card card;
    private boolean isFaceup;
    private boolean isIdle;
    private int index;
    private Consumer<Integer> onCardSelected;


    public void initialize() {
        System.out.println("QueenOnBoardController initialized.");
        card = null;  // no data yet
        isFaceup = false;
        isIdle = false;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
        render();
    }

    public void setIdle(boolean isIdle) {
        this.isIdle = isIdle;
    }

    public void setFaceup(boolean isFaceup) {
        this.isFaceup = isFaceup;
        render();
    }

    @FXML
    public void clickCard(MouseEvent e) {
        // If idle, do nothing
        if (isIdle) {
            System.out.println("This card is idle.");
            return;
        }
        if (card == null) {
            System.out.println("No card data yet.");
            return;
        }
        System.out.println("Card clicked.");
        if (onCardSelected != null) {
            onCardSelected.accept(index);
        }
    }
    
    public void setIndex(int index) {
        this.index = index;
    }

    public void setOnCardSelected(Consumer<Integer> onCardSelected) {
        this.onCardSelected = onCardSelected;
    }

    public void setCardEffect(Effect effect) {
        cardHolder.setEffect(effect);
    }

    public void render() {
        if (card == null) { // card is empty
            Image cardImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/ouroboros/sleepingqueen/cardImg/empty.jpg")));
            cardHolder.setImage(cardImg);
            return;
        }
        if (isFaceup) {
            Image cardImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream(card.getCardImgPath())));
            cardHolder.setImage(cardImg);
        } else {
            Image cardImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream(card.getBackImgPath())));
            cardHolder.setImage(cardImg);
        }
    }
}
