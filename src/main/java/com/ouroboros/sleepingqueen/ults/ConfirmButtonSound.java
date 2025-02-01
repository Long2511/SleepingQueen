/**
 * ConfirmButtonSound.java
 * <p>
 * This class is used to play the confirm button click sound.
 *
 * @author Hai Long Mac
 */

package com.ouroboros.sleepingqueen.ults;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class ConfirmButtonSound {
    /**
     * Plays the button click sound.
     */
    public static void playButtonClickSound() {
        String soundFile = Objects.requireNonNull(ConfirmButtonSound.class.getResource("/com/ouroboros/sleepingqueen/music/confirmButton.mp3")).toExternalForm();
        Media sound = new Media(soundFile);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}

