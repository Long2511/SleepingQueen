package com.ouroboros.sleepingqueen.mainPlayer;

import com.ouroboros.sleepingqueen.card.CardController;
import com.ouroboros.sleepingqueen.deck.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class MainPlayerQueenField {
    @FXML
    public VBox MainQueen1Box;
    @FXML
    public VBox MainQueen2Box;
    @FXML
    public VBox MainQueen3Box;
    @FXML
    public VBox MainQueen4Box;
    private List<CardController> queenControllers;

    public void initialize() {
        queenControllers = new ArrayList<>();
        System.out.println("MainPlayerQueenField initialized");
        loadCard(MainQueen1Box);
        loadCard(MainQueen2Box);
        loadCard(MainQueen3Box);
        loadCard(MainQueen4Box);
    }

    private void loadCard(VBox CardBox) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/mainPlayerView/main-player-queen-card.fxml"));
            CardBox.getChildren().add((VBox) loader.load());
            queenControllers.add(loader.getController());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCard(Card[] cards) {
        for (int i = 0; i < cards.length; i++) {
            queenControllers.get(i).setCard(cards[i]);
        }
    }
}