/**
 * RuleViewController.java
 * <p>
 * This class controls the rule view screen. It initializes the rule view and handles the back button action.
 *
 * @author Hai Long Mac
 */

package com.ouroboros.sleepingqueen.controller;

import com.ouroboros.sleepingqueen.ults.ButtonSound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;

public class RuleViewController {

    @FXML
    private Button backBtn;
    @FXML
    private WebView webView;

    /**
     * Initializes the rule view screen by loading the rule HTML file into the WebView.
     * Adjusts the zoom level for better readability.
     */
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

    /**
     * Handles the back button action to navigate back to the main screen.
     *
     * @param event The action event triggered by the button.
     * @throws IOException If the scene file is not found.
     */
    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        ButtonSound.playButtonClickSound();
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/main-screen.fxml");
    }
}
