package com.ouroboros.sleepingqueen.controller;

import com.ouroboros.sleepingqueen.ults.ButtonSound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreenController {
    @FXML
    private Button playbtn;
    @FXML
    private Button exitbtn;
    @FXML
    private Button rulebtn;

    @FXML
    private void handlePlayNowButtonAction(ActionEvent event) throws IOException {
        ButtonSound.playButtonClickSound();

        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/pre-game-screen.fxml");

    }

    @FXML
    private void handleExitButtonAction(ActionEvent event) {
        ButtonSound.playButtonClickSound();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleRuleViewButtonAction(ActionEvent event) throws IOException {
        ButtonSound.playButtonClickSound();

        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/rule-screen.fxml");
    }
}