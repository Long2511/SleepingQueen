package com.ouroboros.sleepingqueen.mainPlayer;


import com.ouroboros.sleepingqueen.card.CardController;
import com.ouroboros.sleepingqueen.deck.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class MainPlayerCardField {
    private final int NUMBER_OF_CARD = 5;
    boolean isChosen[] = new boolean[NUMBER_OF_CARD];
    @FXML
    private VBox MainCard1Box;
    @FXML
    private VBox MainCard2Box;
    @FXML
    private VBox MainCard3Box;
    @FXML
    private VBox MainCard4Box;
    @FXML
    private VBox MainCard5Box;
    private List<CardController> cardControllers;
    private DropShadow choosingEffect;

    public void initialize() {
        cardControllers = new ArrayList<>();
        System.out.println("MainPlayerCardField initialized.");
        loadCard(MainCard1Box);
        loadCard(MainCard2Box);
        loadCard(MainCard3Box);
        loadCard(MainCard4Box);
        loadCard(MainCard5Box);

        for (int i = 0; i < NUMBER_OF_CARD; i++) {
            CardController cardController = cardControllers.get(i);

            isChosen[i] = false;
            cardController.setIndex(i);

            cardController.setFaceup(true);
            cardController.setIdle(false);

            cardController.setOnCardSelected(this::handleCardSelected);
        }

        choosingEffect = new DropShadow();
        choosingEffect.setBlurType(BlurType.TWO_PASS_BOX);
        choosingEffect.setRadius(10.0);
        choosingEffect.setSpread(1.0);
        choosingEffect.setColor(Color.web("#37ff00"));
    }

    private void loadCard(VBox CardBox) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/mainPlayerView/main-player-card.fxml"));
            CardBox.getChildren().add((VBox) loader.load());
            cardControllers.add(loader.getController());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleCardSelected(int index) {
        System.out.println("Card " + index + " selected.");

        // TODO: delete this setFaceup once do not Flip Card on click
        cardControllers.get(index).setFaceup(true);

        if (isChosen[index]) {
            isChosen[index] = false;
            cardControllers.get(index).setCardEffect(null);
        } else {
            isChosen[index] = true;
            cardControllers.get(index).setCardEffect(choosingEffect);
        }
    }

    public List<Card> getChosenCards() {
        List<Card> chosenCards = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_CARD; i++) {
            if (isChosen[i]) {
                chosenCards.add(cardControllers.get(i).getCard());
            }
        }
        return chosenCards;
    }

    public List<Integer> getChosenCardIndex() {
        List<Integer> chosenCardIndices = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_CARD; i++) {
            if (isChosen[i]) {
                chosenCardIndices.add(i);
            }
        }
        return chosenCardIndices;
    }

    public void setCard(Card[] cards) {
        for (int i = 0; i < cards.length; i++) {
            cardControllers.get(i).setCard(cards[i]);
        }
    }
}
