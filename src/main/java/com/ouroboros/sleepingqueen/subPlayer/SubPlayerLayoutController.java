/**
 * SubPlayerLayoutController.java
 * <p>
 * Controller class for SubPlayerLayout.fxml
 * This class manages the layout of the sub player.
 * It contains the sub player's name, the sub player's queen field,
 * and the logic for handling the selection and effects of the queen card.
 *
 * @author Thanh Phuoc Nguyen
 */

package com.ouroboros.sleepingqueen.subPlayer;

import com.ouroboros.sleepingqueen.deck.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.Effect;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.function.Consumer;

public class SubPlayerLayoutController {

    @FXML
    public VBox SubPlayerQueenFieldBox;
    @FXML
    private Text SubPlayerName;

    private SubPlayerQueenFieldController subPlayerQueenFieldController;
    private Consumer<Integer> onAwakenQueenCardSelected;

    /**
     * Initializes the sub player layout by loading the sub player queen field.
     */
    @FXML
    public void initialize() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/subPlayerView/sub-player-queen-field.fxml"));
            SubPlayerQueenFieldBox.getChildren().add((GridPane) loader.load());
            subPlayerQueenFieldController = loader.getController();
            subPlayerQueenFieldController.setOnAwakenQueenCardSelected(this::handleAwakenQueenCardSelected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets whether the sub player is idle (non-interactive).
     *
     * @param idle If true, disables interactions with the sub player.
     */
    public void setIdle(boolean idle) {
        subPlayerQueenFieldController.setIdle(idle);
    }

    /**
     * Sets the event handler for when a queen card is selected.
     *
     * @param onQueenCardSelected The event handler function.
     */
    public void setOnAwakenQueenCardSelected(Consumer<Integer> onQueenCardSelected) {
        this.onAwakenQueenCardSelected = onQueenCardSelected;
    }

    /**
     * Handles queen card selection by notifying the event handler.
     *
     * @param index The index of the selected queen card.
     */
    private void handleAwakenQueenCardSelected(int index) {
        if (onAwakenQueenCardSelected != null) {
            onAwakenQueenCardSelected.accept(index);
        }
    }

    /**
     * Sets the sub player's name.
     *
     * @param name The name to set.
     */
    public void setPlayerName(String name) {
        SubPlayerName.setText(name);
    }

    /**
     * Sets the queen cards for the sub player.
     *
     * @param queens The array of queen cards.
     * @param indexes The corresponding indexes of the queen cards.
     */
    public void setPlayerQueen(Card[] queens, int[] indexes) {
        for (int i = 0; i < queens.length; i++) {
            subPlayerQueenFieldController.setQueen(i, queens[i], indexes[i]);
        }
    }

    /**
     * Sets a queen card at a specific position.
     *
     * @param position The position of the queen card.
     * @param queen The queen card.
     * @param index The index of the queen card.
     */
    public void setPlayerQueen(int position, Card queen, int index) {
        subPlayerQueenFieldController.setQueen(position, queen, index);
    }

    /**
     * Applies an effect to a queen card at a specific index.
     *
     * @param index The index of the queen card.
     * @param effect The effect to be applied.
     */
    public void setCardEffectByIndex(int index, Effect effect) {
        subPlayerQueenFieldController.setCardEffectByIndex(index, effect);
    }
}
