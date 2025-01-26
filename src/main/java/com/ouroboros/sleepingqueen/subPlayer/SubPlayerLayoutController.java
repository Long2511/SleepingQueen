/**
 * SubPlayerLayoutController.java
 * <p>
 * Controller class for SubPlayerLayout.fxml
 * This class is responsible for handling the layout of the sub player
 * It contains the sub player's name and the sub player's queen field
 * It also contains the logic for handling the selection of the queen card
 * and the effect of the card
 *
 * @author: Thanh Phuoc Nguyen - 1584468
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

    @FXML
    public void initialize() {
        // Load the sub player queen field
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/subPlayerView/sub-player-queen-field.fxml"));
            SubPlayerQueenFieldBox.getChildren().add((GridPane) loader.load());
            subPlayerQueenFieldController = loader.getController();
            subPlayerQueenFieldController.setOnAwakenQueenCardSelected(this::handleAwakenQueenCardSelected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setIdle(boolean idle) {
        subPlayerQueenFieldController.setIdle(idle);
    }


    public void setOnAwakenQueenCardSelected(Consumer<Integer> onQueenCardSelected) {
        this.onAwakenQueenCardSelected = onQueenCardSelected;
    }

    /**
     * Handle the selection of the queen card
     *
     * @param index the index of the selected card
     */
    private void handleAwakenQueenCardSelected(int index) {
        if (onAwakenQueenCardSelected != null) {
            onAwakenQueenCardSelected.accept(index);
        }
    }

    public void setPlayerName(String name) {
        SubPlayerName.setText(name);
    }

    public void setPlayerQueen(Card[] queens, int[] indexes) {
        for (int i = 0; i < queens.length; i++) {
            subPlayerQueenFieldController.setQueen(i, queens[i], indexes[i]);
        }
    }

    public void setPlayerQueen(int position, Card queen, int index) {
        subPlayerQueenFieldController.setQueen(position, queen, index);
    }

    public void setCardEffectByIndex(int index, Effect effect) {
        subPlayerQueenFieldController.setCardEffectByIndex(index, effect);
    }
}
