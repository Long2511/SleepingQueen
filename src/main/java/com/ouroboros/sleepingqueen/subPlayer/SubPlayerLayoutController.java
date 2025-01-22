package com.ouroboros.sleepingqueen.subPlayer;

import com.ouroboros.sleepingqueen.deck.Card;
import com.ouroboros.sleepingqueen.player.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SubPlayerLayoutController {
    @FXML
    public VBox SubPlayerQueenFieldBox;
    @FXML
    private Text SubPlayerName;

    private SubPlayerQueenFieldController subPlayerQueenFieldController;

    @FXML
    public void initialize() {
        System.out.println("SubPlayerLayoutController initialized");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/subPlayerView/sub-player-queen-field.fxml"));
            SubPlayerQueenFieldBox.getChildren().add((GridPane) loader.load());
            subPlayerQueenFieldController = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPlayerName(String name) {
        SubPlayerName.setText(name);
    }
    public void setPlayerQueen(Card[] queens) {
        for (int i = 0; i < queens.length; i++) {
            subPlayerQueenFieldController.setQueen(i, queens[i]);
        }
    }
    public void setPlayerQueen(int position, Card queen) {
        subPlayerQueenFieldController.setQueen(position, queen);
    }
}
