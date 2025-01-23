package com.ouroboros.sleepingqueen.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;

public class RuleViewController {

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
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/main-screen.fxml");
    }
}
