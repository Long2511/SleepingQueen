package com.ouroboros.sleepingqueen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class RuleViewController {
    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        SceneChanger.changeScene(event, "/com/ouroboros/sleepingqueen/view/main-screen.fxml");

    }
}
