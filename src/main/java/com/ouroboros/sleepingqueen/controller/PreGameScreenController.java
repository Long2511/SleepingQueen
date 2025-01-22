package com.ouroboros.sleepingqueen.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class PreGameScreenController {

    @FXML
    private ComboBox playerCountComboBox;


    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/main-screen.fxml");
    }


    @FXML
    private void handleCreateGameButtonAction(ActionEvent event) throws IOException {
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/board-view.fxml");
    }

    public void initialize() {
        playerCountComboBox.getItems().removeAll(playerCountComboBox.getItems());
        playerCountComboBox.getItems().addAll("2 Players", "3 Players", "4 Players", "5 Players");
        playerCountComboBox.getSelectionModel().select("2 Players");
    }

}