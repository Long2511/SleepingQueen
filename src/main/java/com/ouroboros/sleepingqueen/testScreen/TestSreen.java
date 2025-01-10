package com.ouroboros.sleepingqueen.testScreen;

import com.ouroboros.sleepingqueen.MainScreen;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class TestSreen extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainScreen.class.getResource("view/board-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);


        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        stage.setX(event.getScreenX() - scene.getWindow().getWidth() / 2);
                        stage.setY(event.getScreenY() - 20);
                    }
                });
            }
        });

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Sleeping Queen");
        stage.getIcons().add(new Image(Objects.requireNonNull(TestSreen.class.getResourceAsStream("/com/ouroboros/sleepingqueen/icon/icon.png"))));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

}