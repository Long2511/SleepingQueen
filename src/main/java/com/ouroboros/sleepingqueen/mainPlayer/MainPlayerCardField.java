package com.ouroboros.sleepingqueen.mainPlayer;


import com.ouroboros.sleepingqueen.card.CardController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class MainPlayerCardField {
    @FXML
    private VBox MainCard1Box;
    @FXML
    private VBox MainCard2Box;
    @FXML
    private VBox MainCard3Box;
    @FXML
    private VBox MainCard4Box;
    @FXML
    private VBox MainCard5Box;

    private List<CardController> cardControllers;

    public void initialize() {
        cardControllers = new ArrayList<>();
        System.out.println("MainPlayerCardField initialized.");
        loadCard(MainCard1Box);
        loadCard(MainCard2Box);
        loadCard(MainCard3Box);
        loadCard(MainCard4Box);
        loadCard(MainCard5Box);
    }

    private void loadCard(VBox CardBox) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ouroboros/sleepingqueen/view/mainPlayerView/main-player-card.fxml"));
            CardBox.getChildren().add((VBox) loader.load());
            cardControllers.add(loader.getController());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
