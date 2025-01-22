package com.ouroboros.sleepingqueen.controller;

import com.ouroboros.sleepingqueen.card.DeckController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class BoardViewController {
    private static String playerCount;

    private DeckController deckController;
    @FXML
    private StackPane rootPane;
    @FXML
    private HBox deckField;
    @FXML
    private AnchorPane overlayPane;

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

            // Load overlay
            FXMLLoader overlayLoader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/menu.fxml"));
            overlayPane = overlayLoader.load();
            overlayPane.setVisible(false);
            deckField.getChildren().add(overlayPane);

            // Set overlay constraints
            AnchorPane.setTopAnchor(overlayPane, 0.0);
            AnchorPane.setBottomAnchor(overlayPane, 0.0);
            AnchorPane.setLeftAnchor(overlayPane, 0.0);
            AnchorPane.setRightAnchor(overlayPane, 0.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlePlayNowButtonClick() {
        System.out.println("Play now button clicked");
    }

    private void handleMenuButtonClick() {
        overlayPane.setVisible(true);
    }


}