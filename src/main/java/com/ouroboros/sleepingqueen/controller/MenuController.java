package com.ouroboros.sleepingqueen.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    private AnchorPane overlayPane;

    public void setOverlayPane(AnchorPane overlayPane) {
        this.overlayPane = overlayPane;
    }

    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        if (overlayPane != null) {
            overlayPane.setVisible(false);
        }
    }

    @FXML
    private void handleMainMenuAction(ActionEvent event) throws IOException {
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/main-screen.fxml");
    }

    @FXML
    private void handleQuiteGameAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}