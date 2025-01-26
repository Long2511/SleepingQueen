package com.ouroboros.sleepingqueen.controller;

import com.ouroboros.sleepingqueen.ults.ButtonSound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;

public class RuleViewController {

    public Button backBtn;
    @FXML
    private WebView webView;

    @FXML
    public void initialize() {
        URL url = getClass().getResource("/com/ouroboros/sleepingqueen/Rule-Sleeping-Queens.html");
        if (url != null) {
            webView.getEngine().load(url.toString());
            webView.setZoom(1.8);
        } else {
            System.err.println("Resource not found: /com/ouroboros/sleepingqueen/Rule-Sleeping-Queens.html");
        }
        backBtn.setCancelButton(true);

    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        ButtonSound.playButtonClickSound();
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/main-screen.fxml");
    }
}
