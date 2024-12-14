package com.ouroboros.sleepingqueen;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    public TextField nameField;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onFmeButtonClick() {
        String text = nameField.getText();
        welcomeText.setText("Hello, " + text + "!");
    }

    @FXML
    private void onEnterKeyPressed(javafx.scene.input.KeyEvent event) {
        if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
            onFmeButtonClick();
        }
    }
}