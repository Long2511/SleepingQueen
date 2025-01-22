package com.ouroboros.sleepingqueen.controller;

import com.ouroboros.sleepingqueen.card.DeckController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BoardViewController {

    @FXML
    private HBox deckField;

    public static String getPlayerCount() {
        return playerCount;
    }

    public static void setPlayerCount(String count) {
        playerCount = count;
    }

    @FXML
    public void initialize() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/boardView/deck-on-board.fxml"));
            VBox deckPane = loader.load();
            deckField.getChildren().add(deckPane);
            deckController = loader.getController();
            deckController.setPlayNowButtonClick(this::handlePlayNowButtonClick);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlePlayNowButtonClick() {
        System.out.println("Play now button clicked");

    }


}