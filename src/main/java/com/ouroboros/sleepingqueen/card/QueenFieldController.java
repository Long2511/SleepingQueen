package com.ouroboros.sleepingqueen.card;

import com.ouroboros.sleepingqueen.dao.JSONCardDAO;
import com.ouroboros.sleepingqueen.deck.Card;
import javafx.fxml.FXML;

import java.util.*;

public class QueenFieldController {
    @FXML
    private QueenController queen1Controller;
    @FXML
    private QueenController queen2Controller;
    @FXML
    private QueenController queen3Controller;
    @FXML
    private QueenController queen4Controller;
    @FXML
    private QueenController queen5Controller;
    @FXML
    private QueenController queen6Controller;
    @FXML
    private QueenController queen7Controller;
    @FXML
    private QueenController queen8Controller;
    @FXML
    private QueenController queen9Controller;
    @FXML
    private QueenController queen10Controller;
    @FXML
    private QueenController queen11Controller;
    @FXML
    private QueenController queen12Controller;

    private List<QueenController> queenControllers;
    private List<Card> queenCards;
    private final int NUMBER_OF_QUEENS = 12;

    private JSONCardDAO cardDAO;

    @FXML
    public void initialize() {
        System.out.println("QueenFieldController initialized");
        queenControllers = new ArrayList<QueenController>();
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

        for (QueenController queenController : queenControllers) {
            queenController.setCard(queenCards.get(queenControllers.indexOf(queenController)));
            queenController.render();
        }
    }
}
