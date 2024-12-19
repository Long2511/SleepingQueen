package com.ouroboros.sleepingqueen.card;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class QueenFieldController {
    @FXML
    private QueenOnBoardController queen1Controller;
    @FXML
    private QueenOnBoardController queen2Controller;
    @FXML
    private QueenOnBoardController queen3Controller;
    @FXML
    private QueenOnBoardController queen4Controller;
    @FXML
    private QueenOnBoardController queen5Controller;
    @FXML
    private QueenOnBoardController queen6Controller;
    @FXML
    private QueenOnBoardController queen7Controller;
    @FXML
    private QueenOnBoardController queen8Controller;
    @FXML
    private QueenOnBoardController queen9Controller;
    @FXML
    private QueenOnBoardController queen10Controller;
    @FXML
    private QueenOnBoardController queen11Controller;
    @FXML
    private QueenOnBoardController queen12Controller;

    private List<QueenOnBoardController> queenControllers;
    private final int NUMBER_OF_QUEENS = 12;

    public void initialize() {
        System.out.println("QueenFieldController initialized");
        queenControllers = new ArrayList<QueenOnBoardController>();

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

        for (QueenOnBoardController queenController : queenControllers) {
            queenController.render();
        }
    }
}
