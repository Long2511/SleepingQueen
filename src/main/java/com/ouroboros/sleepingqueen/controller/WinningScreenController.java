/**
 * WinningScreenController.java
 * <p>
 * This class controls the winning screen. It handles actions for the main menu button,
 * the quit game button, and sets the winning player name.
 *
 * @author Hai Long Mac
 */
package com.ouroboros.sleepingqueen.controller;

import com.ouroboros.sleepingqueen.ults.ButtonSound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;

public class WinningScreenController {

    @FXML
    private Text winText;

    /**
     * Sets the winning player's name on the winning screen.
     *
     * @param playerName The name of the winning player.
     */
    public void setWinningPlayerName(String playerName) {
        winText.setText("Congratulations " + playerName + " win!");
    }

    /**
     * Handles the action for returning to the main menu.
     *
     * @param event The action event triggered by the button.
     * @throws IOException If the scene file is not found.
     */
    @FXML
    private void handleMainMenuAction(ActionEvent event) throws IOException {
        ButtonSound.playButtonClickSound();
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/main-screen.fxml");
    }

    /**
     * Handles the action for quitting the game.
     *
     * @param event The action event triggered by the button.
     */
    @FXML
    void handleQuitGameAction(ActionEvent event) {
        ButtonSound.playButtonClickSound();
        System.exit(0);
    }
}
