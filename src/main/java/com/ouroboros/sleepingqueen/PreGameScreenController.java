package com.ouroboros.sleepingqueen;

import com.ouroboros.sleepingqueen.multiplayer.GameClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

import static com.ouroboros.sleepingqueen.multiplayer.GameClient.discoverServerAddress;

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
        String playerName = playerNameField.getText();
        String gameId = gameIdField.getText();
        String serverAddress = discoverServerAddress();
        new GameClient().startClient(playerName, gameId, 0, serverAddress);
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/board-view.fxml");
    }

    @FXML
    private void handleCreateGameButtonAction(ActionEvent event) throws IOException {
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/board-view.fxml");
    }
}