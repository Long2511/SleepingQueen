/**
 * @author Thanh Phuoc Nguyen - 1584468
 * @author Hai Long Mac - 1534413
 */

package com.ouroboros.sleepingqueen.mainPlayer;

import com.ouroboros.sleepingqueen.card.CardController;
import com.ouroboros.sleepingqueen.deck.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the main player's queen field.
 * Handles queen card loading, initialization, and setting queens on the field.
 */
public class MainPlayerQueenField {
    private final int NUMBER_OF_CARD = 4;

    @FXML
    public VBox MainQueen1Box;
    @FXML
    public VBox MainQueen2Box;
    @FXML
    public VBox MainQueen3Box;
    @FXML
    public VBox MainQueen4Box;

    private List<CardController> queenControllers;

    /**
     * Initializes the queen field, loads queen cards, and sets their default states.
     */
    public void initialize() {
        queenControllers = new ArrayList<>();
        loadCard(MainQueen1Box);
        loadCard(MainQueen2Box);
        loadCard(MainQueen3Box);
        loadCard(MainQueen4Box);

        for (int i = 0; i < NUMBER_OF_CARD; i++) {
            CardController cardController = queenControllers.get(i);
            cardController.setFaceup(true);
            cardController.setIdle(true);
        }
    }

    /**
     * Loads a queen card into a given VBox container.
     *
     * @param CardBox The VBox where the queen card will be added.
     */
    private void loadCard(VBox CardBox) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/mainPlayerView/main-player-queen-card.fxml"));
            CardBox.getChildren().add((VBox) loader.load());
            queenControllers.add(loader.getController());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets multiple queen cards on the field.
     *
     * @param cards Array of queen cards to be set.
     */
    public void setCard(Card[] cards) {
        for (int i = 0; i < cards.length; i++) {
            queenControllers.get(i).setCard(cards[i]);
        }
    }
}
