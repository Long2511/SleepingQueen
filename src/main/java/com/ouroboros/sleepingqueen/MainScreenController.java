package com.ouroboros.sleepingqueen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreenController {
    @FXML
    private void handlePlayNowButtonAction(ActionEvent event) throws IOException {
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/pre-game-screen.fxml");

    }

    @FXML
    private void handleExitButtonAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleRuleViewButtonAction(ActionEvent event) throws IOException {
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/rule-view.fxml");
    }

}