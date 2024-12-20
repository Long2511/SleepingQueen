package com.ouroboros.sleepingqueen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainScreen extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainScreen.class.getResource("view/board-view.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(MainScreen.class.getResource("view/main-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        stage.setTitle("Sleeping Queen");
        stage.getIcons().add(new Image(Objects.requireNonNull(MainScreen.class.getResourceAsStream("/com/ouroboros/sleepingqueen/icon/icon.png"))));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}