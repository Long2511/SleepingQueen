package com.ouroboros.sleepingqueen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class PreGameScreenController  {
    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/main-screen.fxml");
    }

    @FXML
    private void handleJoinGameButtonAction(ActionEvent event) throws IOException {
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/board-view.fxml");
    }
}
