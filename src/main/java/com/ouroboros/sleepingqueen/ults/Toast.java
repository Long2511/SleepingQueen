package com.ouroboros.sleepingqueen.ults;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Toast {

    public static void show(Stage ownerStage, String message, int durationInMillis) {
        Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);

        Label label = new Label(message);
        label.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        label.setOpacity(0);
        label.setFont(new javafx.scene.text.Font("Great Vibes", 30));

        StackPane pane = new StackPane(label);
        pane.setStyle("-fx-background-color: transparent;");
        popup.getContent().add(pane);

        popup.setOnShown(event -> {
            Scene scene = ownerStage.getScene();
            popup.setX(scene.getWindow().getX() + (scene.getWidth() - pane.getWidth()) / 2);
            popup.setY(scene.getWindow().getY() + (scene.getHeight() - pane.getHeight()) / 2 - 130); // Move up by 50px
        });

        popup.show(ownerStage);

        // Fade in animation
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), label);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1.0);

        // Fade out animation
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), label);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0);
        fadeOut.setDelay(Duration.millis(durationInMillis));

        fadeIn.setOnFinished(e -> fadeOut.play());
        fadeOut.setOnFinished(e -> popup.hide());

        fadeIn.play();
    }
}
