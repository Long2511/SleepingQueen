/**
 * SceneChanger.java
 * <p>
 * This class provides a utility method to change scenes in the application.
 *
 * @author Hai Long Mac
 */
package com.ouroboros.sleepingqueen.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneChanger {

    /**
     * Changes the scene of the application to the specified FXML file.
     *
     * @param event    The action event triggering the scene change.
     * @param fxmlFile The path of the FXML file to load.
     * @throws IOException If the FXML file cannot be loaded.
     */
    public static void changeScene(ActionEvent event, String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneChanger.class.getResource(fxmlFile));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
