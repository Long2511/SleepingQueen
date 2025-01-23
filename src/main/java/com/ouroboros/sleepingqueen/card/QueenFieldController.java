package com.ouroboros.sleepingqueen.card;

import com.ouroboros.sleepingqueen.dao.JSONCardDAO;
import com.ouroboros.sleepingqueen.deck.Card;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
    private List<Card> queenCards;
    private JSONCardDAO cardDAO;

    @FXML
    public void initialize() {
        System.out.println("QueenFieldController initialized");
        queenControllers = new ArrayList<CardController>();
//        queenCards = new ArrayList<Card>();
        cardDAO = new JSONCardDAO();
        queenCards = cardDAO.getAllQueenCard();

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

        for (CardController queenController : queenControllers) {
            queenController.setCard(queenCards.get(queenControllers.indexOf(queenController)));
        }
    }
}
