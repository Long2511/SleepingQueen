package com.ouroboros.sleepingqueen;

import com.ouroboros.sleepingqueen.deck.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CardController implements Initializable{
    @FXML
    private ImageView card1;
    @FXML
    private ImageView card2;

    String card1ImgPath = "/com/ouroboros/sleepingqueen/cardImg/facedown.jpeg";
    String card2ImgPath = "/com/ouroboros/sleepingqueen/cardImg/empty.jpeg";

    Image card1Img = new Image(Objects.requireNonNull(CardController.class.getResourceAsStream(card1ImgPath)));
    Image card2Img = new Image(Objects.requireNonNull(CardController.class.getResourceAsStream(card2ImgPath)));

    private CardDeck cardDeck = new CardDeck();
    private Card lastPlayedCard = new Card();

    @FXML
    public void pickCard(MouseEvent e) {
        System.out.println("Card picked");
        lastPlayedCard = cardDeck.draw();
        if (lastPlayedCard == null) {
            System.out.println("No more cards");
            cardDeck.reset();
            lastPlayedCard = cardDeck.draw();  // repick
        }
        System.out.println(lastPlayedCard.getCardImgPath());
        card2Img = new Image(Objects.requireNonNull(CardController.class.getResourceAsStream(lastPlayedCard.getCardImgPath())));
        card2.setImage(card2Img);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        card1.setImage(card1Img);
        card2.setImage(card2Img);
    }
}
