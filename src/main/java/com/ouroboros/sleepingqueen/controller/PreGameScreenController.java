/**
 * PreGameScreenController.java
 * <p>
 * This class is used to control the pre-game screen. It has methods to handle the back button and create game button.
 *
 * @Author Hai Long Mac
 */

package com.ouroboros.sleepingqueen.controller;

import com.ouroboros.sleepingqueen.ults.ButtonSound;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;

public class PreGameScreenController {
    private final BooleanProperty isSpinnerVisible = new SimpleBooleanProperty(false);
    public Button backBtn;
    public Button createGame;
    private MediaPlayer mediaPlayer;
    @FXML
    private ComboBox<String> playerCountComboBox;
    @FXML
    private ProgressIndicator loadingSpinner;

    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        ButtonSound.playButtonClickSound();
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/main-screen.fxml");
    }

    @FXML
    private void handleCreateGameButtonAction(ActionEvent event) throws IOException {
        ButtonSound.playButtonClickSound();
        isSpinnerVisible.set(true);

        // Run the scene change in a separate thread to allow the spinner to show
        new Thread(() -> {
            try {
                BoardViewController.setPlayerCount(getPlayerCountComboBox());
                Platform.runLater(() -> {
                    try {
                        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/board-view.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        isSpinnerVisible.set(false);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void initialize() {
        playerCountComboBox.getItems().removeAll(playerCountComboBox.getItems());
        playerCountComboBox.getItems().addAll("2", "3", "4", "5");
        playerCountComboBox.getSelectionModel().select("2");
        loadingSpinner.visibleProperty().bind(isSpinnerVisible);


    }

    /**
     * Get the player count from the combo box
     *
     * @return player count
     */
    public String getPlayerCountComboBox() {
        return playerCountComboBox.getValue().toString();
    }
}