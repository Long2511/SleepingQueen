/**
 * SubPlayerFieldController.java
 * <p>
 * This class controls the sub player field, handling the layout and interaction of sub players.
 * It dynamically loads sub players based on the total number of players in the game.
 *
 * @author Phuoc Thanh Nguyen
 * @author Hai Long Mac
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

    /**
     * Initializes the sub player field based on the total number of players.
     *
     * @param playerCount The total number of players in the game.
     */
    @FXML
    public void initializeBoard(int playerCount) {
        subPlayerLayoutControllers = new ArrayList<>();

        if (playerCount > 1) loadSubPlayer(SubPlayer1FieldBox, 2);
        if (playerCount > 2) loadSubPlayer(SubPlayer2FieldBox, 3);
        if (playerCount > 3) loadSubPlayer(SubPlayer3FieldBox, 4);
        if (playerCount > 4) loadSubPlayer(SubPlayer4FieldBox, 5);

        for (SubPlayerLayoutController subPlayerLayoutController : subPlayerLayoutControllers) {
            subPlayerLayoutController.setOnAwakenQueenCardSelected(this::handleAwakenQueenCardSelected);
        }
    }

    /**
     * Sets whether the sub players' fields are idle or active.
     *
     * @param idle If true, disables interactions with sub players.
     */
    public void setIdle(boolean idle) {
        for (SubPlayerLayoutController subPlayerLayoutController : subPlayerLayoutControllers) {
            subPlayerLayoutController.setIdle(idle);
        }
    }

    /**
     * Sets an event listener for when a queen card is selected from a sub player.
     *
     * @param onQueenCardSelected The event handler function.
     */
    public void setOnAwakenQueenCardSelected(Consumer<Integer> onQueenCardSelected) {
        this.onAwakenQueenCardSelected = onQueenCardSelected;
    }

    /**
     * Handles when a queen card is selected from a sub player.
     *
     * @param index The index of the selected queen card.
     */
    private void handleAwakenQueenCardSelected(int index) {
        if (onAwakenQueenCardSelected != null) {
            onAwakenQueenCardSelected.accept(index);
        }
    }

    /**
     * Loads a sub player layout dynamically into the sub player field.
     *
     * @param subPlayer The VBox container for the sub player.
     * @param playerNumber The number representing the player.
     */
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

    /**
     * Sets the player information in a specific sub player slot.
     *
     * @param position The position of the sub player in the field.
     * @param player The player object containing name and queen cards.
     */
    public void setPlayer(int position, Player player) {
        subPlayerLayoutControllers.get(position).setPlayerName(player.getName());
        subPlayerLayoutControllers.get(position).setPlayerQueen(player.getQueenCards(), player.getQueenIndexes());
    }

    /**
     * Applies an effect to a queen card by index.
     *
     * @param index The index of the queen card.
     * @param effect The effect to be applied.
     */
    public void setCardEffectByIndex(int index, Effect effect) {
        for (SubPlayerLayoutController subPlayerLayoutController : subPlayerLayoutControllers) {
            subPlayerLayoutController.setCardEffectByIndex(index, effect);
        }
    }
}
