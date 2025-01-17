package com.ouroboros.sleepingqueen.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class PreGameScreenController {

    @FXML
    private TextField playerNameField;

    @FXML
    private TextField gameIdField;

    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/main-screen.fxml");
    }

    @FXML
    private void handleJoinGameButtonAction(ActionEvent event) throws IOException {
/*        String playerName = playerNameField.getText();
        String gameId = gameIdField.getText();
        new GameClient().startClient(playerName, gameId, 0);*/
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/board-view.fxml");
    }

    @FXML
    private void handleCreateGameButtonAction(ActionEvent event) throws IOException {
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/board-view.fxml");
    }
}