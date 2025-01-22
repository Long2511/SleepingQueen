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
    public void initialize() {
        System.out.println("SubPlayerFieldController initialized");
        // Initialize subPlayerLayoutControllers
        subPlayerLayoutControllers = new ArrayList<>();

        loadSubPlayer(SubPlayer1FieldBox);
        loadSubPlayer(SubPlayer2FieldBox);
        loadSubPlayer(SubPlayer3FieldBox);
        loadSubPlayer(SubPlayer4FieldBox);
    }

    private void loadSubPlayer(VBox subPlayer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/subPlayerView/sub-player-layout.fxml"));
            subPlayer.getChildren().add((GridPane) loader.load());
            subPlayerLayoutControllers.add(loader.getController());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPlayer(int position, Player player) {
        subPlayerLayoutControllers.get(position).setPlayerName(player.getName());
        subPlayerLayoutControllers.get(position).setPlayerQueen(player.getQueenCards());
    }

}
