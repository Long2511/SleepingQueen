package com.ouroboros.sleepingqueen.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class PreGameScreenController {


    @FXML
    private ComboBox<String> playerCountComboBox;


    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/main-screen.fxml");
    }


    @FXML
    private void handleCreateGameButtonAction(ActionEvent event) throws IOException {
        BoardViewController.setPlayerCount(getPlayerCountComboBox());
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/board-view.fxml");
    }

    public void initialize() {
        playerCountComboBox.getItems().removeAll(playerCountComboBox.getItems());
        playerCountComboBox.getItems().addAll("2", "3", "4", "5");
        playerCountComboBox.getSelectionModel().select("2");
    }

    public String getPlayerCountComboBox() {
        return playerCountComboBox.getValue().toString();
    }
}