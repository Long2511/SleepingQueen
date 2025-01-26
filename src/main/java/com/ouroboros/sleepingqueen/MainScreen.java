/**
 * MainScreen.java
 * This class is used to control the main screen. It has a method to set the background music and handle the dragging of the window.
 *
 * @author Hai Long Mac
 */

package com.ouroboros.sleepingqueen;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;


public class MainScreen extends Application {
    private MediaPlayer mediaPlayer;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainScreen.class.getResource("view/main-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        String musicFile = Objects.requireNonNull(getClass().getResource("/com/ouroboros/sleepingqueen/music/mainScreen.mp3")).toExternalForm();
        Media media = new Media(musicFile);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        mediaPlayer.setVolume(0.2);


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
        stage.getIcons().add(new Image(Objects.requireNonNull(MainScreen.class.getResourceAsStream("/com/ouroboros/sleepingqueen/icon/icon.png"))));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

}