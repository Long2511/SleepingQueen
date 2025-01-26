/**
 * SubPlayerQueenFieldController.java
 * <p>
 * Controller class for the queen field of the sub player
 * This class is responsible for displaying the queens on the sub player's field
 * The queens are displayed faceup and fixed
 * The queens are displayed in a row of 4 queens
 * The queens are displayed with an event handler for awaken queen card
 * The queens are displayed with an effect
 * The queens are displayed with a method to set the queen card
 * The queens are displayed with a method to set the card effect by index
 *
 * @author: Thanh Phuoc Nguyen - 1584468
 */

package com.ouroboros.sleepingqueen.subPlayer;

import com.ouroboros.sleepingqueen.card.CardController;
import com.ouroboros.sleepingqueen.deck.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.Effect;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SubPlayerQueenFieldController {
    // Game ends if a player has 5 queens
    // => Maximum displayed queens are 4
    @FXML
    private VBox subQueen1Box;
    @FXML
    private VBox subQueen2Box;
    @FXML
    private VBox subQueen3Box;
    @FXML
    private VBox subQueen4Box;

    private List<CardController> queenControllers;
    private Consumer<Integer> onAwakenQueenCardSelected;


    public void initialize() {
        // Initialize queenControllers
        queenControllers = new ArrayList<>();

        // Load the queen card for the sub player
        loadQueen(subQueen1Box);
        loadQueen(subQueen2Box);
        loadQueen(subQueen3Box);
        loadQueen(subQueen4Box);

        for (CardController queenController : queenControllers) {
            // Queens on player's hand should be fixed
            queenController.setIdle(true);
            // Queens on player's hand are always faceup
            queenController.setFaceup(true);
            // Set event handler for awaken queen card
            queenController.setOnCardSelected(this::handleAwakenQueenCardSelected);
        }
    }

    public void setIdle(boolean idle) {
        for (CardController queenController : queenControllers) {
            queenController.setIdle(idle);
        }
    }

    public void setOnAwakenQueenCardSelected(Consumer<Integer> onQueenCardSelected) {
        this.onAwakenQueenCardSelected = onQueenCardSelected;
    }

    private void handleAwakenQueenCardSelected(int index) {
        if (onAwakenQueenCardSelected != null) {
            onAwakenQueenCardSelected.accept(index);
        }
    }

    /**
     * Load the queen card for the sub player
     *
     * @param subQueen the queen card
     */
    private void loadQueen(VBox subQueen) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/subPlayerView/sub-player-queen-on-board.fxml"));
            subQueen.getChildren().add(loader.load());
            queenControllers.add(loader.getController());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the queen card for the sub player
     *
     * @param position position of the queen card
     * @param queen    the queen card
     * @param index    the index of the queen card
     */
    public void setQueen(int position, Card queen, int index) {
        if (position < 0 || position >= 4) {
            System.out.println("Invalid position of SubPlayer: " + position);
            return;
        }

        queenControllers.get(position).setCard(queen);
        queenControllers.get(position).setIndex(index);
    }

    public void setCardEffectByIndex(int index, Effect effect) {
        for (CardController queenController : queenControllers) {
            if (queenController.getIndex() == index) {
                queenController.setCardEffect(effect);
            }
        }
    }

}
