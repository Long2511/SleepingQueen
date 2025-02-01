/**
 * MainScreenController.java
 * This class is used to control the main screen. It has methods to handle the Play Now button, Exit button, and Rule View button.
 *
 * @author Hai Long Mac
 */

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

    /**
     * Handles the Play Now button action.
     * Changes the scene to the pre-game screen.
     *
     * @param event The action event triggered by the button.
     * @throws IOException If the scene file is not found.
     */
    @FXML
    private void handlePlayNowButtonAction(ActionEvent event) throws IOException {
        ButtonSound.playButtonClickSound();
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/pre-game-screen.fxml");
    }

    /**
     * Handles the Exit button action.
     * Closes the application window.
     *
     * @param event The action event triggered by the button.
     */
    @FXML
    private void handleExitButtonAction(ActionEvent event) {
        ButtonSound.playButtonClickSound();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the Rule View button action.
     * Changes the scene to the rule screen.
     *
     * @param event The action event triggered by the button.
     * @throws IOException If the scene file is not found.
     */
    @FXML
    private void handleRuleViewButtonAction(ActionEvent event) throws IOException {
        ButtonSound.playButtonClickSound();
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/rule-screen.fxml");
    }
}
