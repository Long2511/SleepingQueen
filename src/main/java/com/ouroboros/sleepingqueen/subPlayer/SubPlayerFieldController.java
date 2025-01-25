package com.ouroboros.sleepingqueen.subPlayer;

import com.ouroboros.sleepingqueen.player.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

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
        subPlayerLayoutControllers.get(position).setPlayerQueen(player.getQueenCards());
    }


}
