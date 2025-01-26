/**
 * WinningScreenController.java
 * This class is used to control the winning screen.
 * Handle the main menu button and quit game button and set the winning player name.
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


    public void setWinningPlayerName(String playerName) {
        winText.setText("Congratulations " + playerName + " win!");
    }

    @FXML
    private void handleMainMenuAction(ActionEvent event) throws IOException {
        ButtonSound.playButtonClickSound();
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/main-screen.fxml");
    }

    @FXML
    void handleQuitGameAction(ActionEvent event) {
        ButtonSound.playButtonClickSound();
        System.exit(0);
    }
}