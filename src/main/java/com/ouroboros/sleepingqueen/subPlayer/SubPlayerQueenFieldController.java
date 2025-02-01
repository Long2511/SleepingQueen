/**
 * SubPlayerQueenFieldController.java
 * <p>
 * Controller class for the queen field of the sub player.
 * This class is responsible for displaying the queens on the sub player's field.
 * The queens are displayed faceup and fixed in a row of 4 queens.
 * The queens have an event handler for awaken queen card selection.
 *
 * @author Thanh Phuoc Nguyen
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

    // Maximum displayed queens are 4
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

    /**
     * Initializes the sub player's queen field.
     * Loads and sets up queen cards.
     */
    public void initialize() {
        queenControllers = new ArrayList<>();
        loadQueen(subQueen1Box);
        loadQueen(subQueen2Box);
        loadQueen(subQueen3Box);
        loadQueen(subQueen4Box);

        for (CardController queenController : queenControllers) {
            queenController.setIdle(true); // Queens are fixed in place
            queenController.setFaceup(true); // Always faceup
            queenController.setOnCardSelected(this::handleAwakenQueenCardSelected);
        }
    }

    /**
     * Sets whether the queens can be interacted with.
     *
     * @param idle If true, disables interactions.
     */
    public void setIdle(boolean idle) {
        for (CardController queenController : queenControllers) {
            queenController.setIdle(idle);
        }
    }

    /**
     * Sets the event listener for when a queen card is selected.
     *
     * @param onQueenCardSelected The event handler function.
     */
    public void setOnAwakenQueenCardSelected(Consumer<Integer> onQueenCardSelected) {
        this.onAwakenQueenCardSelected = onQueenCardSelected;
    }

    /**
     * Handles when a queen card is selected.
     *
     * @param index The index of the selected queen card.
     */
    private void handleAwakenQueenCardSelected(int index) {
        if (onAwakenQueenCardSelected != null) {
            onAwakenQueenCardSelected.accept(index);
        }
    }

    /**
     * Loads a queen card into the sub player's field.
     *
     * @param subQueen The VBox container for the queen card.
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
     * Sets a queen card in the specified position.
     *
     * @param position The position of the queen card.
     * @param queen The queen card.
     * @param index The index of the queen card.
     */
    public void setQueen(int position, Card queen, int index) {
        if (position < 0 || position >= 4) {
            return;
        }
        queenControllers.get(position).setCard(queen);
        queenControllers.get(position).setIndex(index);
    }

    /**
     * Applies an effect to a queen card by index.
     *
     * @param index The index of the queen card.
     * @param effect The effect to apply.
     */
    public void setCardEffectByIndex(int index, Effect effect) {
        for (CardController queenController : queenControllers) {
            if (queenController.getIndex() == index) {
                queenController.setCardEffect(effect);
            }
        }
    }
}
