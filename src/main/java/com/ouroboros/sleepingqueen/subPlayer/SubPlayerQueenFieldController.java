package com.ouroboros.sleepingqueen.subPlayer;

import com.ouroboros.sleepingqueen.card.QueenController;
import javafx.fxml.FXML;

public class SubPlayerQueenFieldController {
    // Each Sub Player has 4 queens of their own.
    @FXML
    private QueenController subQueen1Controller;
    @FXML
    private QueenController subQueen2Controller;
    @FXML
    private QueenController subQueen3Controller;
    @FXML
    private QueenController subQueen4Controller;

    public void initialize() {
        System.out.println("SubPlayerQueenFieldController initialized");
    }
}
