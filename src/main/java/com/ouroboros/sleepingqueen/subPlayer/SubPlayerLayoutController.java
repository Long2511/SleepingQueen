/**
 * SubPlayerLayoutController.java
 * <p>
 * This class is the controller for the sub player layout.
 *
 * @Author Phuoc Thanh Nguyen
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
        System.out.println("SubPlayerLayoutController initialized");
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
