package com.ouroboros.sleepingqueen.controller;

import com.ouroboros.sleepingqueen.ults.ButtonSound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    public Button closeBtn;
    public Button mainMenuButton;
    public Button quitGame;
    private AnchorPane overlayPane;

    public void setOverlayPane(AnchorPane overlayPane) {
        this.overlayPane = overlayPane;
    }

    private void initialize() {
        closeBtn.setOnMouseClicked(event -> ButtonSound.playButtonClickSound());
        mainMenuButton.setOnMouseClicked(event -> ButtonSound.playButtonClickSound());
        quitGame.setOnMouseClicked(event -> ButtonSound.playButtonClickSound());
    }

    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        ButtonSound.playButtonClickSound();

        if (overlayPane != null) {
            overlayPane.setVisible(false);
        }
    }

    @FXML
    private void handleMainMenuAction(ActionEvent event) throws IOException {
        ButtonSound.playButtonClickSound();

        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/main-screen.fxml");
    }

    @FXML
    private void handleQuiteGameAction(ActionEvent event) throws IOException {
        ButtonSound.playButtonClickSound();
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}