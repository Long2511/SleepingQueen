package com.ouroboros.sleepingqueen.controller;

import com.ouroboros.sleepingqueen.card.DeckController;
import com.ouroboros.sleepingqueen.card.QueenFieldController;
import com.ouroboros.sleepingqueen.player.Player;
import com.ouroboros.sleepingqueen.subPlayer.SubPlayerFieldController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class BoardViewController {
    @FXML
    private StackPane rootPane;
    @FXML
    private HBox deckField;
    @FXML
    private AnchorPane overlayPane;

    @FXML
    private QueenFieldController queenFieldController;

    @FXML
    private HBox subPlayerField;

    private List<Player> playerList;
    private int currentTurnPlayerIndex;
    private static String playerCount;

    private SubPlayerFieldController subPlayerFieldController;
    private DeckController deckController;


    @FXML
    public void initialize() {
        // Try  to load Deck to the Board
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/boardView/deck-on-board.fxml"));
            VBox deckPane = loader.load();
            deckField.getChildren().add(deckPane);
            deckController = loader.getController();
            deckController.setPlayNowButtonClick(this::handlePlayNowButtonClick);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Try to load SubPlayerField to the Board
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/subPlayerView/sub-player-field.fxml"));
            GridPane subPlayerPane = loader.load();
            subPlayerField.getChildren().add(subPlayerPane);
            subPlayerFieldController = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        setUpPlayer();
    }


    public static int getPlayerCount() {
        return Integer.parseInt(playerCount);
    }

    public static void setPlayerCount(String count) {
        playerCount = count;
    }

    private void setUpPlayer() {
        // Set up players
        playerList = new ArrayList<>();
        for (int i = 0; i < getPlayerCount(); i++) {
            Player player = new Player("Player " + (i + 1));
            // Draw 5 cards for each player
            for (int j = 0; j < player.getMAX_NORMAL_CARDS(); j++) {
                player.addCard(j, deckController.drawCard());
            }
            playerList.add(player);
        }
        // Player 1 makes a move first
        currentTurnPlayerIndex = 0;
    }

    private void endPlayerTurn() {
        int nextTurnPlayerIndex = (currentTurnPlayerIndex + 1) % getPlayerCount();

        // TODO: swap cards between sub&main players

        currentTurnPlayerIndex = nextTurnPlayerIndex;
    }

    private void handlePlayNowButtonClick() {
        System.out.println("Play now button clicked");
    }

    private void handleMenuButtonClick() {
        overlayPane.setVisible(true);
    }
}