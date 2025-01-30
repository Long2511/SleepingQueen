// src/main/java/com/ouroboros/sleepingqueen/card/QueenFieldController.java
package com.ouroboros.sleepingqueen.card;

import com.ouroboros.sleepingqueen.dao.JSONCardDAO;
import com.ouroboros.sleepingqueen.deck.Card;
import javafx.fxml.FXML;
import javafx.scene.effect.Effect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class QueenFieldController {
    private final int NUMBER_OF_QUEENS = 12;
    @FXML
    private CardController queen1Controller;
    @FXML
    private CardController queen2Controller;
    @FXML
    private CardController queen3Controller;
    @FXML
    private CardController queen4Controller;
    @FXML
    private CardController queen5Controller;
    @FXML
    private CardController queen6Controller;
    @FXML
    private CardController queen7Controller;
    @FXML
    private CardController queen8Controller;
    @FXML
    private CardController queen9Controller;
    @FXML
    private CardController queen10Controller;
    @FXML
    private CardController queen11Controller;
    @FXML
    private CardController queen12Controller;
    private List<CardController> queenControllers;
    private Consumer<Integer> onQueenCardSelected;

    @FXML
    public void initialize() {
        queenControllers = new ArrayList<>();

        JSONCardDAO cardDAO = new JSONCardDAO();
        List<Card> queenCards = cardDAO.getAllQueenCard();

        Collections.shuffle(queenCards, new Random());

        queenControllers.add(queen1Controller);
        queenControllers.add(queen2Controller);
        queenControllers.add(queen3Controller);
        queenControllers.add(queen4Controller);
        queenControllers.add(queen5Controller);
        queenControllers.add(queen6Controller);
        queenControllers.add(queen7Controller);
        queenControllers.add(queen8Controller);
        queenControllers.add(queen9Controller);
        queenControllers.add(queen10Controller);
        queenControllers.add(queen11Controller);
        queenControllers.add(queen12Controller);

        for (int i = 0; i < queenControllers.size(); i++) {
            CardController cardController = queenControllers.get(i);
            cardController.setCard(queenCards.get(i));
            cardController.setIndex(i);
            cardController.setOnCardSelected(this::handleQueenCardSelected);
            cardController.render();
        }
    }

    public void setOnQueenCardSelected(Consumer<Integer> onQueenCardSelected) {
        this.onQueenCardSelected = onQueenCardSelected;
    }

    private void handleQueenCardSelected(int index) {
        if (onQueenCardSelected != null) {
            onQueenCardSelected.accept(index);
        }
    }

    public void setIdleQueenField(boolean isIdle) {
        for (CardController cardController : queenControllers) {
            cardController.setIdle(isIdle);
        }
    }

    public Card getQueenCard(int index) {
        if (index >= 0 && index < NUMBER_OF_QUEENS) {
            return queenControllers.get(index).getCard();
        }
        return null;
    }

    public void removeQueenFromField(Card queenCard) {
        for (CardController cardController : queenControllers) {
            if (cardController.getCard() == queenCard) {
                cardController.setCard(null);
                break;
            }
        }
    }

    public void setQueenCard(int index, Card card) {
        if (index >= 0 && index < NUMBER_OF_QUEENS) {
            queenControllers.get(index).setCard(card);
        }
    }

    public void setCardEffectByIndex(int index, Effect effect) {
        if (index >= 0 && index < NUMBER_OF_QUEENS) {
            queenControllers.get(index).setCardEffect(effect);
        }
    }

    public int numberOfAwakenQueens() {
        int count = 0;
        for (CardController cardController : queenControllers) {
            if (cardController.getCard() == null) {
                count++;
            }
        }
        return count;
    }
}