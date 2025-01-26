/**
 * SubPlayerFieldController.java
 * <p>
 * This class is used to control the sub player field.
 *
 * @author Phuoc Thanh Nguyen
 * @Author Hai Long Mac
 */

package com.ouroboros.sleepingqueen.subPlayer;

import com.ouroboros.sleepingqueen.player.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.Effect;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SubPlayerFieldController {
    @FXML
    public VBox SubPlayer1FieldBox;
    @FXML
    public VBox SubPlayer2FieldBox;
    @FXML
    public VBox SubPlayer3FieldBox;
    @FXML
    public VBox SubPlayer4FieldBox;
    private List<SubPlayerLayoutController> subPlayerLayoutControllers;
    private Consumer<Integer> onAwakenQueenCardSelected;


    @FXML
    public void initializeBoard(int playerCount) {
        System.out.println("SubPlayerFieldController initialized");
        // Initialize subPlayerLayoutControllers
        subPlayerLayoutControllers = new ArrayList<>();

        System.out.println("Total Player: " + playerCount);

        if (playerCount > 1) loadSubPlayer(SubPlayer1FieldBox, 2);
        if (playerCount > 2) loadSubPlayer(SubPlayer2FieldBox, 3);
        if (playerCount > 3) loadSubPlayer(SubPlayer3FieldBox, 4);
        if (playerCount > 4) loadSubPlayer(SubPlayer4FieldBox, 5);

        for (SubPlayerLayoutController subPlayerLayoutController : subPlayerLayoutControllers) {
            subPlayerLayoutController.setOnAwakenQueenCardSelected(this::handleAwakenQueenCardSelected);
        }
    }

    public void setIdle(boolean idle) {
        for (SubPlayerLayoutController subPlayerLayoutController : subPlayerLayoutControllers) {
            subPlayerLayoutController.setIdle(idle);
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

    private void loadSubPlayer(VBox subPlayer, int playerNumber) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/subPlayerView/sub-player-layout.fxml"));
            subPlayer.getChildren().add((GridPane) loader.load());
            SubPlayerLayoutController controller = loader.getController();
            controller.setPlayerName("Player " + playerNumber);
            subPlayerLayoutControllers.add(controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPlayer(int position, Player player) {
        subPlayerLayoutControllers.get(position).setPlayerName(player.getName());
        subPlayerLayoutControllers.get(position).setPlayerQueen(player.getQueenCards(), player.getQueenIndexes());
    }

    public void setCardEffectByIndex(int index, Effect effect) {
        for (SubPlayerLayoutController subPlayerLayoutController : subPlayerLayoutControllers) {
            subPlayerLayoutController.setCardEffectByIndex(index, effect);
        }
    }


}
