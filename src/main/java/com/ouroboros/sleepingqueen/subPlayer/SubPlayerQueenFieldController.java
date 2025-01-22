package com.ouroboros.sleepingqueen.subPlayer;

import com.ouroboros.sleepingqueen.card.QueenController;
import com.ouroboros.sleepingqueen.deck.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

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

    private List<QueenController> queenControllers;


    public void initialize() {
        System.out.println("SubPlayerQueenFieldController initialized");
        queenControllers = new ArrayList<>();

        loadQueen(subQueen1Box);
        loadQueen(subQueen2Box);
        loadQueen(subQueen3Box);
        loadQueen(subQueen4Box);

        for (QueenController queenController : queenControllers) {
            // Queens on player's hand should be fixed
            queenController.setIdle(true);
            // Queens on player's hand are always faceup
            queenController.setFaceup(true);
        }
    }

    private void loadQueen(VBox subQueen) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/subPlayerView/sub-player-queen-on-board.fxml"));
            subQueen.getChildren().add(loader.load());
            queenControllers.add(loader.getController());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setQueen(int position, Card queen) {
        if (position < 0 || position >= 4) {
            System.out.println("Invalid position of SubPlayer: " + position);
            return;
        }

        queenControllers.get(position).setCard(queen);
        queenControllers.get(position).render();
    }

}
